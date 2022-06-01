package com.lmyun.event.pointchanger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    public HashMap<String, FileConfiguration> pluginConfig = new HashMap<>();

    @Override
    public void onEnable() {
        this.loadConfig();
        PointManager.load();
        new BukkitRunnable() {
            @Override
            public void run() {
                PointManager.save();
                getLogger().log(Level.INFO, "点数信息保存...");
            }
        }.runTaskTimerAsynchronously(this, 24 * 60 * 3, 24 * 60 * 3);
    }

    @Override
    public void onDisable() {
        PointManager.save();
    }

    public void loadConfig() {
        saveResource("Config.yml", false);
        this.pluginConfig.put("Config", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Config.yml")));
        saveResource("Data.yml", false);
        saveResource("Lang.yml", false);
        this.pluginConfig.put("Lang", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Lang.yml")));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LangUtil.getLang("lang1"));
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 1 && p.isOp() && args[0].equalsIgnoreCase("reload")) {
            this.loadConfig();
            PointManager.load();
            p.sendMessage(LangUtil.getLang("lang2"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            p.sendMessage(LangUtil.getLang("lang3").replace("<point>", String.valueOf(PointManager.getPoint(p.getName()))));
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("exchange")) {
            String changeType = args[1];
            ShopGod god = this.getShopGod(changeType);
            if (god == null) {
                p.sendMessage(LangUtil.getLang("lang4"));
                return true;
            }
            ///////开始兑换
            if (!PointManager.hasPoint(p.getName(), god.getPoint())) {
                p.sendMessage(LangUtil.getLang("lang5"));
                return true;
            }
            p.sendMessage(LangUtil.getLang("lang6").replace("<name>", god.getName()));
            PointManager.addPoint(p.getName(), -god.getPoint());
            this.playerRunCommands(p, god.getCommands());
            return true;
        }
        if (args.length == 3&& p.isOp() && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("set"))) {
            String doType = args[0];
            String pName = args[1];
            int point = Integer.valueOf(args[2]);
            switch (doType) {
                case "give":
                    PointManager.addPoint(pName, point);
                    break;
                case "set":
                    PointManager.setPoint(pName, point);
                    break;
            }
            p.sendMessage(LangUtil.getLang("lang7").replace("<point>", String.valueOf(PointManager.getPoint(pName))));
            return true;
        }
        String help = "/pointchanger reload\t\t重载(需要op权限)" +
                "\r\n/pointchanger info\t\t查询点数信息" +
                "\r\n/pointchanger exchange <商品名称>\t\t兑换" +
                "\n\n/pointchanger give|set <玩家名> <点数>\t\t增加或者设置点数";
        p.sendMessage(help);
        return true;
    }

    public ShopGod getShopGod(String type) {
        ConfigurationSection config = this.pluginConfig.get("Config").getConfigurationSection("exchange." + type);
        if (config == null)
            return null;
        ShopGod god = new ShopGod();
        god.setPoint(config.getInt("point"));
        god.setCommands(config.getStringList("command"));
        god.setName(config.getString("name"));
        return god;
    }

    public void playerRunCommands(Player p, List<String> commands) {
        boolean isOp = p.isOp();
        try {
            p.setOp(true);
            for (String command : commands) {
                command = command.replace("<player>", p.getName());
                p.performCommand(command);
            }
        } finally {
            if (isOp) {
                return;
            }
            p.setOp(false);
        }
    }

    public static Main getPlugin() {
        return (Main) Bukkit.getPluginManager().getPlugin("PointChanger");
    }
}

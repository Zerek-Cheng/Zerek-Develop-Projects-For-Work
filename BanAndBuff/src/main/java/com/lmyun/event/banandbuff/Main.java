package com.lmyun.event.banandbuff;

import cc.baka9.slashbladeshop.utils.ItemUtil;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class Main extends JavaPlugin {
    public static Permission permission = null;
    public static Economy economy = null;
    @Getter
    private HashMap<String, FileConfiguration> pluginConfig = new HashMap<>();

    private HashMap<String, Short> playerKillNum = new HashMap<>();

    @Override
    public void onEnable() {
        this.setupPermissions();
        this.setupEconomy();
        this.loadConfig();
        Bukkit.getPluginManager().registerEvents(new BanListener(this), this);
        Bukkit.getPluginManager().registerEvents(new KillBuffListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GroupKillListener(this), this);
        new BanTask(this).runTaskTimerAsynchronously(this, 24, 24);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        ItemStack itemInHand = p.getItemInHand();
        if (!p.isOp()){
            sender.sendMessage("不是op拒绝执行！");
            return false;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage(LangUtil.getLang("lang6"));
            return true;
        }
        if (args.length >= 1 && args[0].equalsIgnoreCase("ban")) {
            if (args.length == 3 && args[1].equalsIgnoreCase("hand")) {
                this.banItem(args[2], itemInHand.getTypeId(), itemInHand.getDurability());
                sender.sendMessage(LangUtil.getLang("lang2"));
                return true;
            } else if (args.length == 2 && args[1].equalsIgnoreCase("save")) {
                this.saveGroupBan();
                sender.sendMessage(LangUtil.getLang("lang3"));
                return true;
            } else if (args.length == 4) {
                int idFrom = Integer.valueOf(args[2]);
                int idTo = Integer.valueOf(args[3]);
                for (int i = 0; i <= (idTo - idFrom); i++) {
                    this.banItem(args[1], idFrom + i, (short) -1);
                }
                sender.sendMessage(LangUtil.getLang("lang2"));
                return true;
            }
        }
        if (args.length >= 1 && args[0].equalsIgnoreCase("delban")) {
            if (args.length == 3 && args[1].equalsIgnoreCase("hand")) {
                this.delBanItem(args[2], itemInHand.getTypeId(), itemInHand.getDurability());
                sender.sendMessage(LangUtil.getLang("lang2"));
                return true;
            } else if (args.length == 4) {
                int idFrom = Integer.valueOf(args[2]);
                int idTo = Integer.valueOf(args[3]);
                for (int i = 0; i <= (idTo - idFrom); i++) {
                    this.delBanItem(args[1], idFrom + i, (short) -1);
                }
                sender.sendMessage(LangUtil.getLang("lang2"));
                return true;
            }
        }
        String help = "/bab reload 重载\n" +
                "/bab (del)ban hand <权限组> 禁止手上物品\n" +
                "/bab (del)ban save 保存禁止\n" +
                "/bab (del)ban <权限组> <起始ID> <结束ID>";
        sender.sendMessage(help);
        return true;
    }

    public void loadConfig() {
        saveResource("GroupBan.yml", false);
        this.pluginConfig.put("GroupBan", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/GroupBan.yml")));
        saveResource("Lang.yml", false);
        this.pluginConfig.put("Lang", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Lang.yml")));
        saveResource("Buff.yml", false);
        this.pluginConfig.put("Buff", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Buff.yml")));
        saveResource("GroupKill.yml", false);
        this.pluginConfig.put("GroupKill", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/GroupKill.yml")));
    }

    public boolean saveGroupBan() {
        try {
            this.pluginConfig.get("GroupBan").save(new File(getDataFolder().getPath() + "/GroupBan.yml"));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> EconomyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (EconomyProvider != null) {
            economy = EconomyProvider.getProvider();
        }
        return (economy != null);
    }

    public void banItem(String group, int id, short sId) {
        ConfigurationSection banCon = this.pluginConfig.get("GroupBan").getConfigurationSection(group);
        List<String> list = banCon != null ? banCon.getStringList("id") : new ArrayList<>();
        list.add(String.valueOf(id) + ":" + (sId == -1 ? "*" : String.valueOf(sId)));
        banCon = banCon != null ? banCon : this.pluginConfig.get("GroupBan").createSection(group);
        banCon.set("id", list);
        this.pluginConfig.get("GroupBan").set(group, banCon);
    }

    public void delBanItem(String group, int id, short sId) {
        ConfigurationSection banCon = this.pluginConfig.get("GroupBan").getConfigurationSection(group);
        List<String> list = banCon.getStringList("id");
        list.remove(String.valueOf(id) + ":" + (sId == -1 ? "*" : String.valueOf(sId)));
        banCon.set("id", list);
        this.pluginConfig.get("GroupBan").set(group, banCon);
    }

    public boolean isBanned(String group, ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR)) {
            return false;
        }
        ConfigurationSection banConfig = this.pluginConfig.get("GroupBan").getConfigurationSection(group);
        if (banConfig == null) {
            return false;
        }
        List<String> idList = banConfig.getStringList("id");
        if (idList.contains(String.valueOf(item.getTypeId())) || idList.contains(item.getTypeId() + ":*") || idList.contains(item.getTypeId() + ":" + item.getDurability())) {
            return true;
        }
        String nbtDate = ItemUtil.getItemStackNbtDate(item).toString();
        List<String> nbtList = banConfig.getStringList("nbt");
        nbtList = nbtList != null ? nbtList : new ArrayList<>();
        for (String nbt : nbtList) {
            return nbtDate.contains(nbt);
        }
        return false;
    }

    public void addKill(String player) {
        this.playerKillNum.put(player, (short) (this.getKill(player) + 1));
    }

    public void setKill(String player, short kill) {
        this.playerKillNum.put(player, kill);
    }

    public short getKill(String player) {
        Short kill = this.playerKillNum.get(player);
        return kill != null ? kill : 0;
    }

    public void runKill(Player p) {
        short kill = this.getKill(p.getName());
        FileConfiguration buffCons = this.pluginConfig.get("Buff");
        ConfigurationSection buffCon = buffCons.getConfigurationSection(String.valueOf(kill));
        if (buffCon == null) {
            return;
        }
        List<String> commands = buffCon.getStringList("command");
        for (String command : commands) {
            command = command.replaceAll("<player>", p.getName());
            boolean isOp = p.isOp();
            try {
                p.setOp(true);
                p.performCommand(command);
            } finally {
                if (!isOp) {
                    p.setOp(false);
                }
            }
        }
    }

    public double getEnemyMoney(String group1, String group2) {
        ConfigurationSection groupCon = this.pluginConfig.get("GroupKill").getConfigurationSection(group1);
        if (groupCon == null) {
            return -1d;
        }
        List<String> enemys = groupCon.getStringList("enemy");
        if (!enemys.contains(group2)) {
            return -1d;
        }
        return groupCon.getDouble("money");
    }

    public static Main getPlugin() {
        return (Main) Bukkit.getPluginManager().getPlugin("BanAndBuff");
    }
}

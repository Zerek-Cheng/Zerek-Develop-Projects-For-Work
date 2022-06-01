package com._0myun.minecraft.dailyquest;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class DailyQuests extends JavaPlugin {
    static DailyQuests plugin;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        new TimeOutChecker().runTaskTimer(this, 20, 20);
        new EZPlaceholderHook(this, "DailyQuests") {
            @Override
            public String onPlaceholderRequest(Player p, String params) {
                return String.valueOf(getTodayRepeat(p.getUniqueId()));
            }
        }.hook();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player p = (Player) sender;
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                this.reloadConfig();
                return true;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("setloc") && sender.isOp()) {
                String quest = args[1];
                if (!getConfig().isSet("display." + quest)) {
                    sender.sendMessage("任务不存在");
                }
                Location loc = p.getLocation();
                ConfigurationSection section = getConfig().createSection("loc." + quest);
                section.set("x", loc.getX());
                section.set("y", loc.getY());
                section.set("z", loc.getZ());
                section.set("world", loc.getWorld().getName());
                getConfig().set("loc." + quest, section);
                sender.sendMessage("ok");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
                String nowQuest = getConfig().getString("data.quest." + p.getUniqueId().toString());
                if (nowQuest != null && !nowQuest.isEmpty()) {
                    p.sendMessage(getLang("lang3").replace("%doing%", getQuestDisplay(nowQuest).getString("doing")));
                    return true;
                }
                if (getTodayRepeat(p.getUniqueId()) >= getConfig().getInt("repeat")) {//已经达到最大任务数
                    p.sendMessage(getLang("lang4"));
                    return true;
                }
                if (getTodayFail(p.getUniqueId()) >= getConfig().getInt("fail")) {
                    p.sendMessage(getLang("lang5"));
                    return true;
                }
                String quest = randQuest();
                ConfigurationSection questDisplay = getQuestDisplay(quest);
                getConfig().set("data.time." + p.getUniqueId().toString(), System.currentTimeMillis());
                getConfig().set("data.quest." + p.getUniqueId().toString(), quest);
                setTodayRepeat(p.getUniqueId(), getTodayRepeat(p.getUniqueId()) + 1);
                p.sendMessage(getLang("lang1").replace("%name%", questDisplay.getString("name"))
                        .replace("%doing%", questDisplay.getString("doing"))
                        .replace("%time%", getConfig().getString("time")));
                if (getConfig().isSet("loc." + quest)) {
                    ConfigurationSection locCon = getConfig().getConfigurationSection("loc." + quest);
                    Location loc = new Location(Bukkit.getWorld(locCon.getString("world")), locCon.getInt("x"), locCon.getInt("y"), locCon.getInt("z"));
                    p.sendMessage(getConfig().getString("lang11").replace("{x}", String.valueOf(loc.getX()))
                            .replace("{y}", String.valueOf(loc.getY()))
                            .replace("{z}", String.valueOf(loc.getZ()))
                            .replace("{world}", String.valueOf(loc.getWorld().getName())));
                }
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("finish")) {
                ItemStack item = p.getItemInHand();
                String questName = getConfig().getString("data.quest." + p.getUniqueId().toString());
                if (questName == null) {
                    p.sendMessage(getLang("lang9"));
                    return true;
                }
                ConfigurationSection quest = getConfig().getConfigurationSection("quest." + questName);
                String[] itemId = quest.getString("item").split(":");
                if (itemId.length == 1) {
                    if (Integer.valueOf(itemId[0]) != item.getTypeId()) {
                        p.sendMessage(getLang("lang6"));
                        return true;
                    }
                }
                if (itemId.length >= 2) {
                    if (Integer.valueOf(itemId[0]) != item.getTypeId() || Integer.valueOf(itemId[1]) != item.getDurability()) {
                        p.sendMessage(getLang("lang6"));
                        return true;
                    }
                }
                if (quest.getInt("amount") > item.getAmount()) {
                    p.sendMessage(getLang("lang7"));
                    return true;
                }
                if (getConfig().isSet("loc." + questName)) {
                    ConfigurationSection locCon = getConfig().getConfigurationSection("loc." + questName);
                    Location loc = new Location(Bukkit.getWorld(locCon.getString("world")), locCon.getInt("x"), locCon.getInt("y"), locCon.getInt("z"));
                    if (!p.getLocation().getWorld().getName().equalsIgnoreCase(loc.getWorld().getName())) {
                        p.sendMessage(getLang("lang10").replace("{x}", String.valueOf(loc.getX())).replace("{y}", String.valueOf(loc.getY()))
                                .replace("{z}", String.valueOf(loc.getZ()))
                                .replace("{world}", String.valueOf(loc.getWorld().getName())));
                        return true;
                    }
                    if (p.getLocation().distance(loc) > 5) {
                        p.sendMessage(getLang("lang10").replace("{x}", String.valueOf(loc.getX()))
                                .replace("{y}", String.valueOf(loc.getY()))
                                .replace("{z}", String.valueOf(loc.getZ()))
                                .replace("{world}", String.valueOf(loc.getWorld().getName())));
                        return true;
                    }
                }


                int resultAmount = item.getAmount() - quest.getInt("amount");
                item.setAmount(resultAmount);
                if (resultAmount <= 0) item.setType(Material.AIR);
                p.setItemInHand(item);
                getConfig().set("data.time." + p.getUniqueId().toString(), null);
                getConfig().set("data.quest." + p.getUniqueId().toString(), null);
                p.sendMessage(getLang("lang8"));
                this.setTodayFail(p.getUniqueId(), 0);
                boolean op = p.isOp();
                try {
                    if (!op) p.setOp(true);
                    getConfig().getStringList("command").forEach(commandStr -> {
                        p.performCommand(commandStr.replace("<player>", p.getName()));
                    });
                } finally {
                    if (!op) p.setOp(false);
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            saveConfig();
        }
        return true;
    }

    public String randQuest() {
        List<String> quests = getConfig().getStringList("quests");
        String quest = quests.get(new Random(System.currentTimeMillis()).nextInt(quests.size()));
        return quest;
    }

    public ConfigurationSection getQuestDisplay(String quest) {
        return getConfig().getConfigurationSection("display." + quest);
    }

    public String getLang(String lang) {
        return getConfig().getString(lang);
    }

    public int getTodayRepeat(UUID player) {
        return getConfig().getInt("data.repeat." + player.toString() + "-" + (new SimpleDateFormat("yyyyMMdd").format(new Date())));
    }

    public void setTodayRepeat(UUID player, int repeat) {
        //new SimpleDateFormat("yyyyMMdd").format(new Date())
        getConfig().set("data.repeat." + player.toString() + "-" + (new SimpleDateFormat("yyyyMMdd").format(new Date())), repeat);
    }

    public int getTodayFail(UUID player) {
        return getConfig().getInt("data.fail." + player.toString() + "-" + (new SimpleDateFormat("yyyyMMdd").format(new Date())));
    }

    public void setTodayFail(UUID player, int repeat) {
        getConfig().set("data.fail." + player.toString() + "-" + (new SimpleDateFormat("yyyyMMdd").format(new Date())), repeat);
    }
}

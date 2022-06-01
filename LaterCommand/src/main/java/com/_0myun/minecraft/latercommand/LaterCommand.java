package com._0myun.minecraft.latercommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public final class LaterCommand extends JavaPlugin {
    public static LaterCommand INSTANCE;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
        new Runner().runTaskTimerAsynchronously(this, 10, 10);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("格式/lc 延迟秒数 命令名称");
            return true;
        }
        Player p = (Player) sender;
        long time = Long.valueOf(args[0]);
        String argCommand = args[1];
        if (getLaterCommand(argCommand) == null) {
            sender.sendMessage("没有这个命令");
            return true;
        }
        addPlayerLaterData(p.getUniqueId(), String.valueOf(System.currentTimeMillis() + (time * 1000l)) + ":" + argCommand);
        p.sendMessage("延迟指令设置成功...");
        saveConfig();
        return true;
    }

    public String getLaterCommand(String name) {
        return getConfig().getString("commands." + name);
    }

    public synchronized List<String> getPlayerLaterData(UUID p) {
        List<String> result = getConfig().getStringList("data." + p.toString());
        if (result == null) {
            getConfig().createSection("data." + p.toString());
            result = getConfig().getStringList("data." + p.toString());
        }
        return result;
    }

    public synchronized void removePlayerLaterData(UUID p, String str) {
        List<String> data = getPlayerLaterData(p);
        data.remove(str);
        getConfig().set("data." + p.toString(), data);
    }

    public synchronized void addPlayerLaterData(UUID p, String str) {
        List<String> data = getPlayerLaterData(p);
        data.add(str);
        getConfig().set("data." + p.toString(), data);
    }

}

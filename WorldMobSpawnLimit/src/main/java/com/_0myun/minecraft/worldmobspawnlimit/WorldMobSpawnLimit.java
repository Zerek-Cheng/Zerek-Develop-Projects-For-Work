package com._0myun.minecraft.worldmobspawnlimit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldMobSpawnLimit extends JavaPlugin {
    public static WorldMobSpawnLimit INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new com._0myun.minecraft.worldmobspawnlimit.Listener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("ok");
        reloadConfig();
        return true;
    }

}

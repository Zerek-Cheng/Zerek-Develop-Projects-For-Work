package com._0myun.minecraft.blockrandomcommands;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        SqliteManager.init();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        new Souter().runTaskLater(this, 1);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage("reload ok");
        }
        return true;
    }
}

package com._0myun.minecraft.lycanitescleaner;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Main extends JavaPlugin implements Listener {
    BukkitTask task;
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        task = new Cleaner().runTaskTimerAsynchronously(this, getConfig().getInt("time") * 20, getConfig().getInt("time") * 20);
    }

    @EventHandler
    public void onDebug(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (!getConfig().getBoolean("debug")) return;
        if (!p.isOp()) return;
        Entity entity = e.getRightClicked();
        p.sendMessage("entity.getName() = " + entity.getName());
        p.sendMessage("entity.getType() = " + entity.getType());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("reload ok");
        return true;
    }
}

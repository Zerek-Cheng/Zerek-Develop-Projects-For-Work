package com._0myun.minecraft.noname;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main
        extends JavaPlugin
        implements Listener {
    int a;
    int b;
    int c;
    int d;
    int e;

    public void onEnable() {
        this.getLogger().info("§6§l==========");
        this.getLogger().info("§6§l已开启");
        this.getLogger().info("§6§l==========");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public void onLoad() {
        this.getLogger().info("§lnoname已重载");
    }

    public void onDisable() {
        this.getLogger().info("§lnoname已关闭");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        e.getPlayer().setSneaking(true);
    }

    @EventHandler
    public void onTp(PlayerTeleportEvent e) {
        e.getPlayer().setSneaking(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().setSneaking(true);
    }
}


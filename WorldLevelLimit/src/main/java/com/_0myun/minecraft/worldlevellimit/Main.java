package com._0myun.minecraft.worldlevellimit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().warning("定制插件+q2025255093");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("重载ok");
        return true;
    }

    @EventHandler
    public void onTP(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();
        int level = p.getLevel();
        if (level < getMin(to.getWorld().getName()) || level > getMax(to.getWorld().getName())) {
            p.sendMessage(getConfig().getString("lang1"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        Location to = p.getLocation();
        int level = p.getLevel();
        if (level < getMin(to.getWorld().getName()) || level > getMax(to.getWorld().getName())) {
            p.sendMessage(getConfig().getString("lang1"));
            p.teleport(getDefaultLocation());
        }
    }

    public int getMin(String world) {
        return getConfig().getInt("worlds." + world + ".min");
    }

    public int getMax(String world) {
        return getConfig().getInt("worlds." + world + ".max");
    }

    public Location getDefaultLocation() {
        return new Location(Bukkit.getWorld(getConfig().getString("default.world")),
                getConfig().getInt("default.x"),
                getConfig().getInt("default.y"),
                getConfig().getInt("default.z"));
    }
}

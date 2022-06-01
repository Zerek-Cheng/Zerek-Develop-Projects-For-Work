package com._0myun.minecraft.eventmsg.debugback;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {
    private HashMap<UUID, Location> deathLoc = new HashMap<>();
    private HashMap<UUID, Boolean> death = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }
    // Plugin shutdown logic

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        this.deathLoc.put(p.getUniqueId(), p.getLocation().clone());
        this.death.put(p.getUniqueId(), true);
    }

    @EventHandler
    public void onTp(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();
        if (p.isDead()) {
            return;
        }
        //this.deathLoc.put(p.getUniqueId(), from);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String command = e.getMessage();
        if (!command.equalsIgnoreCase("/back")) {
            return;
        }
        if (!this.death.containsKey(p.getUniqueId()))return;
        if (!this.death.get(p.getUniqueId())) {
            return;
        }
        this.death.put(p.getUniqueId(), false);
        e.setCancelled(true);
        p.teleport(this.deathLoc.get(p.getUniqueId()));
        p.sendMessage("正在回到上次传送地点...");
    }
}

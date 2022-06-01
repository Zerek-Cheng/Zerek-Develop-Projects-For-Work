package com.lmyun.minecraft.event.playerheadtitle;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener {
    Main main;

    public Listener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        Location from = e.getFrom();
        Location to = e.getTo();
        if (from.getX() == to.getX() &&
                from.getY() == to.getY() &&
                from.getZ() == to.getZ()) {
            return;
        }
        if (!this.main.titleEntities.containsKey(p.getUniqueId())) {
            this.main.spawnTitleEntities(p.getUniqueId());
        }
        TitleEntitiesManager titleEntitiesManager = this.main.titleEntities.get(p.getUniqueId());
        if (titleEntitiesManager.isKilled()) {
            this.main.spawnTitleEntities(p.getUniqueId());
        }
        titleEntitiesManager.tp(p);
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        this.main.removeTitleEntities(p.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        this.main.removeTitleEntities(e.getPlayer().getUniqueId());
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.listener;

import com.github.shawhoi.nybattle.Main;
import com.github.shawhoi.nybattle.playerdata.PlayerData;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PlayerDamage implements Listener {
    private ArrayList<String> damages;

    public PlayerDamage() {
        this.damages = new ArrayList<String>();
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {
        if (PlayerData.PlayerInGame(e.getEntity())) {
            PlayerData.getPlayerGameArena(e.getEntity()).PlayerDeath(e.getEntity());
            if (e.getEntity().getKiller() instanceof Player) {
                final Player p = e.getEntity().getKiller();
                if (PlayerData.PlayerInGame(p)) {
                    PlayerData.getPlayerGameArena(p).addKill(p.getName());
                }
            }
            new BukkitRunnable() {
                public void run() {
                    e.getEntity().spigot().respawn();
                }
            }.runTaskLater((Plugin) Main.getInstance(), 5L);
        }
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent e) {
        if (PlayerData.PlayerInGame(e.getPlayer())) {
            PlayerData.getPlayerGameArena(e.getPlayer()).PlayerLeave(e.getPlayer(), false);
        }
    }

    @EventHandler
    public void onPlayerDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player) e.getEntity();
            if (PlayerData.PlayerInGame(p)) {
                if (!PlayerData.getPlayerGameArena(p).getArenaState().equals("START")) {
                    e.setCancelled(true);
                } else if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION && PlayerData.getPlayerGameArena((Player) e.getEntity()).isInside((Player) e.getEntity())) {
                    if (this.damages.contains(p.getName())) {
                        e.setCancelled(true);
                        return;
                    }
                    e.setDamage(PlayerData.getPlayerGameArena((Player) e.getEntity()).getDamage());
                    new BukkitRunnable() {
                        public void run() {
                            PlayerDamage.this.damages.remove(p.getName());
                        }
                    }.runTaskLaterAsynchronously((Plugin) Main.getInstance(), 20L);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerHunger(final FoodLevelChangeEvent e) {
        final Player p = (Player) e.getEntity();
        if (PlayerData.PlayerInGame(p) && !PlayerData.getPlayerGameArena(p).getArenaState().equals("START")) {
            e.setCancelled(true);
            e.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player) e.getEntity();
            if (PlayerData.PlayerInGame(p) && !PlayerData.getPlayerGameArena(p).getArenaState().equals("START")) {
                e.setCancelled(true);
            }
        }
        LivingEntity entity = (LivingEntity) e.getEntity();
        if (e.getEntity() instanceof Chicken && entity.getCustomName() != null && entity.getCustomName().equals("NyBattle")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerUnleashEntity(final PlayerUnleashEntityEvent e) {
        LivingEntity entity = (LivingEntity) e.getEntity();
        if (entity.getCustomName() != null && entity.getCustomName().equals("NyBattle")) {
            e.setCancelled(true);
        }
    }
}

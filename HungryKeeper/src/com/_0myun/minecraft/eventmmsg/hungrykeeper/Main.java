package com._0myun.minecraft.eventmmsg.hungrykeeper;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {
    HashMap<UUID, Integer> foodLevel = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        foodLevel.put(p.getUniqueId(), p.getFoodLevel());
        startDebug(p);
        /*new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    this.cancel();
                    return;
                }
                Integer realFood = foodLevel.get(p.getUniqueId());
                int nowFood = p.getFoodLevel();
                if (nowFood < realFood) {
                    p.damage(realFood - nowFood);
                    p.setFoodLevel(realFood);
                }
            }
        }.runTaskTimerAsynchronously(this, 0, 5);*/
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        int change = p.getFoodLevel() - e.getFoodLevel();
        foodLevel.put(p.getUniqueId(), foodLevel.get(p.getUniqueId()) + change);
    }

    @EventHandler
    public void onReLiving(PlayerRespawnEvent e) {
        foodLevel.put(e.getPlayer().getUniqueId(), 20);
        startDebug(e.getPlayer());
    }

    public void startDebug(Player p) {
        double health = p.getHealth();
        int foodLevel = p.getFoodLevel();
        boolean op = p.isOp();
        try {
            p.setOp(true);
            //p.setFoodLevel(0);
            p.performCommand("heal");
            p.setHealth(health);
            p.setFoodLevel(foodLevel);
            p.updateInventory();
        } finally {
            if (!op) {
                p.setOp(false);
            }
        }
        p.setSaturation(0);
    }
}

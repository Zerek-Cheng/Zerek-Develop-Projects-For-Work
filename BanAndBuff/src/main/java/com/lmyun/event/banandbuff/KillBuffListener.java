package com.lmyun.event.banandbuff;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillBuffListener implements Listener {
    private Main plugin;

    public KillBuffListener(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onBeKilled(PlayerDeathEvent e) {
        Player p = e.getEntity();
        this.plugin.setKill(p.getName(), (short) 0);
    }

    @EventHandler
    public void onKill(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity entity = e.getEntity();
        if (!(damager instanceof Player) || !(entity instanceof Player)) {
            return;
        }
        if (e.getFinalDamage() <= ((Player) entity).getHealth()) {
            return;
        }
        Player p = (Player) damager;
        this.plugin.addKill(p.getName());
        this.plugin.runKill(p);
    }

}

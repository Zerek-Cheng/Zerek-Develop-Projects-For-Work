package com.lmyun.minecraft.event.petskillforplayer;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onKill(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (!(damager instanceof Wolf)) {
            return;
        }
        Wolf pet = (Wolf) damager;
        AnimalTamer owner = pet.getOwner();
        if (owner == null || owner.getUniqueId() == null) {
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(owner.getUniqueId());
        if (!offlinePlayer.isOnline()) {
            return;
        }
        Player p = offlinePlayer.getPlayer();
        e.setCancelled(true);
        LivingEntity entity = (LivingEntity) e.getEntity();
        entity.damage(e.getDamage(), p);
        //EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(p, e.getEntity(), e.getCause(), e.getDamage());
        //Bukkit.getPluginManager().callEvent(event);
    }


}

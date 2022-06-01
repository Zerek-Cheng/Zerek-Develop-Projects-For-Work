package com._0myun.minecraft.mobslocationattribute;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobsLocationAttribute extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        Location loc = e.getLocation();
        if (!getConfig().getStringList("worlds").contains(loc.getWorld().getName())) return;

        LivingEntity entity = e.getEntity();
        double dis = Math.abs(Math.max(loc.getX(), loc.getZ()));
        int mult = Double.valueOf(dis / getConfig().getInt("interval")).intValue();
        double attribute = getConfig().getDouble("attribute");
        entity.setMaxHealth(entity.getMaxHealth() + ((mult * attribute) * entity.getMaxHealth()));
        entity.setHealth(entity.getMaxHealth()
        );
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Location loc = damager.getLocation();
        if (!(damager instanceof LivingEntity)) return;
        if (damager instanceof Player || damager instanceof HumanEntity) return;

        if (!getConfig().getStringList("worlds").contains(loc.getWorld().getName())) return;
        double dis = Math.abs(Math.max(loc.getX(), loc.getZ()));
        int mult = Double.valueOf(dis / getConfig().getInt("interval")).intValue();
        double attribute = getConfig().getDouble("attribute");
        e.setDamage(e.getDamage() + ((mult * attribute) * e.getDamage()));
    }

}

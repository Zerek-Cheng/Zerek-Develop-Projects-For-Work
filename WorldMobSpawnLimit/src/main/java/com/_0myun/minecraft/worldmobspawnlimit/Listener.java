package com._0myun.minecraft.worldmobspawnlimit;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        Location loc = e.getLocation();
        Entity entity = e.getEntity();
        if (!(entity instanceof LivingEntity)) return;
        if (WorldMobSpawnLimit.INSTANCE.getConfig().getBoolean("debug"))
            System.out.println("entity = " + entity.getType().toString());
        if (!WorldMobSpawnLimit.INSTANCE.getConfig().isSet("rules." + loc.getWorld().getName())) return;
        List<String> rules = WorldMobSpawnLimit.INSTANCE.getConfig().getStringList("rules." + loc.getWorld().getName());
        if (!rules.contains(entity.getType().toString())) return;
        e.setCancelled(true);
    }
}

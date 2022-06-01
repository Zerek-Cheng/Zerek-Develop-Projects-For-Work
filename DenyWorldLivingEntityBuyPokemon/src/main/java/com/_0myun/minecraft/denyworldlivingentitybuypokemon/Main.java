package com._0myun.minecraft.denyworldlivingentitybuypokemon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        Location loc = e.getLocation();
        LivingEntity entity = e.getEntity();
        if (getConfig().getBoolean("debug"))
            getLogger().log(Level.INFO, loc.getWorld().getName() + "----" + entity.getType().toString());
        if (!getConfig().getStringList("worlds").contains(loc.getWorld().getName())) return;
        if (getConfig().getStringList("denyEntity").contains(entity.getType().toString())) e.setCancelled(true);
    }
}

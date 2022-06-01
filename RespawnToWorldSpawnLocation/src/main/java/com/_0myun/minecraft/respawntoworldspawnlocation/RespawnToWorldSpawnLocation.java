package com._0myun.minecraft.respawntoworldspawnlocation;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.event.MVRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class RespawnToWorldSpawnLocation extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        if (!getConfig().getBoolean(loc.getWorld().getName())) return;
        MultiverseCore plugin = (MultiverseCore) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        Location spawnLocation = plugin.getMVWorldManager().getMVWorld(loc.getWorld()).getSpawnLocation();
        spawnLocation.setWorld(loc.getWorld());
        e.setRespawnLocation(spawnLocation);
        p.sendMessage("当前适用");
        for (RegisteredListener registeredListener : e.getHandlers().getRegisteredListeners()) {
        }
    }
}

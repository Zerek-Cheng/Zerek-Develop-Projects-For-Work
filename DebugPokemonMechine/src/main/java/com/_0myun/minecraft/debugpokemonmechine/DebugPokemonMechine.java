package com._0myun.minecraft.debugpokemonmechine;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class DebugPokemonMechine extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new High(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onClick(InventoryClickEvent e) {
        try {
            Inventory inv = e.getInventory();
            String name = inv.getName();
            String title = inv.getTitle();
            String holder = inv.getHolder().getClass().getName();
            int size = inv.getSize();
            if (!(name.isEmpty() && title.isEmpty() && holder.equalsIgnoreCase("catserver.server.inventory.CatCustomInventory") && size == 3))
                return;
            e.setCancelled(false);
            //catserver.server.inventory.CatCustomInventory
        } catch (Exception ex) {

        }
    }

}

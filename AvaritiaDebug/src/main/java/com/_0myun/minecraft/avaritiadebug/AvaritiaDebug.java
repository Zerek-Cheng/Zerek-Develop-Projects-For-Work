package com._0myun.minecraft.avaritiadebug;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AvaritiaDebug extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        int rawSlot = e.getRawSlot();
        if (!e.getInventory().getName().equalsIgnoreCase("container.neutronium_compressor")) return;
        if (e.getClick().toString().contains("SHIFT")) e.setCancelled(true);
    }
}
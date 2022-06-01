package com._0myun.minecraft.debugpokemonmechine;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class High implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void clickHigh(InventoryClickEvent high) {
        try {
            Inventory inv = high.getInventory();
            String name = inv.getName();
            String title = inv.getTitle();
            String holder = inv.getHolder().getClass().getName();
            int size = inv.getSize();
            if (!(name.isEmpty() && title.isEmpty() && holder.equalsIgnoreCase("catserver.server.inventory.CatCustomInventory") && size == 3))
                return;
            high.setCancelled(false);
            //catserver.server.inventory.CatCustomInventory
        }catch (Exception e){

        }
    }
}

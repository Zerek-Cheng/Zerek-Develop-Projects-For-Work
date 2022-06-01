package com.rb2750.restart;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.Listener;

public class EventHandle implements Listener
{
    Main plugin;

    public EventHandle(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(final InventoryCloseEvent event) {
        if (event.getInventory().getTitle().contains("Backpack")) {
            String inventory = "";
            for (int i = 0; i < event.getInventory().getSize(); ++i) {
                if (event.getInventory().getItem(i) != null) {
                    if (inventory != "") {
                        inventory = inventory + ",";
                    }
                    inventory = inventory + event.getInventory().getItem(i).getTypeId() + ":" + event.getInventory().getItem(i).getDurability() + ";" + event.getInventory().getItem(i).getAmount();
                }
            }
            this.plugin.getConfig().set("Players." + event.getPlayer().getName() + ".Inventory", inventory);
            this.plugin.saveConfig();
        }
    }
}

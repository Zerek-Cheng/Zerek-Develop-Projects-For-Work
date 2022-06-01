package com._0myun.minecraft.denyoilrefinerieschest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player whoClicked = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        if (whoClicked.isOp() && getConfig().getBoolean("debug"))
            whoClicked.sendMessage("inv.getName() = " + inv.getName());

        if (getConfig().getStringList("list").contains(inv.getName())) {
            whoClicked.sendMessage(getConfig().getString("sbdaoju"));
            e.setCancelled(true);
        }
    }
}

package com._0myun.minecraft.banitemshower;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class UiListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        int rawSlot = e.getRawSlot();
        if (!(inv.getHolder() instanceof ShowerHolder)) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
        ShowerHolder holder = (ShowerHolder) inv.getHolder();
        int page = holder.getPage();
        if (rawSlot == 0) {
            int to = page - 1;
            p.performCommand("BanitemShower " + to);
        } else if (rawSlot == 53) {
            int to = page + 1;
            p.performCommand("BanitemShower " + to);
        }
    }
}

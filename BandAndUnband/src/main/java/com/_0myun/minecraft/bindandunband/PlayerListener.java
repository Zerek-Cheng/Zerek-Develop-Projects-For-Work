package com._0myun.minecraft.bindandunband;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    @EventHandler
    public void onGetItem(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();
        if (!Main.plugin.isBind(item)) return;//没有绑定
        if (p.getUniqueId().toString().equalsIgnoreCase(Main.plugin.getBinder(item))
                || Main.plugin.getOwner(item).equalsIgnoreCase(p.getUniqueId().toString())) return;//绑定人是自己
        if (!Main.plugin.getOwner(item).isEmpty() && !Main.plugin.getOwner(item).equalsIgnoreCase(p.getUniqueId().toString())) {//不为空且不是自己就取消
            //e.setCancelled(true);
            p.sendMessage(Main.plugin.getConfig().getString("lang6"));
            return;
        }
        Main.plugin.setOwner(item, p);
        p.sendMessage(Main.plugin.getConfig().getString("lang5"));
    }

    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        int raw = e.getRawSlot();
        ItemStack item = e.getCurrentItem();

        if (!Main.plugin.isBind(item)) return;//没有绑定
        if (p.getUniqueId().toString().equalsIgnoreCase(Main.plugin.getBinder(item))
                || Main.plugin.getBinder(item).equalsIgnoreCase(p.getUniqueId().toString())) return;//绑定人是自己
        if (!Main.plugin.getOwner(item).isEmpty() && !Main.plugin.getOwner(item).equalsIgnoreCase(p.getUniqueId().toString())) {//不为空且不是自己就取消
            e.setCancelled(true);
            p.sendMessage(Main.plugin.getConfig().getString("lang6"));
            return;
        }
    }
}

package com._0myun.minecraft.eventmsg.auction.inv;

import com._0myun.minecraft.eventmsg.auction.AuctionManager;
import com._0myun.minecraft.eventmsg.auction.pay.PayType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public class UiListener implements Listener {
    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (!(holder instanceof UiHolder)) return;
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        int slot = e.getRawSlot();
        if (!(inv.getHolder() instanceof UiHolder)) return;
        e.setCancelled(true);
        if (!AuctionManager.isStart()) return;
        if (slot < 37 || slot > 43) return;
        PayType payType = AuctionManager.getNowPayType();
        HashMap<Integer, Integer> premiums = payType.getPremiums();
        Integer amount = premiums.get(slot - 37);
        if (amount == null || amount == 0) return;
        AuctionManager.offerAdd((Player) e.getWhoClicked(), amount);
    }
}

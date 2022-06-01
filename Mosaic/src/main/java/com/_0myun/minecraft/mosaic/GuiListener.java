package com._0myun.minecraft.mosaic;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        int rawSlot = e.getRawSlot();
        if (rawSlot > inv.getSize()) return;
        ItemStack currentItem = e.getCurrentItem();
        ItemStack cursor = e.getCursor();
        if (!(inv.getHolder() instanceof GemGuiHolder)) return;
        GemGuiHolder holder = (GemGuiHolder) inv.getHolder();
        if (e.getClick().equals(ClickType.SHIFT_RIGHT) || e.getClick().equals(ClickType.SHIFT_LEFT))
            e.setCancelled(true);
        if (currentItem != null && !currentItem.getType().equals(Material.AIR)) e.setCancelled(true);
        if (e.getClick().equals(ClickType.SHIFT_LEFT)) e.setCancelled(true);
        if (e.getClick().equals(ClickType.SHIFT_RIGHT)) e.setCancelled(true);
        if (!Mosaic.INSTANCE.isSlotCanOpen(holder.getItemStack(), rawSlot) && !Mosaic.INSTANCE.isSlotOpen(holder.getItemStack(), rawSlot))
            e.setCancelled(true);
        if (rawSlot >= Mosaic.INSTANCE.getMax() || rawSlot < 0) return;
        if (Mosaic.INSTANCE.isSlotCanOpen(holder.getItemStack(), rawSlot)) {
            if (!Mosaic.INSTANCE.isSlotOpenItem(cursor)) {
                p.sendMessage(LangManager.get("lang5"));
                return;
            }
            ItemStack clone = cursor.clone();
            cursor.setAmount(cursor.getAmount() - 1);
            if (Math.random() < Mosaic.INSTANCE.getOpenSuccessRand()) {//success
                Mosaic.INSTANCE.openSlot(holder.itemStack, rawSlot);
                inv.setItem(rawSlot, null);
                holder.init();
                p.sendMessage(LangManager.get("lang9"));
                return;
            }
            if (Math.random() < Mosaic.INSTANCE.getConfig().getDouble("break-rand-when-open-filed")
                    && !Mosaic.INSTANCE.isSlotSafeOpenItem(clone)) {//failed and break
                Mosaic.INSTANCE.breakSlot(holder.itemStack, rawSlot);
                p.sendMessage(LangManager.get("lang7"));
                holder.init();
                return;
            }

            holder.init();
            p.sendMessage(LangManager.get("lang6"));
            return;
        }
        if (Mosaic.INSTANCE.isSlotOpen(holder.getItemStack(), rawSlot)) {
            String gemType = Mosaic.INSTANCE.getGemType(cursor);
            if (gemType == null || gemType.isEmpty()) {
                p.sendMessage(LangManager.get("lang10"));
                e.setCancelled(true);
                return;
            }
            if (!Mosaic.INSTANCE.canUseGem(holder.itemStack, gemType)) {
                p.sendMessage(LangManager.get("lang11"));
                e.setCancelled(true);
                return;
            }
            ItemStack clone = cursor.clone();
            clone.setAmount(1);
            Mosaic.INSTANCE.setSlotGemData(holder.itemStack, rawSlot, clone);
            Mosaic.INSTANCE.setSlotStatus(holder.itemStack, rawSlot, 2);
            ItemsManager.updateLores(holder.itemStack);
            p.sendMessage(LangManager.get("lang12"));
            e.setCancelled(true);
            cursor.setAmount(cursor.getAmount() - 1);
            holder.init();
        }

    }

    @EventHandler
    public void onClick(InventoryDragEvent e) {
        Inventory inv = e.getInventory();
        if (!(inv.getHolder() instanceof GemGuiHolder)) return;
        e.setCancelled(true);
    }
}

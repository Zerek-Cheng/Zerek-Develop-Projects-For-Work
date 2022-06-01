package com._0myun.minecraft.slotrules;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerChecker extends BukkitRunnable {

    private final Player p;

    public PlayerChecker(Player p) {
        this.p = p;
    }
/*
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        InventoryView view = e.getView();
        Inventory eInv = e.getInventory();
        Player p = (Player) e.getWhoClicked();
        PlayerInventory inv = p.getInventory();
        ItemStack itemStack = e.getCursor();

        int rawSlot = e.getRawSlot();
        if (!view.getTopInventory().getType().equals(InventoryType.CRAFTING) && rawSlot > view.getTopInventory().getSize() - 1){
            rawSlot -= view.getTopInventory().getSize();
            rawSlot+=9;
        }
        rawSlot += 9;
        int slota = e.getSlot();

        if (!Main.INSTANCE.existRule(rawSlot)) return;
//        System.out.println("e.getClick() = " + e.getClick());
        ItemStack cursor = e.getCursor();

        if (!Main.INSTANCE.check(rawSlot, cursor)) {
            int slot = getAllowSlot(inv, itemStack);
            if (slot != -1) {
                e.setCursor(null);
                inv.setItem(slot, itemStack);
                p.sendMessage(Main.INSTANCE.getConfig().getString("lang1"));
                p.updateInventory();
                return;
            }
            e.setCursor(null);
            p.getLocation().getWorld().dropItem(p.getLocation(), itemStack);
            p.sendMessage(Main.INSTANCE.getConfig().getString("lang2"));
            p.updateInventory();
            return;
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        ItemStack cursor = e.getCursor();
        e.getRawSlots().forEach(slot -> {
            if (Main.INSTANCE.check(slot, cursor)) e.setCancelled(true);
        });
    }*/

    @Override
    public synchronized void run() {
        if (!this.p.isOnline()) {
            this.cancel();
            return;
        }
        PlayerInventory inv = p.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            if (!Main.INSTANCE.existRule(i)) continue;
            ItemStack itemStack = inv.getItem(i);
            boolean allow = Main.INSTANCE.check(i, itemStack);
            if (allow) continue;
            System.out.println(11111);
            int slot = getAllowSlot(inv, itemStack);
            if (slot != -1) {
                inv.setItem(i, null);
                inv.setItem(slot, itemStack);
                p.sendMessage(Main.INSTANCE.getConfig().getString("lang1"));
                p.updateInventory();
                return;
            }
            inv.setItem(i, null);
            p.getLocation().getWorld().dropItem(p.getLocation(), itemStack);
            p.sendMessage(Main.INSTANCE.getConfig().getString("lang2"));
            p.updateInventory();
        }
    }

    int getAllowSlot(PlayerInventory inv, ItemStack itemStack) {
        int result = -1;
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null || item.getType().equals(Material.AIR)) {
                if (Main.INSTANCE.check(i, itemStack)) result = i;
                if (result != -1) return result;
            }
        }
        return result;
    }
}

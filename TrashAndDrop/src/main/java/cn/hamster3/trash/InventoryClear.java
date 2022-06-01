/*
 * Decompiled with CFR 0_133.
 * 
 * Could not onEnbale_Trash the following classes:
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.scheduler.BukkitRunnable
 */
package cn.hamster3.trash;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryClear
extends BukkitRunnable {
    private Inventory inventory;
    private int cooldown;
    private ItemStack barrier;
    private String DisplayName;
    private int ticks;

    public InventoryClear(Inventory inventory, int cooldown, ItemStack barrier, String displayName) {
        this.inventory = inventory;
        this.cooldown = cooldown;
        this.barrier = barrier;
        this.DisplayName = displayName;
        this.ticks = 0;
    }

    public void run() {
        int time;
        ++this.ticks;
        ItemStack stack = this.inventory.getItem(0);
        if (stack == null) {
            stack = this.barrier.clone();
            this.inventory.setItem(0, stack);
        }
        if ((time = this.cooldown - this.ticks) < 0) {
            this.inventory.clear();
            this.inventory.setItem(0, this.barrier.clone());
            this.ticks = 0;
        } else {
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(this.DisplayName + time);
            stack.setItemMeta(stackMeta);
        }
    }
}


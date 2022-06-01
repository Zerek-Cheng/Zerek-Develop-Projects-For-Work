package com._0myun.minecraft.crazycrates.debugkey;

import me.badbones69.crazycrates.api.CrazyCrates;
import org.bukkit.Bukkit;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Checker extends BukkitRunnable {
    @Override
    public void run() {
        CrazyCrates api = CrazyCrates.getInstance();
        Bukkit.getOnlinePlayers().forEach(p -> {
            EntityEquipment eq = p.getEquipment();
            if (api.isKey(eq.getHelmet())) {
                p.getInventory().addItem(eq.getHelmet());
                eq.setHelmet(new ItemStack(0));
                p.updateInventory();
                p.sendMessage("钥匙不能被装备!已放到你的背包中");
            }
            if (api.isKey(eq.getChestplate())) {
                p.getInventory().addItem(eq.getChestplate());
                eq.setChestplate(new ItemStack(0));
                p.updateInventory();
                p.sendMessage("钥匙不能被装备!已放到你的背包中");
            }
            if (api.isKey(eq.getLeggings())) {
                p.getInventory().addItem(eq.getLeggings());
                eq.setLeggings(new ItemStack(0));
                p.updateInventory();
                p.sendMessage("钥匙不能被装备!已放到你的背包中");
            }
            if (api.isKey(eq.getBoots())) {
                p.getInventory().addItem(eq.getBoots());
                eq.setBoots(new ItemStack(0));
                p.updateInventory();
                p.sendMessage("钥匙不能被装备!已放到你的背包中");
            }
            if (api.isKey(eq.getItemInOffHand())) {
                p.getInventory().addItem(eq.getItemInOffHand());
                eq.setItemInOffHand(new ItemStack(0));
                p.updateInventory();
                p.sendMessage("钥匙不能被装备!已放到你的背包中");
            }
        });
    }
}

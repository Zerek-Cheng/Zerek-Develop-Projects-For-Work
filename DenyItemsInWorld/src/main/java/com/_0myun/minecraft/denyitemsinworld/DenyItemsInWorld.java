package com._0myun.minecraft.denyitemsinworld;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class DenyItemsInWorld extends JavaPlugin implements Listener {
    public static DenyItemsInWorld plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        new Checker().runTaskTimer(this, 5, 5);
    }


    public void check(Player p) {
        PlayerInventory inv = p.getInventory();
        EntityEquipment eq = p.getEquipment();
        ItemStack helmet = eq.getHelmet();
        ItemStack boots = eq.getBoots();
        ItemStack chestplate = eq.getChestplate();
        ItemStack leggings = eq.getLeggings();
        boolean take = false;
        if (isDeny(p.getWorld().getName(), helmet)) {
            eq.setHelmet(new ItemStack(0));
            inv.addItem(helmet);
            take = !take;
        }
        if (isDeny(p.getWorld().getName(), boots)) {
            eq.setBoots(new ItemStack(0));
            inv.addItem(boots);
            take = !take;
        }
        if (isDeny(p.getWorld().getName(), chestplate)) {
            eq.setChestplate(new ItemStack(0));
            inv.addItem(chestplate);
            take = !take;
        }
        if (isDeny(p.getWorld().getName(), leggings)) {
            eq.setLeggings(new ItemStack(0));
            inv.addItem(leggings);
            take = !take;
        }
        if (take) p.sendMessage(getConfig().getString("lang1"));
    }

    public boolean isDeny(String world, ItemStack itemStack) {
        List<String> rules = getConfig().getStringList("rule." + world);
        if (rules == null) return false;
        if (itemStack == null) return false;
        if (rules.contains(String.valueOf(itemStack.getTypeId()))
                || rules.contains(String.valueOf(itemStack.getTypeId()) + ":" + String.valueOf(itemStack.getDurability()))
                || rules.contains(String.valueOf(itemStack.getTypeId()) + ":" + "*")) return true;
        return false;
    }
}

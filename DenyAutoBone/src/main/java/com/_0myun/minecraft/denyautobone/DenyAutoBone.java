package com._0myun.minecraft.denyautobone;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class DenyAutoBone extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onUse(BlockDispenseEvent e) {
        ItemStack item = e.getItem();
        if (item.getTypeId()==351&&item.getDurability()==15) e.setCancelled(true);
    }
}

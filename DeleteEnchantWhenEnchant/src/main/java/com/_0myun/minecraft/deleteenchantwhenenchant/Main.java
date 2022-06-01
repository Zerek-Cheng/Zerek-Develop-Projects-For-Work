package com._0myun.minecraft.deleteenchantwhenenchant;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.Map;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void on(EnchantItemEvent e) {
        Player p = e.getEnchanter();
        Map<Enchantment, Integer> adds = e.getEnchantsToAdd();
        Iterator<Enchantment> iter = adds.keySet().iterator();
        while (iter.hasNext()) {
            Enchantment next = iter.next();
            if (getConfig().getStringList("list").contains(next.getName())) iter.remove();
        }
    }
}

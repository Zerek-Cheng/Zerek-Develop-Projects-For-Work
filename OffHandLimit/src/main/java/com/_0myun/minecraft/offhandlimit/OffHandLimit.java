package com._0myun.minecraft.offhandlimit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public final class OffHandLimit extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    check(p);
                });
            }
        }.runTaskTimer(this, 20, 20);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onInv(InventoryClickEvent e) {
        check((Player) e.getWhoClicked());
        new BukkitRunnable() {

            @Override
            public void run() {
                check((Player) e.getWhoClicked());
            }
        }.runTaskLater(this, 10);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        check(e.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (damager instanceof Player) {
            check((Player) damager);
        }
    }

    public void check(Player p) {
        ItemStack itemInOffHand = p.getInventory().getItemInOffHand();
        if (itemInOffHand == null || itemInOffHand.getType().equals(Material.AIR)) return;
        if (isOffHand(itemInOffHand.getItemMeta().getLore())) return;
        p.getInventory().setItemInOffHand(new ItemStack(0));
        p.getInventory().addItem(itemInOffHand);
        p.sendMessage(getConfig().getString("lang"));
    }

    public boolean isOffHand(List<String> lores) {
        if (lores == null) return false;
        for (int i = 0; i < lores.size(); i++) {
            if (lores.get(i).contains(getConfig().getString("lore"))) return true;
        }
        return false;
    }
}

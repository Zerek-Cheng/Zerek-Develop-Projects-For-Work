package com._0myun.minecraft.invalidarmorwhensxdurabilitylose;

import github.saukiya.sxattribute.data.condition.SubCondition;
import github.saukiya.sxattribute.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class R extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        ItemStack item = p.getItemInHand();
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore()) return;
        for (String lore : item.getItemMeta().getLore()) {
            if (!lore.contains(Config.getConfig().getString(Config.NAME_DURABILITY))) continue;

            int durability = SubCondition.getDurability(lore);
            if (durability <= 1) {
                e.setDamage(0);
                p.sendTitle(getConfig().getString("lang.lang1"), "");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamageProjectile(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Projectile)) return;
        if (!(((Projectile) e.getDamager()).getShooter() instanceof Player)) return;
        Player p = (Player) ((Projectile) e.getDamager()).getShooter();
        ItemStack item = p.getItemInHand();
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore()) return;
        for (String lore : item.getItemMeta().getLore()) {
            if (!lore.contains(Config.getConfig().getString(Config.NAME_DURABILITY))) continue;

            int durability = SubCondition.getDurability(lore);
            if (durability <= 1) {
                e.setDamage(0);
                p.sendTitle(getConfig().getString("lang.lang1"), "");
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onDamaged(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) && !(e.getEntity() instanceof Projectile)) return;
        Player p = (Player) e.getEntity();
        EntityEquipment eq = p.getEquipment();
        double attribute = 1;
        for (ItemStack item : eq.getArmorContents()) {
            if (!item.hasItemMeta() || !item.getItemMeta().hasLore()) continue;
            for (String lore : item.getItemMeta().getLore()) {
                if (!lore.contains(Config.getConfig().getString(Config.NAME_DURABILITY))) continue;

                int durability = SubCondition.getDurability(lore);
                if (durability <= 1) {
                    attribute += 0.2;
                    p.sendTitle(getConfig().getString("lang.lang2"), "");
                }
            }
        }
        if (attribute == 1) return;
        e.setDamage(Double.valueOf(e.getDamage() * attribute).intValue());
    }
}

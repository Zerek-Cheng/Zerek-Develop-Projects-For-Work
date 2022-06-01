package com._0myun.minecraft.keepbleed;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public final class KeepBleed extends JavaPlugin implements Listener {

    public static void main(String[] args) {

    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        try {
            Entity entity = e.getEntity();
            if (!(entity instanceof LivingEntity)) return;
            LivingEntity livingEntity = (LivingEntity) entity;
            Entity damager = e.getDamager();
            if (!(damager instanceof Player)) return;
            Player p = (Player) damager;
            ItemStack itemInHand = p.getItemInHand();
            BleedItem bleedItem = BleedItem.getBleedItem(itemInHand);
            if (!bleedItem.validate()) return;
            if (!bleedItem.rand()) return;
            if (entity instanceof Player) {
                Player ep = (Player) entity;
                ep.sendMessage(this.getConfig().getString("lang").replace("%player%", p.getName()));
            }
            long endTime = System.currentTimeMillis() + bleedItem.getTime() * 1000l;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() > endTime || livingEntity.isDead() || livingEntity.getHealth() == 1) {
                        try {
                            Bukkit.getScheduler().cancelTask(this.getTaskId());
                        } catch (Exception e) {

                        }
                        return;
                    }
                    double health = livingEntity.getHealth();
                    if (health <= 1) return;
                    double resultHealth = health - bleedItem.getDamage();
                    resultHealth = resultHealth <= 1 ? 1 : resultHealth;
                    livingEntity.setHealth(resultHealth);
/*                System.out.println("流血ok");
                System.out.println("livingEntity.getHealth() = " + livingEntity.getHealth());*/
                }
            }.runTaskTimer(this, 20, 20);
        } catch (Exception ex) {

        }
    }
}

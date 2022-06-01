package com.me.tft_02.soulbound.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.soulbound.util.DurabilityUtils;

public class EntityListener implements Listener {

    /**
     * Apply combat modifiers
     *
     * @param event The event to run the combat checks on.
     */
    public void onCombat(final EntityDamageByEntityEvent event) {
        final Entity damager = event.getDamager();
        final EntityType damagerType = damager.getType();
        switch (damagerType) {
            case PLAYER:
                final Player attacker = (Player) event.getDamager();
                final ItemStack itemInHand = attacker.getItemInHand();
                DurabilityUtils.handleInfiniteDurability(itemInHand);
            default:
                return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageEvent event) {
        if (event.getDamage() == 0 || event.getEntity().isDead()) {
            return;
        }
        final Entity entity = event.getEntity();
        if (entity instanceof Player) {
            final Player player = (Player) entity;
            for (final ItemStack itemStack : player.getInventory().getArmorContents()) {
                DurabilityUtils.handleInfiniteDurability(itemStack);
            }
        }
    }

    /**
     * Check EntityDamageByEntityEvent events.
     *
     * @param event The event to check
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDeath(final EntityDamageByEntityEvent event) {
        if (event.getDamage() == 0 || event.getEntity().isDead()) {
            return;
        }
        if (event.getEntity() instanceof LivingEntity) {
            onCombat(event);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityShootBow(final EntityShootBowEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof Player) {
            DurabilityUtils.handleInfiniteDurability(((Player) entity).getItemInHand());
        }
    }
}

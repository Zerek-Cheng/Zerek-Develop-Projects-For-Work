package Test;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class LoreEvents
        implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void applyOnInventoryClose(InventoryCloseEvent event) {
        if ((event.getPlayer() instanceof Player)) {
            LoreAttributes.loreManager.handleArmorRestriction((Player) event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void applyOnPlayerLogin(PlayerJoinEvent event) {
        LoreAttributes.loreManager.applyHpBonus(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void applyOnPlayerRespawn(PlayerRespawnEvent event) {
        LoreAttributes.loreManager.applyHpBonus(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void applyOnEntityTarget(EntityTargetEvent event) {
        if ((event.getEntity() instanceof LivingEntity)) {
            LivingEntity e = (LivingEntity) event.getEntity();

            LoreAttributes.loreManager.applyHpBonus(e);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void modifyEntityDamage(EntityDamageByEntityEvent event) {
        if ((event.isCancelled()) || (!(event.getEntity() instanceof LivingEntity))) {
            return;
        }
        if (LoreAttributes.loreManager.dodgedAttack((LivingEntity) event.getEntity())) {
            event.setDamage(0.0D);
            event.setCancelled(true);
            return;
        }
        if ((event.getDamager() instanceof LivingEntity)) {
            LivingEntity damager = (LivingEntity) event.getDamager();
            if ((damager instanceof Player)) {
                if (LoreAttributes.loreManager.canAttack(((Player) damager).getName())) {
                    LoreAttributes.loreManager.addAttackCooldown(((Player) damager).getName());
                } else {
                    if (!LoreAttributes.config.getBoolean("lore.attack-speed.display-message")) {
                        event.setCancelled(true);
                        return;
                    }
                    ((Player) damager).sendMessage(LoreAttributes.config.getString("lore.attack-speed.message"));
                    event.setCancelled(true);
                    return;
                }
            }
            if (LoreAttributes.loreManager.useRangeOfDamage(damager)) {
                int num = Math.max(0, LoreAttributes.loreManager.getDamageBonus(damager) - LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity()));
                event.setDamage(num);
                event.setDamage(event.getDamage() + num * this.getPowerDamage(damager));
            } else {
                int num1 = LoreAttributes.loreManager.getDamageBonus(damager) - LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity());
                event.setDamage(Math.max(0.0D, event.getDamage() + num1));
                event.setDamage(event.getDamage() + num1 * this.getPowerDamage(damager));

            }
            damager.setHealth(Math.min(damager.getMaxHealth(), damager.getHealth() + Math.min(LoreAttributes.loreManager.getLifeSteal(damager), event.getDamage())));
        } else if ((event.getDamager() instanceof Arrow)) {
            Arrow arrow = (Arrow) event.getDamager();
            if ((arrow.getShooter() != null) && ((arrow.getShooter() instanceof LivingEntity))) {
                LivingEntity damager = (LivingEntity) arrow.getShooter();
                if ((damager instanceof Player)) {
                    if (LoreAttributes.loreManager.canAttack(((Player) damager).getName())) {
                        LoreAttributes.loreManager.addAttackCooldown(((Player) damager).getName());
                    } else {
                        if (!LoreAttributes.config.getBoolean("lore.attack-speed.display-message")) {
                            event.setCancelled(true);
                            return;
                        }
                        ((Player) damager).sendMessage(LoreAttributes.config.getString("lore.attack-speed.message"));
                        event.setCancelled(true);
                        return;
                    }
                }
                if (LoreAttributes.loreManager.useRangeOfDamage(damager)) {
                    int num2 = LoreAttributes.loreManager.getDamageBonus(damager) - LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity());
                    event.setDamage(Math.max(0, num2));
                    event.setDamage(event.getDamage() + num2 * this.getPowerDamage(damager));
                } else {
                    int num3 = LoreAttributes.loreManager.getDamageBonus(damager);
                    LoreAttributes.loreManager.getArmorBonus((LivingEntity) event.getEntity());
                    event.setDamage(Math.max(0.0D, event.getDamage() + num3));
                    event.setDamage(event.getDamage() + num3 * this.getPowerDamage(damager));
                }
                damager.setHealth(Math.min(damager.getMaxHealth(), damager.getHealth() + Math.min(LoreAttributes.loreManager.getLifeSteal(damager), event.getDamage())));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void damage(EntityDamageEvent event) {
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void applyHealthRegen(EntityRegainHealthEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (((event.getEntity() instanceof Player)) &&
                (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED)) {
            event.setAmount(event.getAmount() + LoreAttributes.loreManager.getRegenBonus((LivingEntity) event.getEntity()));
            if (event.getAmount() <= 0.0D) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void checkBowRestriction(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!LoreAttributes.loreManager.canUse((Player) event.getEntity(), event.getBow())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void checkCraftRestriction(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = event.getInventory().getContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack item = arrayOfItemStack[i];
            if (!LoreAttributes.loreManager.canUse((Player) event.getWhoClicked(), item)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void checkWeaponRestriction(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        if (!LoreAttributes.loreManager.canUse((Player) event.getDamager(), ((Player) event.getDamager()).getItemInHand())) {
            event.setCancelled(true);
            return;
        }
    }

    public double getPowerDamage(LivingEntity p) {
        Collection<PotionEffect> es = p.getActivePotionEffects();
        for (PotionEffect pe : es) {
            if (!pe.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                continue;
            }
            int level = pe.getAmplifier();
            return (level + 1) * 1.3;
        }
        return 0;
    }
}


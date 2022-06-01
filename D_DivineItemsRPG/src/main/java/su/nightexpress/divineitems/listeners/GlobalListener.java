/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.entity.Egg
 *  org.bukkit.entity.EnderPearl
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Fireball
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.LlamaSpit
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.ShulkerBullet
 *  org.bukkit.entity.Snowball
 *  org.bukkit.entity.ThrownExpBottle
 *  org.bukkit.entity.WitherSkull
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityShootBowEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemDamageEvent
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.util.Vector
 */
package su.nightexpress.divineitems.listeners;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.types.AmmoType;
import su.nightexpress.divineitems.utils.ItemUtils;

public class GlobalListener
implements Listener {
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$types$AmmoType;

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onProjShoot(EntityShootBowEvent entityShootBowEvent) {
        LivingEntity livingEntity = entityShootBowEvent.getEntity();
        ItemStack itemStack = entityShootBowEvent.getBow();
        AmmoType ammoType = ItemAPI.getAmmoType(itemStack);
        switch (GlobalListener.$SWITCH_TABLE$su$nightexpress$divineitems$types$AmmoType()[ammoType.ordinal()]) {
            case 1: {
                break;
            }
            case 2: {
                Snowball snowball = (Snowball)livingEntity.launchProjectile(Snowball.class);
                entityShootBowEvent.setProjectile((Entity)snowball);
                break;
            }
            case 3: {
                Egg egg = (Egg)livingEntity.launchProjectile(Egg.class);
                entityShootBowEvent.setProjectile((Entity)egg);
                break;
            }
            case 8: {
                EnderPearl enderPearl = (EnderPearl)livingEntity.launchProjectile(EnderPearl.class);
                entityShootBowEvent.setProjectile((Entity)enderPearl);
                break;
            }
            case 4: {
                Fireball fireball = (Fireball)livingEntity.launchProjectile(Fireball.class);
                entityShootBowEvent.setProjectile((Entity)fireball);
                break;
            }
            case 5: {
                WitherSkull witherSkull = (WitherSkull)livingEntity.launchProjectile(WitherSkull.class);
                entityShootBowEvent.setProjectile((Entity)witherSkull);
                break;
            }
            case 6: {
                ShulkerBullet shulkerBullet = (ShulkerBullet)livingEntity.getWorld().spawnEntity(livingEntity.getEyeLocation().add(livingEntity.getEyeLocation().getDirection()), EntityType.SHULKER_BULLET);
                shulkerBullet.setShooter((ProjectileSource)livingEntity);
                shulkerBullet.setVelocity(livingEntity.getEyeLocation().getDirection().multiply(1.0f + entityShootBowEvent.getForce()));
                ItemStack itemStack2 = new ItemStack(livingEntity.getEquipment().getItemInMainHand());
                ItemUtils.setProjectileData((Projectile)shulkerBullet, livingEntity, itemStack2);
                entityShootBowEvent.setProjectile((Entity)shulkerBullet);
                break;
            }
            case 7: {
                LlamaSpit llamaSpit = (LlamaSpit)livingEntity.getWorld().spawnEntity(livingEntity.getEyeLocation().add(livingEntity.getEyeLocation().getDirection()), EntityType.LLAMA_SPIT);
                llamaSpit.setShooter((ProjectileSource)livingEntity);
                llamaSpit.setBounce(true);
                llamaSpit.setVelocity(livingEntity.getEyeLocation().getDirection().multiply(1.0f + entityShootBowEvent.getForce()));
                ItemStack itemStack3 = new ItemStack(livingEntity.getEquipment().getItemInMainHand());
                ItemUtils.setProjectileData((Projectile)llamaSpit, livingEntity, itemStack3);
                entityShootBowEvent.setProjectile((Entity)llamaSpit);
                break;
            }
            case 9: {
                ThrownExpBottle thrownExpBottle = (ThrownExpBottle)livingEntity.getWorld().spawnEntity(livingEntity.getEyeLocation().add(livingEntity.getEyeLocation().getDirection()), EntityType.THROWN_EXP_BOTTLE);
                thrownExpBottle.setShooter((ProjectileSource)livingEntity);
                thrownExpBottle.setBounce(true);
                thrownExpBottle.setVelocity(livingEntity.getEyeLocation().getDirection().multiply(1.0f + entityShootBowEvent.getForce()));
                ItemStack itemStack4 = new ItemStack(livingEntity.getEquipment().getItemInMainHand());
                ItemUtils.setProjectileData((Projectile)thrownExpBottle, livingEntity, itemStack4);
                entityShootBowEvent.setProjectile((Entity)thrownExpBottle);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onDurability(PlayerItemDamageEvent playerItemDamageEvent) {
        ItemStack itemStack = playerItemDamageEvent.getItem();
        if (ItemAPI.getDurability(itemStack, 0) != -1) {
            playerItemDamageEvent.setCancelled(true);
        } else if (ItemAPI.getDurability(itemStack, 0) == -999) {
            playerItemDamageEvent.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        if (ItemAPI.getDurability(itemStack, 0) > 0) {
            ItemAPI.setFinalDurability(itemStack, (Entity)player, 1);
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onFish(PlayerFishEvent playerFishEvent) {
        Player player = playerFishEvent.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        if (!ItemAPI.canUse(itemStack, player)) {
            playerFishEvent.setCancelled(true);
            return;
        }
        if (ItemAPI.getDurability(itemStack, 0) > 0) {
            ItemAPI.setFinalDurability(itemStack, (Entity)player, 1);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onDamageArmor(EntityDamageEvent entityDamageEvent) {
        Entity entity = entityDamageEvent.getEntity();
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity)entity;
        ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, true);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack[n2];
            if (itemStack != null && ItemAPI.getDurability(itemStack, 0) > 0) {
                ItemAPI.setFinalDurability(itemStack, (Entity)livingEntity, 1);
            }
            ++n2;
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onDamageItem(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player)entityDamageByEntityEvent.getDamager();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        if (ItemAPI.getDurability(itemStack, 0) > 0) {
            ItemAPI.setFinalDurability(itemStack, (Entity)player, 1);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onShoot(EntityShootBowEvent entityShootBowEvent) {
        if (!(entityShootBowEvent.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)entityShootBowEvent.getEntity();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        if (ItemAPI.getDurability(itemStack, 0) > 0) {
            ItemAPI.setFinalDurability(itemStack, (Entity)player, 1);
        }
    }

    @EventHandler
    public void onCloseInv(InventoryCloseEvent inventoryCloseEvent) {
        Player player = (Player)inventoryCloseEvent.getPlayer();
        if (inventoryCloseEvent.getInventory().getType() == InventoryType.CRAFTING && inventoryCloseEvent.getInventory().getHolder().equals((Object)player)) {
            EntityAPI.checkForLegitItems(player);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onDamageBroken(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player)entityDamageByEntityEvent.getDamager();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (!ItemAPI.canUse(itemStack, player)) {
            entityDamageByEntityEvent.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onInteractBroken(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = playerInteractEvent.getItem();
        if (itemStack == null || itemStack.getItemMeta() == null || itemStack.getItemMeta().getLore() == null) {
            return;
        }
        if (!ItemAPI.canUse(itemStack, player)) {
            playerInteractEvent.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onClickDenied(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (inventoryClickEvent.getInventory().getType() == InventoryType.CRAFTING && inventoryClickEvent.getInventory().getHolder().equals((Object)player)) {
            ItemStack itemStack2 = inventoryClickEvent.getCursor();
            if (itemStack2 != null && itemStack2.getType() != Material.AIR && inventoryClickEvent.getSlot() >= 36 && inventoryClickEvent.getSlot() <= 40 && !ItemAPI.canUse(itemStack2, player)) {
                inventoryClickEvent.setCancelled(true);
                return;
            }
            if (itemStack == null || !itemStack.hasItemMeta()) {
                return;
            }
            if (!ItemAPI.canUse(itemStack, player) && inventoryClickEvent.isShiftClick()) {
                inventoryClickEvent.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onBlockBreakBroken(BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        if (!ItemAPI.canUse(itemStack, player)) {
            blockBreakEvent.setCancelled(true);
            return;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$types$AmmoType() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$types$AmmoType;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[AmmoType.values().length];
        try {
            arrn[AmmoType.ARROW.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AmmoType.EGG.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AmmoType.ENDER_PEARL.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AmmoType.EXP_POTION.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AmmoType.FIREBALL.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AmmoType.LLAMA_SPIT.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AmmoType.SHULKER_BULLET.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AmmoType.SNOWBALL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AmmoType.WITHER_SKULL.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$types$AmmoType = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$types$AmmoType;
    }
}


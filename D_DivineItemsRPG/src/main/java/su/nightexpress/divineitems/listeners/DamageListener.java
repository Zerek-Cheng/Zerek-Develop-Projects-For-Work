/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.EnderPearl
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent$RegainReason
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.projectiles.ProjectileSource
 */
package su.nightexpress.divineitems.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.projectiles.ProjectileSource;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.attributes.DisarmRateSettings;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.attributes.StatSettings;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.hooks.external.MythicMobsHook;
import su.nightexpress.divineitems.hooks.external.WorldGuardHook;
import su.nightexpress.divineitems.hooks.external.citizens.CitizensHook;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.buffs.BuffManager;
import su.nightexpress.divineitems.modules.combatlog.CombatLogManager;
import su.nightexpress.divineitems.types.ArmorType;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.Utils;

public class DamageListener
implements Listener {
    private DivineItems plugin;

    public DamageListener(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @EventHandler
    public void onRangeDamage(PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (playerInteractEvent.getAction().toString().contains("RIGHT")) {
            return;
        }
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.BOW || !ItemUtils.isWeapon(itemStack)) {
            return;
        }
        double d = ItemAPI.getAttribute(itemStack, ItemStat.RANGE);
        double d2 = this.plugin.getMM().getBuffManager().getBuff(player, BuffManager.BuffType.ITEM_STAT, ItemStat.RANGE.name());
        if (d2 > 0.0) {
            if (d <= 0.0) {
                d = 3.0;
            }
            d *= 1.0 + d2 / 100.0;
        }
        if (d <= 0.0) {
            return;
        }
        LivingEntity livingEntity = EntityAPI.getEntityTargetByRange((Entity)player, d);
        if (livingEntity != null) {
            double d3 = ItemAPI.getDefaultDamage(itemStack);
            livingEntity.damage(d3, (Entity)player);
        }
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onMeleeDamage(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        Entity entity = entityDamageByEntityEvent.getEntity();
        Entity entity2 = entityDamageByEntityEvent.getDamager();
        if (!(entity instanceof LivingEntity) || entity == null) {
            return;
        }
        if (!(entity2 instanceof LivingEntity) || entity2 == null) {
            return;
        }
        if (Hook.WORLD_GUARD.isEnabled() && !this.plugin.getHM().getWorldGuard().canFights(entity, entity2)) {
            entityDamageByEntityEvent.setCancelled(true);
            return;
        }
        if (Hook.CITIZENS.isEnabled() && this.plugin.getHM().getCitizens().isNPC(entity)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity)entity2;
        LivingEntity livingEntity2 = (LivingEntity)entity;
        ItemStack itemStack = null;
        if (livingEntity.getEquipment().getItemInMainHand() != null) {
            itemStack = new ItemStack(livingEntity.getEquipment().getItemInMainHand());
        }
        if (itemStack == null || itemStack.getType() == Material.BOW) {
            return;
        }
        double d = ItemAPI.getAttribute(itemStack, ItemStat.RANGE);
        if (d > 0.0 && livingEntity.getWorld().equals((Object)livingEntity2.getWorld()) && livingEntity.getLocation().distance(livingEntity2.getLocation()) > d) {
            entityDamageByEntityEvent.setCancelled(true);
            return;
        }
        this.processDmg(itemStack, livingEntity, livingEntity2, entityDamageByEntityEvent);
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onDBows(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        String string;
        Player player;
        Entity entity = entityDamageByEntityEvent.getEntity();
        Entity entity2 = entityDamageByEntityEvent.getDamager();
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        if (!(entity2 instanceof Projectile)) {
            return;
        }
        if (Hook.WORLD_GUARD.isEnabled() && !this.plugin.getHM().getWorldGuard().canFights(entity, entity2)) {
            entityDamageByEntityEvent.setCancelled(true);
            return;
        }
        if (Hook.CITIZENS.isEnabled() && this.plugin.getHM().getCitizens().isNPC(entity)) {
            return;
        }
        Projectile projectile = (Projectile)entity2;
        if (projectile instanceof EnderPearl && entity instanceof Player) {
            player = (Player)entity;
            if (projectile.getShooter() != null && projectile.getShooter().equals((Object)player)) {
                return;
            }
        }
        if (!projectile.hasMetadata("DIItem") || !(projectile.getShooter() instanceof LivingEntity)) {
            return;
        }
        player = (LivingEntity)entity;
        LivingEntity livingEntity = (LivingEntity)projectile.getShooter();
        if (player == null || livingEntity == null) {
            return;
        }
        if (projectile.hasMetadata("DIVINE_ARROW_ID")) {
            string = ((MetadataValue)projectile.getMetadata("DIVINE_ARROW_ID").get(0)).asString();
            player.setMetadata("DIVINE_ARROW_ID", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)string));
        }
        string = (ItemStack)((MetadataValue)projectile.getMetadata("DIItem").get(0)).value();
        this.processDmg((ItemStack)string, livingEntity, (LivingEntity)player, entityDamageByEntityEvent);
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onProjectLaunch(ProjectileLaunchEvent projectileLaunchEvent) {
        Projectile projectile = projectileLaunchEvent.getEntity();
        if (projectile == null || !(projectile instanceof Projectile)) {
            return;
        }
        Projectile projectile2 = projectile;
        if (projectile2.getShooter() == null || !(projectile2.getShooter() instanceof LivingEntity)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity)projectile2.getShooter();
        if (livingEntity.getEquipment().getItemInMainHand() == null) {
            return;
        }
        ItemStack itemStack = new ItemStack(livingEntity.getEquipment().getItemInMainHand());
        if (!(livingEntity instanceof Player) && !this.plugin.getCM().getCFG().allowAttributesToMobs()) {
            return;
        }
        ItemUtils.setProjectileData(projectile2, livingEntity, itemStack);
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onSimpleDamage(EntityDamageEvent entityDamageEvent) {
        if (!(entityDamageEvent.getEntity() instanceof LivingEntity)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity)entityDamageEvent.getEntity();
        double d = entityDamageEvent.getFinalDamage();
        if (livingEntity instanceof Player) {
            if (this.plugin.getCM().getCFG().getPlayerDmgModifiers().containsKey((Object)entityDamageEvent.getCause())) {
                d *= this.plugin.getCM().getCFG().getPlayerDmgModifiers().get((Object)entityDamageEvent.getCause()).doubleValue();
            }
        } else if (this.plugin.getCM().getCFG().getMobDmgModifiers().containsKey((Object)entityDamageEvent.getCause())) {
            d *= this.plugin.getCM().getCFG().getMobDmgModifiers().get((Object)entityDamageEvent.getCause()).doubleValue();
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onFinalDamage(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            return;
        }
        if (!(entityDamageEvent.getEntity() instanceof LivingEntity)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity)entityDamageEvent.getEntity();
        double d = entityDamageEvent.getFinalDamage();
        if (!(livingEntity instanceof Player) && !this.plugin.getCFG().allowAttributesToMobs()) {
            return;
        }
        String string = entityDamageEvent.getCause().name();
        HashMap<ArmorType, Double> hashMap = ItemAPI.getDefenseTypes(livingEntity);
        for (ArmorType armorType : hashMap.keySet()) {
            double d2 = 0.0;
            if (armorType.getBlockDamageSources().contains(string)) {
                d2 = hashMap.get(armorType);
            }
            String string2 = armorType.getFormula().replace("%def%", String.valueOf(d2)).replace("%dmg%", String.valueOf(d)).replace("%penetrate%", "0");
            entityDamageEvent.setDamage(ItemUtils.calc(string2));
        }
        if (this.plugin.getMM().getCombatLogManager().isActive()) {
            this.plugin.getMM().getCombatLogManager().setDSMeta(livingEntity, string, Utils.round3(d));
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onVampirism(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        Projectile projectile;
        if (!(entityDamageByEntityEvent.getEntity() instanceof LivingEntity)) {
            return;
        }
        if (entityDamageByEntityEvent.getEntity() instanceof ArmorStand) {
            return;
        }
        Entity entity = entityDamageByEntityEvent.getDamager();
        if (entity instanceof Projectile && (projectile = (Projectile)entity).getShooter() != null && projectile.getShooter() instanceof Entity) {
            entity = (Entity)projectile.getShooter();
        }
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        projectile = (LivingEntity)entity;
        double d = entityDamageByEntityEvent.getFinalDamage();
        double d2 = Math.max(0.0, d * (EntityAPI.getItemStat((LivingEntity)projectile, ItemStat.VAMPIRISM) / 100.0));
        EntityRegainHealthEvent entityRegainHealthEvent = new EntityRegainHealthEvent((Entity)projectile, d2, EntityRegainHealthEvent.RegainReason.CUSTOM);
        this.plugin.getPluginManager().callEvent((Event)entityRegainHealthEvent);
        if (!entityRegainHealthEvent.isCancelled() && projectile.getHealth() + d2 <= projectile.getMaxHealth()) {
            projectile.setHealth(projectile.getHealth() + d2);
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onFish(PlayerFishEvent playerFishEvent) {
        if (!this.plugin.getCM().getCFG().allowFishHookDamage()) {
            return;
        }
        Entity entity = playerFishEvent.getCaught();
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        Player player = playerFishEvent.getPlayer();
        LivingEntity livingEntity = (LivingEntity)entity;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        double d = ItemAPI.getItemTotalDamage(itemStack);
        double d2 = player.getLocation().distance(livingEntity.getLocation());
        d2 = d2 < 10.0 ? (d2 /= 10.0) : 1.5;
        livingEntity.damage(d *= d2, (Entity)player);
    }

    @EventHandler
    public void onWallFall(EntityChangeBlockEvent entityChangeBlockEvent) {
        if (entityChangeBlockEvent.getEntity() instanceof FallingBlock && entityChangeBlockEvent.getEntity().hasMetadata("DIFall")) {
            FallingBlock fallingBlock = (FallingBlock)entityChangeBlockEvent.getEntity();
            Utils.playEffect("BLOCK_CRACK:" + fallingBlock.getMaterial().name(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, fallingBlock.getLocation());
            entityChangeBlockEvent.getEntity().remove();
            entityChangeBlockEvent.setCancelled(true);
            Entity entity = entityChangeBlockEvent.getEntity();
            Player player = Bukkit.getPlayer((String)((MetadataValue)entity.getMetadata("LauncherZ").get(0)).asString());
            if (player == null) {
                return;
            }
            List list = entityChangeBlockEvent.getEntity().getNearbyEntities(3.0, 3.0, 3.0);
            if (list.isEmpty()) {
                return;
            }
            ItemStack itemStack = (ItemStack)((MetadataValue)entity.getMetadata("DIItem").get(0)).value();
            for (Entity entity2 : list) {
                if (!(entity2 instanceof LivingEntity)) continue;
                LivingEntity livingEntity = (LivingEntity)entity2;
                if (Hook.CITIZENS.isEnabled() && this.plugin.getHM().getCitizens().isNPC((Entity)livingEntity) || livingEntity.equals((Object)player) || livingEntity.equals((Object)entity) || Hook.WORLD_GUARD.isEnabled() && !this.plugin.getHM().getWorldGuard().canFights((Entity)livingEntity, (Entity)player)) continue;
                livingEntity.damage(ItemUtils.calcDamageByFormula(livingEntity, (LivingEntity)player, 1.0, itemStack), (Entity)player);
            }
        }
    }

    private void processDmg(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        double d;
        double d2;
        double d3;
        double d4 = EntityAPI.getItemStat(livingEntity2, ItemStat.DODGE_RATE);
        double d5 = EntityAPI.getItemStat(livingEntity, ItemStat.ACCURACY_RATE);
        double d6 = Utils.getRandDouble(0.0, 100.0);
        double d7 = Utils.getRandDouble(0.0, 100.0);
        if (d6 <= d4 && d7 > d5) {
            entityDamageByEntityEvent.setDamage(0.0);
            entityDamageByEntityEvent.setCancelled(true);
            if (this.plugin.getMM().getCombatLogManager().isActive()) {
                this.plugin.getMM().getCombatLogManager().setDodgeMeta(livingEntity2);
            }
            return;
        }
        if (entityDamageByEntityEvent.isApplicable(EntityDamageEvent.DamageModifier.ARMOR)) {
            entityDamageByEntityEvent.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0.0);
        }
        if ((d = ItemAPI.getAttribute(itemStack, ItemStat.DISARM_RATE)) > 0.0 && Utils.getRandDouble(0.0, 100.0) <= d) {
            boolean bl = true;
            ItemStack itemStack2 = livingEntity2.getEquipment().getItemInMainHand();
            if (itemStack2 == null || itemStack2.getType() == Material.AIR) {
                itemStack2 = livingEntity2.getEquipment().getItemInOffHand();
                bl = false;
            }
            if (itemStack2 != null && itemStack2.getType() != Material.AIR) {
                Player player;
                if (bl) {
                    livingEntity2.getEquipment().setItemInMainHand(new ItemStack(Material.AIR));
                } else {
                    livingEntity2.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
                }
                livingEntity2.getWorld().dropItemNaturally(livingEntity2.getLocation(), itemStack2).setPickupDelay(40);
                DisarmRateSettings disarmRateSettings = (DisarmRateSettings)ItemStat.DISARM_RATE.getSettings();
                Utils.playEffect(disarmRateSettings.getEffect(), 0.2, 0.4, 0.2, 0.1, 25, livingEntity2.getLocation());
                if (livingEntity instanceof Player) {
                    player = (Player)livingEntity;
                    player.sendMessage(disarmRateSettings.getMsgToDamager().replace("%s%", Utils.getEntityName((Entity)livingEntity2)));
                }
                if (livingEntity2 instanceof Player) {
                    player = (Player)livingEntity2;
                    player.sendMessage(disarmRateSettings.getMsgToEntity().replace("%s%", Utils.getEntityName((Entity)livingEntity)));
                }
            }
        }
        double d8 = entityDamageByEntityEvent.getDamage();
        double d9 = ItemAPI.getAttribute(itemStack, ItemStat.BURN_RATE);
        if (Utils.getRandDouble(0.0, 100.0) <= d9) {
            livingEntity2.setFireTicks(100);
        }
        if ((d3 = EntityAPI.getItemStat(livingEntity, ItemStat.RANGE)) <= 0.0) {
            d3 = 3.0;
        }
        if ((d2 = d8 * (ItemAPI.getAttribute(itemStack, ItemStat.AOE_DAMAGE) / 100.0)) > 0.0) {
            if (livingEntity2.hasMetadata("AOE_FIX")) {
                livingEntity2.removeMetadata("AOE_FIX", (Plugin)this.plugin);
            } else {
                for (Entity entity : livingEntity2.getNearbyEntities(d3, d3, d3)) {
                    if (!(entity instanceof LivingEntity)) continue;
                    LivingEntity livingEntity3 = (LivingEntity)entity;
                    if (Hook.CITIZENS.isEnabled() && this.plugin.getHM().getCitizens().isNPC((Entity)livingEntity3) || livingEntity3.equals((Object)livingEntity) || Hook.WORLD_GUARD.isEnabled() && !this.plugin.getHM().getWorldGuard().canFights((Entity)livingEntity3, (Entity)livingEntity)) continue;
                    livingEntity3.setMetadata("AOE_FIX", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)"yes"));
                    livingEntity3.damage(d2, (Entity)livingEntity);
                }
            }
        }
        double d10 = ItemUtils.calcDamageByFormula(livingEntity2, livingEntity, d8, itemStack);
        entityDamageByEntityEvent.setDamage(d10);
        if (Hook.MYTHIC_MOBS.isEnabled()) {
            this.plugin.getHM().getMythicHook().setSkillDamage((Entity)livingEntity, d10);
        }
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.util.Vector
 *  ru.Capitalism.DivineClassesRPG.API.DivineAPI
 *  ru.Capitalism.DivineClassesRPG.Player.SkillPlayer
 *  ru.Capitalism.DivineClassesRPG.Skills.StatType
 */
package su.nightexpress.divineitems.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import ru.Capitalism.DivineClassesRPG.API.DivineAPI;
import ru.Capitalism.DivineClassesRPG.Player.SkillPlayer;
import ru.Capitalism.DivineClassesRPG.Skills.StatType;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.hooks.external.RPGInvHook;
import su.nightexpress.divineitems.hooks.external.WorldGuardHook;
import su.nightexpress.divineitems.hooks.external.citizens.CitizensHook;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.buffs.BuffManager;
import su.nightexpress.divineitems.modules.gems.GemManager;
import su.nightexpress.divineitems.modules.sets.SetManager;
import su.nightexpress.divineitems.utils.ItemUtils;

public class EntityAPI {
    private static DivineItems plugin = DivineItems.instance;

    public static LivingEntity getEntityTarget(Entity entity) {
        return EntityAPI.getEntityTargetByRange(entity, plugin.getCM().getCFG().getMaxTargetDistance());
    }

    public static LivingEntity getEntityTargetByRange(Entity entity, double d) {
        LivingEntity livingEntity = null;
        Location location = entity.getLocation();
        if (entity instanceof LivingEntity) {
            location = ((LivingEntity)entity).getEyeLocation();
        }
        Vector vector = location.getDirection();
        int n = 0;
        while ((double)n < d) {
            Location location2 = location.add(vector);
            if (location2.getBlock() != null && location2.getBlock().getType() != Material.AIR) break;
            Entity[] arrentity = location2.getChunk().getEntities();
            int n2 = arrentity.length;
            int n3 = 0;
            while (n3 < n2) {
                Entity entity2 = arrentity[n3];
                if (!(!(entity2 instanceof LivingEntity) || entity2 instanceof ArmorStand || !entity2.getWorld().equals((Object)location2.getWorld()) || entity2.getLocation().distance(location2) > 1.5 || Hook.WORLD_GUARD.isEnabled() && !plugin.getHM().getWorldGuard().canFights(entity2, entity) || Hook.CITIZENS.isEnabled() && plugin.getHM().getCitizens().isNPC(entity2) || entity2.equals((Object)entity))) {
                    return (LivingEntity)entity2;
                }
                ++n3;
            }
            ++n;
        }
        return livingEntity;
    }

    public static double getEnchantedDefense(Entity entity, LivingEntity livingEntity) {
        double d;
        d = 0.0;
        if (entity instanceof Creeper) {
            ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, true);
            int n = arritemStack.length;
            int n2 = 0;
            while (n2 < n) {
                ItemStack itemStack = arritemStack[n2];
                if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS)) {
                    d += (double)(itemStack.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS) * 2);
                }
                ++n2;
            }
        } else if (entity instanceof Projectile) {
            ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, true);
            int n = arritemStack.length;
            int n3 = 0;
            while (n3 < n) {
                ItemStack itemStack = arritemStack[n3];
                if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.containsEnchantment(Enchantment.PROTECTION_PROJECTILE)) {
                    d += (double)(itemStack.getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE) * 2);
                }
                ++n3;
            }
        } else {
            ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, true);
            int n = arritemStack.length;
            int n4 = 0;
            while (n4 < n) {
                ItemStack itemStack = arritemStack[n4];
                if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    d += (double)itemStack.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                }
                ++n4;
            }
        }
        return d;
    }

    public static double getPvPEDamage(LivingEntity livingEntity, LivingEntity livingEntity2) {
        double d = 0.0;
        if (livingEntity2 instanceof Player) {
            d = livingEntity instanceof Player ? EntityAPI.getItemStat(livingEntity2, ItemStat.PVP_DAMAGE) : EntityAPI.getItemStat(livingEntity2, ItemStat.PVE_DAMAGE);
        }
        return d;
    }

    public static double getItemStat(LivingEntity livingEntity, ItemStat itemStat) {
        Player player;
        SkillPlayer skillPlayer;
        if (!(livingEntity instanceof Player) && !plugin.getCM().getCFG().allowAttributesToMobs()) {
            return -1.0;
        }
        double d = 0.0;
        double d2 = EntityAPI.getBase(livingEntity, itemStat);
        double d3 = EntityAPI.getBonus(livingEntity, itemStat);
        if (livingEntity instanceof Player) {
            player = (Player)livingEntity;
            d3 += plugin.getMM().getBuffManager().getBuff(player, BuffManager.BuffType.ITEM_STAT, itemStat.name());
        }
        if (livingEntity instanceof Player && Hook.DIVINE_CLASSES.isEnabled() && (skillPlayer = DivineAPI.getSkillPlayer((UUID)(player = (Player)livingEntity).getUniqueId())) != null && itemStat == ItemStat.DODGE_RATE) {
            d2 += (Double)skillPlayer.getStats().get((Object)StatType.DODGE) / 20.0;
        }
        if (itemStat.isPercent()) {
            d = d2 + d3;
        } else {
            if (d2 == 0.0 && d3 != 0.0) {
                d2 = 1.0;
            }
            d = d2 * (1.0 + d3 / 100.0);
        }
        if (itemStat.getCapability() >= 0.0 && d > itemStat.getCapability()) {
            d = itemStat.getCapability();
        }
        return d;
    }

    public static double getBase(LivingEntity livingEntity, ItemStat itemStat) {
        String string = plugin.getCM().getCFG().getAttributeFormat();
        String string2 = string.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getPrefix());
        double d = 0.0;
        if (!plugin.getCM().getCFG().allowAttributesToMobs() && !(livingEntity instanceof Player)) {
            return d;
        }
        if (plugin.getMM().getSetManager().isActive()) {
            for (SetManager.ItemSet itemSet : plugin.getMM().getSetManager().getSets()) {
                if (!plugin.getMM().getSetManager().getSetAttributes(livingEntity, itemSet, false).containsKey((Object)itemStat)) continue;
                d += plugin.getMM().getSetManager().getSetAttributes(livingEntity, itemSet, false).get((Object)itemStat).doubleValue();
            }
        }
        ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, false);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            SetManager.ItemSet itemSet;
            itemSet = arritemStack[n2];
            if (itemSet != null && itemSet.hasItemMeta() && itemSet.getItemMeta().hasLore()) {
                List list = itemSet.getItemMeta().getLore();
                for (String string3 : list) {
                    if (!string3.startsWith(string2)) continue;
                    String string4 = ChatColor.stripColor((String)string3.replace(string2, "").replace(plugin.getCM().getCFG().getStrPercent(), "").replace(plugin.getCM().getCFG().getStrModifier(), ""));
                    if (string4.isEmpty()) break;
                    double d2 = Double.parseDouble(string4);
                    d += d2;
                    break;
                }
                if (plugin.getMM().getGemManager().getItemGemStats((ItemStack)itemSet, false).containsKey((Object)itemStat)) {
                    d += plugin.getMM().getGemManager().getItemGemStats((ItemStack)itemSet, false).get((Object)itemStat).doubleValue();
                }
            }
            ++n2;
        }
        return d;
    }

    public static double getBonus(LivingEntity livingEntity, ItemStat itemStat) {
        String string = plugin.getCM().getCFG().getAttributeFormat();
        String string2 = string.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getBonus());
        double d = 0.0;
        if (!plugin.getCM().getCFG().allowAttributesToMobs() && !(livingEntity instanceof Player)) {
            return d;
        }
        if (plugin.getMM().getSetManager().isActive()) {
            for (SetManager.ItemSet itemSet : plugin.getMM().getSetManager().getSets()) {
                if (!plugin.getMM().getSetManager().getSetAttributes(livingEntity, itemSet, true).containsKey((Object)itemStat)) continue;
                d += plugin.getMM().getSetManager().getSetAttributes(livingEntity, itemSet, true).get((Object)itemStat).doubleValue();
            }
        }
        ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, false);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            SetManager.ItemSet itemSet;
            itemSet = arritemStack[n2];
            if (itemSet != null && itemSet.hasItemMeta() && itemSet.getItemMeta().hasLore()) {
                if (plugin.getMM().getGemManager().getItemGemStats((ItemStack)itemSet, true).containsKey((Object)itemStat)) {
                    d += plugin.getMM().getGemManager().getItemGemStats((ItemStack)itemSet, true).get((Object)itemStat).doubleValue();
                }
                List list = itemSet.getItemMeta().getLore();
                for (String string3 : list) {
                    if (!string3.startsWith(string2)) continue;
                    String string4 = ChatColor.stripColor((String)string3.replace(string2, "").replace(plugin.getCM().getCFG().getStrPercent(), "").replace(plugin.getCM().getCFG().getStrModifier(), ""));
                    double d2 = 0.0;
                    try {
                        d2 = Double.parseDouble(string4);
                    }
                    catch (NumberFormatException numberFormatException) {
                        Bukkit.getConsoleSender().sendMessage("[DivineItems] Unable to get bonus stats from string: " + string4);
                    }
                    d += d2;
                    break;
                }
            }
            ++n2;
        }
        return d;
    }

    public static ItemStack[] getEquipment(LivingEntity livingEntity, boolean bl) {
        if (livingEntity instanceof Player && Hook.RPG_INVENTORY.isEnabled()) {
            Player player = (Player)livingEntity;
            return plugin.getHM().getRPGInventory().getEquip(player);
        }
        ItemStack[] arritemStack = new ItemStack[6];
        if (livingEntity != null && livingEntity.getEquipment() != null) {
            ItemStack itemStack;
            int n = 2;
            while (n < 6) {
                arritemStack[n] = livingEntity.getEquipment().getArmorContents()[n - 2];
                ++n;
            }
            if (livingEntity.getEquipment().getItemInOffHand() != null && (plugin.getCM().getCFG().allowAttributesToOffHand() || livingEntity.getEquipment().getItemInOffHand().getType() == Material.SHIELD)) {
                arritemStack[1] = livingEntity.getEquipment().getItemInOffHand();
            }
            if (livingEntity.getEquipment().getItemInMainHand() != null && !ItemUtils.isArmor(itemStack = livingEntity.getEquipment().getItemInMainHand())) {
                arritemStack[0] = bl ? new ItemStack(Material.AIR) : livingEntity.getEquipment().getItemInMainHand();
            }
        }
        return arritemStack;
    }

    public static void checkForLegitItems(Player player) {
        ItemStack[] arritemStack;
        int n = 0;
        ItemStack[] arritemStack2 = arritemStack = player.getInventory().getArmorContents();
        int n2 = arritemStack2.length;
        int n3 = 0;
        while (n3 < n2) {
            ItemStack itemStack = arritemStack2[n3];
            if (itemStack != null && !ItemAPI.canUse(itemStack, player)) {
                if (n == 2) {
                    player.getInventory().setChestplate(null);
                } else if (n == 1) {
                    player.getInventory().setLeggings(null);
                } else if (n == 0) {
                    player.getInventory().setBoots(null);
                } else {
                    player.getInventory().setHelmet(null);
                }
                if (player.getInventory().firstEmpty() != -1) {
                    player.getInventory().addItem(new ItemStack[]{itemStack});
                } else {
                    player.getWorld().dropItem(player.getLocation(), itemStack).setPickupDelay(40);
                }
            }
            ++n;
            ++n3;
        }
    }
}


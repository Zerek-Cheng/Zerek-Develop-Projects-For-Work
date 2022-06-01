/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Biome
 *  org.bukkit.block.Block
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 *  org.bukkit.inventory.meta.PotionMeta
 *  org.bukkit.inventory.meta.SpawnEggMeta
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package su.nightexpress.divineitems.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.api.events.DivineItemDamageEvent;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.hooks.ClassesHook;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.hooks.LevelsHook;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.ability.AbilityManager;
import su.nightexpress.divineitems.modules.buffs.BuffManager;
import su.nightexpress.divineitems.modules.gems.GemManager;
import su.nightexpress.divineitems.modules.identify.IdentifyManager;
import su.nightexpress.divineitems.modules.soulbound.SoulboundManager;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.nms.NBTAttribute;
import su.nightexpress.divineitems.nms.NMS;
import su.nightexpress.divineitems.types.AmmoType;
import su.nightexpress.divineitems.types.ArmorType;
import su.nightexpress.divineitems.types.DamageType;
import su.nightexpress.divineitems.types.SlotType;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.Utils;

public class ItemAPI {
    private static DivineItems plugin = DivineItems.instance;
    private static Random r = new Random();
    private static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$Material;
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$attributes$ItemStat;

    public static boolean hasOwner(ItemStack itemStack) {
        return plugin.getMM().getSoulboundManager().hasOwner(itemStack);
    }

    public static boolean isOwner(ItemStack itemStack, Player player) {
        return plugin.getMM().getSoulboundManager().isOwner(itemStack, player);
    }

    public static String getOwner(ItemStack itemStack) {
        return plugin.getMM().getSoulboundManager().getOwner(itemStack);
    }

    public static ItemStack setOwner(ItemStack itemStack, Player player) {
        return plugin.getMM().getSoulboundManager().setOwner(itemStack, player);
    }

    public static boolean isUntradeable(ItemStack itemStack) {
        return plugin.getMM().getSoulboundManager().isUntradeable(itemStack);
    }

    public static boolean isSoulboundRequired(ItemStack itemStack) {
        return plugin.getMM().getSoulboundManager().isSoulboundRequired(itemStack);
    }

    public static boolean isSoulBinded(ItemStack itemStack) {
        return plugin.getMM().getSoulboundManager().isSoulBinded(itemStack);
    }

    public static boolean isLevelRequired(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        String[] arrstring = plugin.getCFG().getStrLevel().split("%n");
        String string = null;
        if (arrstring[0] != null) {
            string = arrstring[0];
        }
        String string2 = null;
        if (arrstring.length == 2 && arrstring[1] != null) {
            string2 = arrstring[1];
        }
        for (String string3 : list) {
            if ((string == null || !string3.contains(string)) && (string2 == null || !string3.contains(string2))) continue;
            return true;
        }
        return false;
    }

    public static boolean isClassRequired(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        for (String string : list) {
            if (!string.startsWith(plugin.getCFG().getStrClass())) continue;
            return true;
        }
        return false;
    }

    public static boolean isAllowedPlayerClass(ItemStack itemStack, Player player) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return true;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        for (String string : list) {
            if (!string.startsWith(plugin.getCFG().getStrClass())) continue;
            String string2 = string.replace(plugin.getCFG().getStrClass(), "");
            String[] arrstring = string2.split(plugin.getCFG().getStrClassSeparator());
            int n = 0;
            while (n < arrstring.length) {
                String string3 = plugin.getHM().getClassesHook().getPlayerClass(player);
                if (ChatColor.stripColor((String)string3).equalsIgnoreCase(ChatColor.stripColor((String)arrstring[n]))) {
                    return true;
                }
                ++n;
            }
        }
        return false;
    }

    public static boolean canUse(ItemStack itemStack, Player player) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return true;
        }
        if (ItemAPI.getDurability(itemStack, 0) == 0 && !plugin.getCFG().breakItems()) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_BrokenItem.toMsg());
            return false;
        }
        if (ItemAPI.isClassRequired(itemStack) && !ItemAPI.isAllowedPlayerClass(itemStack, player)) {
            if (player.hasPermission("divineitems.bypass.class")) {
                return true;
            }
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Restrictions_Class.toMsg().replace("%s", new StringBuilder().append(plugin.getHM().getClassesHook().getPlayerClass(player)).toString()));
            return false;
        }
        if (ItemAPI.isLevelRequired(itemStack) && ItemAPI.getLevelRequired(itemStack) > plugin.getHM().getLevelsHook().getPlayerLevel(player)) {
            if (player.hasPermission("divineitems.bypass.level")) {
                return true;
            }
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Restrictions_Level.toMsg().replace("%s", new StringBuilder().append(plugin.getHM().getLevelsHook().getPlayerLevel(player)).toString()));
            return false;
        }
        if (plugin.getMM().getSoulboundManager().isActive()) {
            if (plugin.getMM().getSoulboundManager().isSoulboundRequired(itemStack)) {
                if (player.hasPermission("divineitems.bypass.soulbound")) {
                    return true;
                }
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Restrictions_Usage.toMsg());
                return false;
            }
            if (plugin.getMM().getSoulboundManager().isSoulBinded(itemStack) || plugin.getMM().getSoulboundManager().hasOwner(itemStack)) {
                if (player.hasPermission("divineitems.bypass.owner")) {
                    return true;
                }
                if (!plugin.getMM().getSoulboundManager().isOwner(itemStack, player)) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Restrictions_NotOwner.toMsg());
                    return false;
                }
            }
        }
        if (plugin.getMM().getIdentifyManager().isActive() && plugin.getMM().getIdentifyManager().isUnidentified(itemStack)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Identify_NoEquip.toMsg());
            return false;
        }
        return true;
    }

    public static boolean hasAttribute(ItemStack itemStack, ItemStat itemStat) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return false;
        }
        String string = plugin.getCFG().getAttributeFormat();
        String string2 = string.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getPrefix());
        for (String string3 : itemStack.getItemMeta().getLore()) {
            if (!string3.startsWith(string2)) continue;
            return true;
        }
        return false;
    }

    public static int getLevelRequired(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return -1;
        }
        int n = -1;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        String[] arrstring = plugin.getCFG().getStrLevel().split("%n");
        String string = "";
        if (arrstring[0] != null) {
            string = arrstring[0];
        }
        String string2 = "";
        if (arrstring.length == 2 && arrstring[1] != null) {
            string2 = arrstring[1];
        }
        if (string.equals("") && string2.equals("")) {
            return n;
        }
        for (String string3 : list) {
            if (!string3.contains(string) || !string3.contains(string2)) continue;
            string3 = ChatColor.stripColor((String)string3.replace(string, "").replace(string2, "").trim().replaceAll("\\s+", ""));
            try {
                n = Integer.parseInt(string3);
            }
            catch (NumberFormatException numberFormatException) {
                Bukkit.getConsoleSender().sendMessage("[DivineItems] Unable to get level from string: " + string3);
            }
            break;
        }
        return n;
    }

    public static int getDurability(ItemStack itemStack, int n) {
        int n2 = -1;
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return n2;
        }
        String string = plugin.getCFG().getAttributeFormat();
        ItemStat itemStat = ItemStat.DURABILITY;
        String string2 = string.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getPrefix());
        ItemStat itemStat2 = ItemStat.DURABILITY_UNBREAK;
        String string3 = string.replace("%att_value%", itemStat2.getValue()).replace("%att_name%", itemStat2.getName()).replace("%att_prefix%", itemStat2.getPrefix());
        for (String string4 : itemStack.getItemMeta().getLore()) {
            if (string4.startsWith(string3) && string4.endsWith(itemStat2.getValue())) {
                return -999;
            }
            if (!string4.startsWith(string2)) continue;
            String string5 = ChatColor.stripColor((String)string4.replace(string2, ""));
            try {
                n2 = Integer.parseInt(string5.split(ChatColor.stripColor((String)plugin.getCFG().getStrDurabilitySeparator()))[n]);
            }
            catch (NumberFormatException numberFormatException) {
                Bukkit.getConsoleSender().sendMessage("[DivineItems] Unable to get durability from string: " + string5);
            }
            break;
        }
        return n2;
    }

    private static double getDamageFromString(String string, int n) {
        double d = 0.0;
        String[] arrstring = string.split(plugin.getCFG().getStrDamageSeparator());
        if (arrstring.length <= 1 && n > 0) {
            return d;
        }
        try {
            d = Double.parseDouble(ChatColor.stripColor((String)arrstring[n].replace(plugin.getCFG().getStrPercent(), "")));
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return d;
    }

    public static double getItemTotalDamageMinMax(ItemStack itemStack, int n) {
        double d = ItemAPI.getDefaultDamage(itemStack);
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return d;
        }
        block0 : for (DamageType damageType : plugin.getCFG().getDamageTypes().values()) {
            String string = plugin.getCFG().getDamageTypeFormat();
            String string2 = string.replace("%type_value%", damageType.getValue()).replace("%type_name%", damageType.getName()).replace("%type_prefix%", damageType.getPrefix());
            List list = itemStack.getItemMeta().getLore();
            for (String string3 : list) {
                if (!string3.startsWith(string2)) continue;
                String string4 = string3.replace(string2, "");
                d += ItemAPI.getDamageFromString(string4, n);
                continue block0;
            }
        }
        return d;
    }

    public static double getItemTotalDamage(ItemStack itemStack) {
        double d = ItemAPI.getDefaultDamage(itemStack);
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return d;
        }
        double d2 = ItemAPI.getItemTotalDamageMinMax(itemStack, 0);
        double d3 = ItemAPI.getItemTotalDamageMinMax(itemStack, 1);
        return Utils.getRandDouble(d2, d3);
    }

    public static double getItemDamageMinMax(String string, ItemStack itemStack, int n) {
        double d = 0.0;
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return d;
        }
        DamageType damageType = plugin.getCFG().getDamageTypes().get(string.toLowerCase());
        if (damageType != null) {
            String string2 = plugin.getCFG().getDamageTypeFormat();
            String string3 = string2.replace("%type_value%", damageType.getValue()).replace("%type_name%", damageType.getName()).replace("%type_prefix%", damageType.getPrefix());
            List list = itemStack.getItemMeta().getLore();
            for (String string4 : list) {
                if (!string4.startsWith(string3)) continue;
                String string5 = string4.replace(string3, "");
                return ItemAPI.getDamageFromString(string5, n);
            }
        }
        if (d == 0.0 && damageType.isDefault()) {
            d = ItemAPI.getDefaultDamage(itemStack);
        }
        return d;
    }

    public static boolean hasDamageType(String string, ItemStack itemStack) {
        DamageType damageType = plugin.getCFG().getDamageTypes().get(string.toLowerCase());
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return false;
        }
        if (damageType != null) {
            String string2 = plugin.getCFG().getDamageTypeFormat();
            String string3 = string2.replace("%type_value%", damageType.getValue()).replace("%type_name%", damageType.getName()).replace("%type_prefix%", damageType.getPrefix());
            List list = itemStack.getItemMeta().getLore();
            for (String string4 : list) {
                if (!string4.startsWith(string3)) continue;
                return true;
            }
        }
        return false;
    }

    public static double getItemDamage(String string, ItemStack itemStack) {
        DamageType damageType = plugin.getCFG().getDamageTypes().get(string.toLowerCase());
        double d = 0.0;
        if (damageType.isDefault()) {
            d = ItemAPI.getDefaultDamage(itemStack);
        }
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return d;
        }
        if (ItemAPI.hasDamageType(string, itemStack)) {
            double d2 = ItemAPI.getItemDamageMinMax(string, itemStack, 0);
            double d3 = ItemAPI.getItemDamageMinMax(string, itemStack, 1);
            if (d3 == 0.0) {
                d3 = d2;
            }
            d = Utils.getRandDouble(d2, d3);
        }
        return d;
    }

    public static double getAttribute(ItemStack itemStack, ItemStat itemStat) {
        double d = 0.0;
        double d2 = ItemAPI.getBase(itemStack, itemStat);
        double d3 = ItemAPI.getBonus(itemStack, itemStat);
        if (d2 == 0.0 && d3 != 0.0 && itemStat == ItemStat.RANGE) {
            d2 = 3.0;
        }
        if (itemStat.isPercent()) {
            d = d2 + d3;
        } else {
            if (d2 == 0.0 && d3 != 0.0) {
                d2 = 1.0;
            }
            d = d2 * (1.0 + d3 / 100.0);
        }
        if (d > itemStat.getCapability()) {
            d = itemStat.getCapability();
        }
        return d;
    }

    public static double getBase(ItemStack itemStack, ItemStat itemStat) {
        String string = plugin.getCFG().getAttributeFormat();
        String string2 = string.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getPrefix());
        double d = 0.0;
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return d;
        }
        if (plugin.getMM().getGemManager().getItemGemStats(itemStack, false).containsKey((Object)itemStat)) {
            d += plugin.getMM().getGemManager().getItemGemStats(itemStack, false).get((Object)itemStat).doubleValue();
        }
        List list = itemStack.getItemMeta().getLore();
        for (String string3 : list) {
            if (!string3.startsWith(string2)) continue;
            String string4 = ChatColor.stripColor((String)string3.replace(string2, "").replace(plugin.getCFG().getStrPercent(), "").replace(plugin.getCFG().getStrModifier(), ""));
            double d2 = Double.parseDouble(string4);
            d += d2;
            break;
        }
        return d;
    }

    public static double getBonus(ItemStack itemStack, ItemStat itemStat) {
        String string = plugin.getCFG().getAttributeFormat();
        String string2 = string.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getBonus());
        double d = 0.0;
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return d;
        }
        if (plugin.getMM().getGemManager().getItemGemStats(itemStack, true).containsKey((Object)itemStat)) {
            d += plugin.getMM().getGemManager().getItemGemStats(itemStack, true).get((Object)itemStat).doubleValue();
        }
        List list = itemStack.getItemMeta().getLore();
        for (String string3 : list) {
            if (!string3.startsWith(string2)) continue;
            String string4 = ChatColor.stripColor((String)string3.replace(string2, "").replace(plugin.getCFG().getStrPercent(), "").replace(plugin.getCFG().getStrModifier(), ""));
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
        return d;
    }

    public static double getFinalDamageByType(String string, LivingEntity livingEntity) {
        DamageType damageType = plugin.getCFG().getDamageTypeById(string);
        double d = 0.0;
        if (damageType == null) {
            return d;
        }
        Block block = livingEntity.getLocation().getBlock();
        String string2 = block.getBiome().name();
        ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, false);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack[n2];
            if (ItemAPI.hasDamageType(damageType.getId(), itemStack) || damageType.isDefault()) {
                if (d == 0.0) {
                    d = ItemAPI.getItemDamageMinMax(damageType.getId(), itemStack, 1);
                }
                if (plugin.getMM().getGemManager().isActive()) {
                    HashMap<DamageType, Double> hashMap;
                    HashMap<DamageType, Double> hashMap2 = plugin.getMM().getGemManager().getItemGemDamages(itemStack, false);
                    if (hashMap2.containsKey(damageType)) {
                        d += hashMap2.get(damageType).doubleValue();
                    }
                    if ((hashMap = plugin.getMM().getGemManager().getItemGemDamages(itemStack, true)).containsKey(damageType)) {
                        d *= 1.0 + hashMap.get(damageType) / 100.0;
                    }
                }
                d *= damageType.getDamageModifierByBiome(string2);
            }
            ++n2;
        }
        return d;
    }

    public static HashMap<DamageType, Double> getDamageTypes(LivingEntity livingEntity) {
        HashMap<DamageType, Double> hashMap = new HashMap<DamageType, Double>();
        Block block = livingEntity.getLocation().getBlock();
        String string = block.getBiome().name();
        ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, false);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack[n2];
            if (itemStack != null && itemStack.hasItemMeta()) {
                for (DamageType damageType : plugin.getCFG().getDamageTypes().values()) {
                    Object object;
                    Object object2;
                    if (!ItemAPI.hasDamageType(damageType.getId(), itemStack) && !damageType.isDefault()) continue;
                    double d = ItemAPI.getItemDamage(damageType.getId(), itemStack);
                    if (plugin.getMM().getGemManager().isActive()) {
                        object = plugin.getMM().getGemManager().getItemGemDamages(itemStack, false);
                        if (object.containsKey(damageType)) {
                            d += object.get(damageType).doubleValue();
                        }
                        if ((object2 = plugin.getMM().getGemManager().getItemGemDamages(itemStack, true)).containsKey(damageType)) {
                            d *= 1.0 + (Double)object2.get(damageType) / 100.0;
                        }
                    }
                    if (plugin.getMM().getBuffManager().isActive()) {
                        object2 = plugin.getMM().getBuffManager().getEntityBuffs(livingEntity).iterator();
                        while (object2.hasNext()) {
                            object = object2.next();
                            if (object.getType() != BuffManager.BuffType.DAMAGE || !object.getValue().equalsIgnoreCase(damageType.getId())) continue;
                            d *= 1.0 + object.getModifier() / 100.0;
                            break;
                        }
                    }
                    d *= damageType.getDamageModifierByBiome(string);
                    if (hashMap.containsKey(damageType)) {
                        double d2 = hashMap.get(damageType);
                        d += d2;
                    }
                    hashMap.put(damageType, d);
                }
            }
            ++n2;
        }
        return hashMap;
    }

    public static HashMap<ArmorType, Double> getDefenseTypes(LivingEntity livingEntity) {
        HashMap<ArmorType, Double> hashMap = new HashMap<ArmorType, Double>();
        ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, true);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack object = arritemStack[n2];
            if (object != null && object.hasItemMeta() && object.getItemMeta().hasLore()) {
                ItemMeta itemMeta = object.getItemMeta();
                List list = itemMeta.getLore();
                String string = plugin.getCFG().getArmorTypeFormat();
                block1 : for (ArmorType armorType : plugin.getCFG().getArmorTypes().values()) {
                    String string2 = string.replace("%type_prefix%", armorType.getPrefix()).replace("%type_name%", armorType.getName()).replace("%type_value%", armorType.getValue());
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()) {
                        Object object2;
                        Object object3;
                        String string3 = (String)iterator.next();
                        if (!string3.startsWith(string2)) continue;
                        String string4 = ChatColor.stripColor((String)string3.replace(plugin.getCFG().getStrPercent(), "").replace(string2, ""));
                        double d = Double.parseDouble(string4);
                        if (plugin.getMM().getGemManager().isActive()) {
                            object2 = plugin.getMM().getGemManager().getItemGemArmors(object, false);
                            if (object2.containsKey(armorType)) {
                                d += object2.get(armorType).doubleValue();
                            }
                            if ((object3 = plugin.getMM().getGemManager().getItemGemArmors(object, true)).containsKey(armorType)) {
                                d *= 1.0 + (Double)object3.get(armorType) / 100.0;
                            }
                        }
                        if (plugin.getMM().getBuffManager().isActive()) {
                            object3 = plugin.getMM().getBuffManager().getEntityBuffs(livingEntity).iterator();
                            while (object3.hasNext()) {
                                object2 = object3.next();
                                if (object2.getType() != BuffManager.BuffType.DEFENSE || !object2.getValue().equalsIgnoreCase(armorType.getId())) continue;
                                d *= 1.0 + object2.getModifier() / 100.0;
                                break;
                            }
                        }
                        if (hashMap.containsKey(armorType)) {
                            double d2 = hashMap.get(armorType);
                            d += d2;
                        }
                        hashMap.put(armorType, d);
                        continue block1;
                    }
                }
            }
            ++n2;
        }
        if (hashMap.isEmpty()) {
            for (DamageType damageType : plugin.getCFG().getDamageTypes().values()) {
                if (!damageType.isDefault()) continue;
                double d = ItemAPI.getDefaultDefense(livingEntity);
                for (ArmorType armorType : plugin.getCFG().getArmorTypes().values()) {
                    if (!armorType.getBlockDamageTypes().contains(damageType.getId())) continue;
                    hashMap.put(armorType, d);
                }
            }
        }
        return hashMap;
    }

    public static double getDefaultDamage(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR || ItemUtils.isArmor(itemStack)) {
            return 0.0;
        }
        double d = 1.0;
        if (itemStack.getType() == Material.DIAMOND_SWORD || itemStack.getType() == Material.GOLD_AXE || itemStack.getType() == Material.WOOD_AXE) {
            d = 7.0;
        } else if (itemStack.getType() == Material.DIAMOND_AXE || itemStack.getType() == Material.IRON_AXE || itemStack.getType() == Material.STONE_AXE || itemStack.getType().name().equals("TRIDENT")) {
            d = 9.0;
        } else if (itemStack.getType() == Material.DIAMOND_PICKAXE || itemStack.getType() == Material.DIAMOND_SPADE || itemStack.getType() == Material.STONE_SWORD) {
            d = 5.0;
        } else if (itemStack.getType() == Material.IRON_SWORD) {
            d = 6.0;
        } else if (itemStack.getType() == Material.IRON_PICKAXE || itemStack.getType() == Material.IRON_SPADE || itemStack.getType() == Material.GOLD_SWORD || itemStack.getType() == Material.WOOD_SWORD) {
            d = 4.0;
        } else if (itemStack.getType() == Material.STONE_PICKAXE || itemStack.getType() == Material.STONE_SPADE) {
            d = 3.0;
        } else if (itemStack.getType() == Material.GOLD_PICKAXE || itemStack.getType() == Material.GOLD_SPADE || itemStack.getType() == Material.WOOD_PICKAXE || itemStack.getType() == Material.WOOD_SPADE) {
            d = 2.0;
        }
        return d;
    }

    public static double getDefaultDefense(ItemStack itemStack) {
        if (itemStack == null) {
            return 0.0;
        }
        if (itemStack.getType() == Material.LEATHER_LEGGINGS || itemStack.getType() == Material.CHAINMAIL_HELMET || itemStack.getType() == Material.IRON_HELMET || itemStack.getType() == Material.IRON_BOOTS || itemStack.getType() == Material.GOLD_HELMET) {
            return 2.0;
        }
        if (itemStack.getType() == Material.LEATHER_CHESTPLATE || itemStack.getType() == Material.DIAMOND_HELMET || itemStack.getType() == Material.DIAMOND_BOOTS || itemStack.getType() == Material.GOLD_LEGGINGS) {
            return 3.0;
        }
        if (itemStack.getType() == Material.CHAINMAIL_LEGGINGS) {
            return 4.0;
        }
        if (itemStack.getType() == Material.CHAINMAIL_CHESTPLATE || itemStack.getType() == Material.IRON_LEGGINGS || itemStack.getType() == Material.GOLD_CHESTPLATE) {
            return 5.0;
        }
        if (itemStack.getType() == Material.IRON_CHESTPLATE || itemStack.getType() == Material.DIAMOND_LEGGINGS) {
            return 6.0;
        }
        if (itemStack.getType() == Material.DIAMOND_CHESTPLATE) {
            return 8.0;
        }
        return 0.0;
    }

    public static double getDefaultToughness(ItemStack itemStack) {
        if (itemStack == null || !ItemUtils.isArmor(itemStack)) {
            return 0.0;
        }
        if (itemStack.getType().name().startsWith("DIAMOND_")) {
            return 2.0;
        }
        return 0.0;
    }

    public static double getDefaultDefense(LivingEntity livingEntity) {
        double d = 0.0;
        ItemStack[] arritemStack = EntityAPI.getEquipment(livingEntity, true);
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack[n2];
            if (itemStack != null) {
                if (itemStack.getType() == Material.LEATHER_LEGGINGS || itemStack.getType() == Material.CHAINMAIL_HELMET || itemStack.getType() == Material.IRON_HELMET || itemStack.getType() == Material.IRON_BOOTS || itemStack.getType() == Material.GOLD_HELMET) {
                    d += 2.0;
                } else if (itemStack.getType() == Material.LEATHER_CHESTPLATE || itemStack.getType() == Material.DIAMOND_HELMET || itemStack.getType() == Material.DIAMOND_BOOTS || itemStack.getType() == Material.GOLD_LEGGINGS) {
                    d += 3.0;
                } else if (itemStack.getType() == Material.CHAINMAIL_LEGGINGS) {
                    d += 4.0;
                } else if (itemStack.getType() == Material.CHAINMAIL_CHESTPLATE || itemStack.getType() == Material.IRON_LEGGINGS || itemStack.getType() == Material.GOLD_CHESTPLATE) {
                    d += 5.0;
                } else if (itemStack.getType() == Material.IRON_CHESTPLATE || itemStack.getType() == Material.DIAMOND_LEGGINGS) {
                    d += 6.0;
                } else if (itemStack.getType() == Material.DIAMOND_CHESTPLATE) {
                    d += 8.0;
                }
            }
            ++n2;
        }
        return d;
    }

    public static double getDefaultAttackSpeed(ItemStack itemStack) {
        if (itemStack == null) {
            return -0.5;
        }
        double d = -0.5;
        switch (ItemAPI.$SWITCH_TABLE$org$bukkit$Material()[itemStack.getType().ordinal()]) {
            case 255: 
            case 268: 
            case 272: 
            case 276: 
            case 278: 
            case 283: 
            case 285: 
            case 289: 
            case 293: {
                d = 3.0;
                break;
            }
            case 266: 
            case 267: 
            case 271: 
            case 275: 
            case 282: {
                d = 2.4;
                break;
            }
            case 256: 
            case 269: 
            case 273: 
            case 277: 
            case 284: {
                d = 2.8;
                break;
            }
            case 257: 
            case 270: 
            case 274: {
                d = 3.2;
                break;
            }
            case 290: {
                d = 2.0;
                break;
            }
            case 291: {
                d = 1.0;
                break;
            }
            case 292: {
                d = 0.0;
                break;
            }
            default: {
                d = -0.5;
            }
        }
        return d;
    }

    public static ItemStack setAmmoType(ItemStack itemStack, AmmoType ammoType, int n) {
        if (!ammoType.isEnabled()) {
            return itemStack;
        }
        if (itemStack.getType() != Material.BOW) {
            return itemStack;
        }
        String string = plugin.getCFG().getAmmoTypeFormat();
        String string2 = string.replace("%type_name%", ammoType.getName()).replace("%type_prefix%", ammoType.getPrefix());
        int n2 = 0;
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        for (String string3 : arrayList) {
            if (!string3.startsWith(string2)) continue;
            n2 = arrayList.indexOf(string3);
            arrayList.remove(n2);
            break;
        }
        n2 = n;
        if (n2 < 0 || n2 >= arrayList.size()) {
            arrayList.add(string2);
        } else {
            arrayList.add(n2, string2);
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static AmmoType getAmmoType(ItemStack itemStack) {
        AmmoType ammoType = AmmoType.ARROW;
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return ammoType;
        }
        String string = plugin.getCFG().getAmmoTypeFormat();
        AmmoType[] arrammoType = AmmoType.values();
        int n = arrammoType.length;
        int n2 = 0;
        while (n2 < n) {
            AmmoType ammoType2 = arrammoType[n2];
            String string2 = string.replace("%type_name%", ammoType2.getName()).replace("%type_prefix%", ammoType2.getPrefix());
            ItemMeta itemMeta = itemStack.getItemMeta();
            List list = itemMeta.getLore();
            if (list == null || list.isEmpty()) {
                return ammoType;
            }
            for (String string3 : list) {
                if (!string3.equals(string2)) continue;
                return ammoType2;
            }
            ++n2;
        }
        return ammoType;
    }

    public static ItemStack setDurability(ItemStack itemStack, int n, int n2) {
        if (n > n2) {
            n = n2;
        }
        String string = plugin.getCFG().getAttributeFormat();
        ItemStat itemStat = ItemStat.DURABILITY;
        String string2 = string.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getPrefix());
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        for (String string3 : list) {
            if (!string3.startsWith(string2)) continue;
            int n3 = list.indexOf(string3);
            list.remove(n3);
            list.add(n3, String.valueOf(string2) + n + plugin.getCFG().getStrDurabilitySeparator() + itemStat.getValue() + n2);
            itemMeta.setLore(list);
            itemStack.setItemMeta(itemMeta);
            break;
        }
        return itemStack;
    }

    public static ItemStack setFinalDurability(ItemStack itemStack, Entity entity, int n) {
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (ItemAPI.getDurability(itemStack, 0) > 0) {
                DivineItemDamageEvent divineItemDamageEvent = new DivineItemDamageEvent(itemStack, player);
                plugin.getPluginManager().callEvent((Event)divineItemDamageEvent);
                if (divineItemDamageEvent.isCancelled()) {
                    return itemStack;
                }
                if (ItemAPI.getDurability(itemStack = ItemAPI.reduceDurability(itemStack, n), 0) <= 0 && plugin.getCFG().breakItems()) {
                    itemStack.setAmount(0);
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
                }
            }
        }
        return itemStack;
    }

    public static ItemStack reduceDurability(ItemStack itemStack, int n) {
        int n2;
        ItemStack itemStack2 = itemStack;
        if (itemStack2.containsEnchantment(Enchantment.DURABILITY)) {
            n2 = itemStack2.getEnchantmentLevel(Enchantment.DURABILITY);
            if (r.nextInt(200) <= n2 * 10) {
                return itemStack2;
            }
        }
        if ((n2 = ItemAPI.getDurability(itemStack2, 0)) == -999) {
            return itemStack2;
        }
        int n3 = ItemAPI.getDurability(itemStack2, 1);
        itemStack2 = ItemAPI.setDurability(itemStack2, n2 - n, n3);
        return itemStack2;
    }

    /*
     * Exception decompiling
     */
    public static ItemStack getItemByModule(String var0, String var1_1, int var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:416)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:196)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:141)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:379)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:867)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:768)
        // org.benf.cfr.reader.Main.doJar(Main.java:140)
        // org.benf.cfr.reader.Main.main(Main.java:241)
        throw new IllegalStateException("Decompilation failed");
    }

    public static ItemStack setUntradeable(ItemStack itemStack, String string, int n) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        String string2 = plugin.getMM().getSoulboundManager().getUntradeString();
        if (string.contains("true")) {
            if (n > 0 && arrayList.size() > n) {
                arrayList.add(n, string2);
            } else {
                arrayList.add(string2);
            }
        } else if (string.contains("false")) {
            for (String string3 : arrayList) {
                if (!string3.equals(string2)) continue;
                arrayList.remove(string3);
                break;
            }
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setLevelRequired(ItemStack itemStack, int n, int n2) {
        String string2;
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        int n3 = 0;
        String[] arrstring = plugin.getCFG().getStrLevel().split("%n");
        for (String string2 : arrayList) {
            if ((arrstring.length != 1 || !string2.contains(arrstring[0])) && (arrstring.length != 2 || !string2.contains(arrstring[0]) && !string2.contains(arrstring[1]))) continue;
            n3 = arrayList.indexOf(string2);
            arrayList.remove(string2);
            break;
        }
        if (n2 > 0) {
            n3 = n2;
        }
        string2 = plugin.getCFG().getStrLevel().replace("%n", String.valueOf(n));
        if (n3 < 0 || n3 >= arrayList.size()) {
            arrayList.add(string2);
        } else {
            arrayList.add(n3, string2);
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setClassRequired(ItemStack itemStack, String string, int n) {
        String string22;
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        int n2 = 0;
        String string3 = plugin.getCFG().getStrClass();
        for (String string22 : arrayList) {
            if (!string22.contains(string3)) continue;
            n2 = arrayList.indexOf(string22);
            arrayList.remove(string22);
            break;
        }
        if (n > 0) {
            n2 = n;
        }
        string22 = String.valueOf(plugin.getCFG().getStrClass()) + plugin.getCFG().getStrClassColor() + string.replace(",", plugin.getCFG().getStrClassSeparator());
        if (n2 < 0 || n2 >= arrayList.size()) {
            arrayList.add(string22);
        } else {
            arrayList.add(n2, string22);
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setSoulboundRequired(ItemStack itemStack, String string, int n) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return itemStack;
        }
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        String string2 = plugin.getMM().getSoulboundManager().getSoulString();
        for (String string3 : arrayList) {
            if (!string3.equals(string2)) continue;
            arrayList.remove(string3);
            break;
        }
        if (string.contains("true")) {
            if (n > 0 && n < arrayList.size()) {
                arrayList.add(n, string2);
            } else {
                arrayList.add(string2);
            }
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack addAttribute(ItemStack itemStack, ItemStat itemStat, boolean bl, String string, String string2, int n) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> list = itemMeta.getLore();
        if (list == null) {
            list = new ArrayList();
        }
        String string3 = plugin.getCFG().getAttributeFormat();
        String string4 = string3.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getPrefix());
        if (bl) {
            string4 = string3.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getBonus());
        }
        int n2 = -1;
        for (String string5 : list) {
            if (!string5.startsWith(string4)) continue;
            n2 = list.indexOf(string5);
            list.remove(string5);
            break;
        }
        if (n > 0) {
            n2 = n;
        }
        if (n2 >= list.size()) {
            n2 = -1;
        }
        switch (ItemAPI.$SWITCH_TABLE$su$nightexpress$divineitems$attributes$ItemStat()[itemStat.ordinal()]) {
            case 19: {
                if (!ItemUtils.isWeapon(itemStack)) break;
                double d = -1.0;
                try {
                    d = Double.parseDouble(string) + ItemAPI.getAttribute(itemStack, itemStat);
                }
                catch (NumberFormatException numberFormatException) {
                    return itemStack;
                }
                if (bl) {
                    if (d >= 0.0) {
                        string = String.valueOf(plugin.getCFG().getStrPositive()) + string + plugin.getCFG().getStrPercent();
                    }
                    if (d < 0.0) {
                        string = String.valueOf(plugin.getCFG().getStrNegative()) + string + plugin.getCFG().getStrPercent();
                    }
                    if (n2 < 0 || n2 >= list.size()) {
                        list.add(String.valueOf(string4) + string);
                    } else {
                        list.add(n2, String.valueOf(string4) + string);
                    }
                } else if (d > 0.0) {
                    if (n2 < 0 || n2 >= list.size()) {
                        list.add(String.valueOf(string4) + ChatColor.stripColor((String)plugin.getCFG().getStrPositive()) + string + plugin.getCFG().getStrPercent());
                    } else {
                        list.add(n2, String.valueOf(string4) + ChatColor.stripColor((String)plugin.getCFG().getStrPositive()) + string + plugin.getCFG().getStrPercent());
                    }
                }
                if (itemStat.getCapability() > 0.0 && d > itemStat.getCapability()) {
                    d = itemStat.getCapability();
                }
                itemStack = plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.attackSpeed, d / 1000.0);
                itemStack = plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.attackDamage, ItemAPI.getDefaultDamage(itemStack));
                itemMeta = itemStack.getItemMeta();
                break;
            }
            case 21: {
                double d = -1.0;
                try {
                    d = Double.parseDouble(string);
                }
                catch (NumberFormatException numberFormatException) {
                    return itemStack;
                }
                double d2 = ItemAPI.getAttribute(itemStack, itemStat);
                if (bl) {
                    d = d2 <= 0.0 ? 0.0 : d2 * (1.0 + d / 100.0);
                    if (d >= 0.0) {
                        string = String.valueOf(plugin.getCFG().getStrPositive()) + string + plugin.getCFG().getStrPercent();
                    }
                    if (d < 0.0) {
                        string = String.valueOf(plugin.getCFG().getStrNegative()) + string + plugin.getCFG().getStrPercent();
                    }
                    if (n2 < 0 || n2 >= list.size()) {
                        list.add(String.valueOf(string4) + string);
                    } else {
                        list.add(n2, String.valueOf(string4) + string);
                    }
                } else if ((d += d2) > 0.0) {
                    if (n2 < 0 || n2 >= list.size()) {
                        list.add(String.valueOf(string4) + ChatColor.stripColor((String)plugin.getCFG().getStrPositive()) + string);
                    } else {
                        list.add(n2, String.valueOf(string4) + ChatColor.stripColor((String)plugin.getCFG().getStrPositive()) + string);
                    }
                }
                if (itemStat.getCapability() > 0.0 && d > itemStat.getCapability()) {
                    d = itemStat.getCapability();
                }
                if (!ItemUtils.isArmor(itemStack = plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.maxHealth, d))) {
                    itemStack = plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.attackDamage, ItemAPI.getDefaultDamage(itemStack));
                } else {
                    itemStack = plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.armor, ItemAPI.getDefaultDefense(itemStack));
                    itemStack = plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.armorToughness, ItemAPI.getDefaultToughness(itemStack));
                }
                itemMeta = itemStack.getItemMeta();
                break;
            }
            case 17: {
                double d = -1.0;
                try {
                    d = Double.parseDouble(string) + ItemAPI.getAttribute(itemStack, itemStat);
                }
                catch (NumberFormatException numberFormatException) {
                    return itemStack;
                }
                if (bl) {
                    if (d >= 0.0) {
                        string = String.valueOf(plugin.getCFG().getStrPositive()) + string + plugin.getCFG().getStrPercent();
                    }
                    if (d < 0.0) {
                        string = String.valueOf(plugin.getCFG().getStrNegative()) + string + plugin.getCFG().getStrPercent();
                    }
                    if (n2 < 0 || n2 >= list.size()) {
                        list.add(String.valueOf(string4) + string);
                    } else {
                        list.add(n2, String.valueOf(string4) + string);
                    }
                } else if (d > 0.0) {
                    if (n2 < 0 || n2 >= list.size()) {
                        list.add(String.valueOf(string4) + ChatColor.stripColor((String)plugin.getCFG().getStrPositive()) + string + plugin.getCFG().getStrPercent());
                    } else {
                        list.add(n2, String.valueOf(string4) + ChatColor.stripColor((String)plugin.getCFG().getStrPositive()) + string + plugin.getCFG().getStrPercent());
                    }
                }
                if (itemStat.getCapability() > 0.0 && d > itemStat.getCapability()) {
                    d = itemStat.getCapability();
                }
                if (!ItemUtils.isArmor(itemStack = new ItemStack(plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.movementSpeed, d)))) {
                    itemStack = plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.attackDamage, ItemAPI.getDefaultDamage(itemStack));
                } else {
                    itemStack = plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.armor, ItemAPI.getDefaultDefense(itemStack));
                    itemStack = plugin.getNMS().setNBTAtt(itemStack, NBTAttribute.armorToughness, ItemAPI.getDefaultToughness(itemStack));
                }
                itemMeta = itemStack.getItemMeta();
                break;
            }
            case 15: {
                if (bl) break;
                double d = -1.0;
                try {
                    d = Double.parseDouble(string);
                }
                catch (NumberFormatException numberFormatException) {
                    return itemStack;
                }
                itemStack = ItemAPI.removeAttribute(itemStack, ItemStat.DURABILITY_UNBREAK);
                list = itemStack.getItemMeta().getLore();
                if (list == null) {
                    list = new ArrayList();
                }
                if (d <= 0.0) break;
                int n3 = (int)d;
                if (n2 < 0 || n2 >= list.size()) {
                    list.add(String.valueOf(string4) + n3 + plugin.getCFG().getStrDurabilitySeparator() + itemStat.getValue() + n3);
                    break;
                }
                list.add(n2, String.valueOf(string4) + n3 + plugin.getCFG().getStrDurabilitySeparator() + itemStat.getValue() + n3);
                break;
            }
            case 16: {
                if (bl) break;
                list = (itemStack = ItemAPI.removeAttribute(itemStack, ItemStat.DURABILITY)).getItemMeta().getLore();
                if (list == null) {
                    list = new ArrayList();
                }
                double d = -1.0;
                try {
                    d = Double.parseDouble(string);
                }
                catch (NumberFormatException numberFormatException) {
                    return itemStack;
                }
                if (d > 0.0) {
                    if (n2 < 0 || n2 >= list.size()) {
                        list.add(string4);
                    } else {
                        list.add(n2, string4);
                    }
                }
                itemStack.getItemMeta().spigot().setUnbreakable(true);
                break;
            }
            default: {
                double d = -1.0;
                try {
                    d = Double.parseDouble(string);
                }
                catch (NumberFormatException numberFormatException) {
                    return itemStack;
                }
                if (itemStat == ItemStat.RANGE && (itemStack.getType() == Material.BOW || d <= 0.0)) break;
                if (itemStat.isPercent() && !bl) {
                    string = String.valueOf(string) + plugin.getCFG().getStrPercent();
                }
                if (itemStat == ItemStat.CRITICAL_DAMAGE && !bl) {
                    string = String.valueOf(string) + plugin.getCFG().getStrModifier();
                }
                if (bl) {
                    if (d > 0.0) {
                        string = String.valueOf(plugin.getCFG().getStrPositive()) + string + plugin.getCFG().getStrPercent();
                    }
                    if (d < 0.0) {
                        string = String.valueOf(plugin.getCFG().getStrNegative()) + string + plugin.getCFG().getStrPercent();
                    }
                } else if (itemStat.isPlus() && d > 0.0) {
                    string = String.valueOf(ChatColor.stripColor((String)plugin.getCFG().getStrPositive())) + string;
                }
                if (n2 < 0 || n2 >= list.size()) {
                    list.add(String.valueOf(string4) + string);
                    break;
                }
                list.add(n2, String.valueOf(string4) + string);
            }
        }
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack removeAttribute(ItemStack itemStack, ItemStat itemStat) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return itemStack;
        }
        String string = plugin.getCFG().getAttributeFormat();
        String string2 = string.replace("%att_value%", itemStat.getValue()).replace("%att_name%", itemStat.getName()).replace("%att_prefix%", itemStat.getPrefix());
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        for (String string3 : list) {
            if (!string3.startsWith(string2)) continue;
            list.remove(string3);
            break;
        }
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack addNBTTag(ItemStack itemStack, String string, String string2) {
        NBTItem nBTItem = new NBTItem(itemStack);
        if (StringUtils.isNumeric((String)string2)) {
            if (Double.valueOf(string2) != null) {
                nBTItem.setDouble(string, Double.parseDouble(string2));
            } else if (Integer.valueOf(string2) != null) {
                nBTItem.setInteger(string, Integer.parseInt(string2));
            }
        } else if (string2.equals("true") || string2.equals("false")) {
            boolean bl = Boolean.valueOf(string2);
            nBTItem.setBoolean(string, bl);
        } else {
            nBTItem.setString(string, string2);
        }
        return nBTItem.getItem();
    }

    public static ItemStack delNBTTag(ItemStack itemStack, String string) {
        NBTItem nBTItem = new NBTItem(itemStack);
        nBTItem.removeKey(string);
        return nBTItem.getItem();
    }

    public static ItemStack addDamageType(ItemStack itemStack, DamageType damageType, double d, double d2, int n) {
        String string4;
        String string2 = plugin.getCFG().getDamageTypeFormat();
        String string3 = string2.replace("%type_value%", damageType.getValue()).replace("%type_name%", damageType.getName()).replace("%type_prefix%", damageType.getPrefix());
        int n2 = 0;
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        for (String string4 : arrayList) {
            if (!string4.startsWith(string3)) continue;
            n2 = arrayList.indexOf(string4);
            arrayList.remove(n2);
            break;
        }
        n2 = n;
        string4 = String.valueOf(string3) + Math.min(d, d2) + plugin.getCFG().getStrDamageSeparator() + damageType.getValue() + Math.max(d, d2);
        if (n2 < 0 || n2 >= arrayList.size()) {
            arrayList.add(string4);
        } else {
            arrayList.add(n2, string4);
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack addDefenseType(ItemStack itemStack, ArmorType armorType, double d, int n) {
        String string4;
        d = Utils.round3(d);
        String string2 = plugin.getCFG().getArmorTypeFormat();
        String string3 = string2.replace("%type_value%", armorType.getValue()).replace("%type_name%", armorType.getName()).replace("%type_prefix%", armorType.getPrefix());
        int n2 = 0;
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        for (String string4 : arrayList) {
            if (!string4.startsWith(string3)) continue;
            n2 = arrayList.indexOf(string4);
            arrayList.remove(n2);
            break;
        }
        n2 = n;
        string4 = armorType.isPercent() ? String.valueOf(string3) + plugin.getCFG().getStrPositive() + d + plugin.getCFG().getStrPercent() : String.valueOf(string3) + d;
        if (n2 < 0 || n2 >= arrayList.size()) {
            arrayList.add(string4);
        } else {
            arrayList.add(n2, string4);
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack addAbility(ItemStack itemStack, String string, int n, String string2, boolean bl, int n2, int n3) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        AbilityManager.Ability ability = plugin.getMM().getAbilityManager().getAbilityById(string);
        String string3 = plugin.getMM().getAbilityManager().getSettings().getLeftClick();
        if (string2.equalsIgnoreCase("RIGHT")) {
            string3 = plugin.getMM().getAbilityManager().getSettings().getRightClick();
        }
        String string4 = "";
        if (bl) {
            string4 = String.valueOf(plugin.getMM().getAbilityManager().getSettings().getShiftClick()) + " ";
        }
        String string5 = plugin.getMM().getAbilityManager().getSettings().getCD().replace("%s", String.valueOf(n2));
        String string6 = String.valueOf(plugin.getMM().getAbilityManager().getSettings().getFilledSlot()) + ability.getName().replace("%level%", new StringBuilder(String.valueOf(n)).toString()).replace("%rlevel%", Utils.IntegerToRomanNumeral(n)) + " " + string4 + string3 + " " + string5;
        for (String object2 : arrayList) {
            if (!object2.contains(ability.getName().replace("%rlevel%", "").replace("%level%", "").trim())) continue;
            arrayList.remove(object2);
            break;
        }
        if (n3 > 0) {
            arrayList.add(n3, string6);
        } else {
            arrayList.add(string6);
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        NBTItem nBTItem = new NBTItem(itemStack);
        nBTItem.setString("ABILITY_" + plugin.getMM().getAbilityManager().getItemAbsAmount(itemStack), String.valueOf(ability.getIdName().toLowerCase()) + ":" + n + ":" + n2 + ":" + string2.toUpperCase() + ":" + bl);
        return nBTItem.getItem();
    }

    public static ItemStack addDivineSlot(ItemStack itemStack, SlotType slotType, int n) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        String string = slotType.getEmpty();
        if (n < 0 || n >= arrayList.size()) {
            arrayList.add(string);
        } else {
            arrayList.add(n, string);
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack addFlag(ItemStack itemStack, ItemFlag itemFlag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(new ItemFlag[]{itemFlag});
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack delFlag(ItemStack itemStack, ItemFlag itemFlag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.removeItemFlags(new ItemFlag[]{itemFlag});
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setName(ItemStack itemStack, String string) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes((char)'&', (String)string.trim()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack addLoreLine(ItemStack itemStack, String string, int n) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = itemMeta.getLore();
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
        }
        if (n > 0 && n < arrayList.size()) {
            arrayList.add(n, ChatColor.translateAlternateColorCodes((char)'&', (String)string));
        } else {
            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string));
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack delLoreLine(ItemStack itemStack, int n) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        if (list == null) {
            return itemStack;
        }
        if (n >= list.size() || n < 0) {
            n = list.size() - 1;
        }
        list.remove(n);
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack delLoreLine(ItemStack itemStack, String string) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        if (list == null) {
            return itemStack;
        }
        if (!list.contains(string)) {
            return itemStack;
        }
        int n = list.indexOf(string);
        list.remove(n);
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack clearLore(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        if (list == null) {
            return itemStack;
        }
        itemMeta.setLore(new ArrayList());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack enchant(ItemStack itemStack, Enchantment enchantment, int n) {
        if (n <= 0) {
            itemStack.removeEnchantment(enchantment);
        } else {
            itemStack.addUnsafeEnchantment(enchantment, n);
        }
        return itemStack;
    }

    public static ItemStack addPotionEffect(ItemStack itemStack, PotionEffectType potionEffectType, int n, int n2, boolean bl, boolean bl2, boolean bl3) {
        PotionMeta potionMeta = (PotionMeta)itemStack.getItemMeta();
        if (--n < 0) {
            potionMeta.removeCustomEffect(potionEffectType);
        } else {
            potionMeta.addCustomEffect(new PotionEffect(potionEffectType, n2 * 20, n, bl, bl2), true);
        }
        itemStack.setItemMeta((ItemMeta)potionMeta);
        return itemStack;
    }

    public static ItemStack addPotionEffect(ItemStack itemStack, PotionEffectType potionEffectType, int n, int n2, boolean bl, boolean bl2, Color color) {
        PotionMeta potionMeta = (PotionMeta)itemStack.getItemMeta();
        if (--n < 0) {
            potionMeta.removeCustomEffect(potionEffectType);
        } else {
            potionMeta.addCustomEffect(new PotionEffect(potionEffectType, n2 * 20, n, bl, bl2, color), true);
        }
        itemStack.setItemMeta((ItemMeta)potionMeta);
        return itemStack;
    }

    public static ItemStack setEggType(ItemStack itemStack, EntityType entityType) {
        SpawnEggMeta spawnEggMeta = (SpawnEggMeta)itemStack.getItemMeta();
        spawnEggMeta.setSpawnedType(entityType);
        itemStack.setItemMeta((ItemMeta)spawnEggMeta);
        return itemStack;
    }

    public static ItemStack setLeatherColor(ItemStack itemStack, Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        itemStack.setItemMeta((ItemMeta)leatherArmorMeta);
        return itemStack;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$Material() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$org$bukkit$Material;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Material.values().length];
        try {
            arrn[Material.ACACIA_DOOR.ordinal()] = 197;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACACIA_DOOR_ITEM.ordinal()] = 429;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACACIA_FENCE.ordinal()] = 193;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACACIA_FENCE_GATE.ordinal()] = 188;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACACIA_STAIRS.ordinal()] = 164;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ACTIVATOR_RAIL.ordinal()] = 158;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.AIR.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ANVIL.ordinal()] = 146;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.APPLE.ordinal()] = 259;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ARMOR_STAND.ordinal()] = 415;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ARROW.ordinal()] = 261;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BAKED_POTATO.ordinal()] = 392;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BANNER.ordinal()] = 424;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BARRIER.ordinal()] = 167;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEACON.ordinal()] = 139;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BED.ordinal()] = 354;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEDROCK.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BED_BLOCK.ordinal()] = 27;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEETROOT.ordinal()] = 433;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEETROOT_BLOCK.ordinal()] = 208;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEETROOT_SEEDS.ordinal()] = 434;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BEETROOT_SOUP.ordinal()] = 435;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_DOOR.ordinal()] = 195;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_DOOR_ITEM.ordinal()] = 427;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_FENCE.ordinal()] = 190;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_FENCE_GATE.ordinal()] = 185;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BIRCH_WOOD_STAIRS.ordinal()] = 136;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLACK_GLAZED_TERRACOTTA.ordinal()] = 251;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLACK_SHULKER_BOX.ordinal()] = 235;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLAZE_POWDER.ordinal()] = 376;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLAZE_ROD.ordinal()] = 368;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLUE_GLAZED_TERRACOTTA.ordinal()] = 247;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BLUE_SHULKER_BOX.ordinal()] = 231;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT.ordinal()] = 332;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_ACACIA.ordinal()] = 446;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_BIRCH.ordinal()] = 444;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_DARK_OAK.ordinal()] = 447;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_JUNGLE.ordinal()] = 445;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOAT_SPRUCE.ordinal()] = 443;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BONE.ordinal()] = 351;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BONE_BLOCK.ordinal()] = 217;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOOK.ordinal()] = 339;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOOKSHELF.ordinal()] = 48;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOOK_AND_QUILL.ordinal()] = 385;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOW.ordinal()] = 260;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BOWL.ordinal()] = 280;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BREAD.ordinal()] = 296;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BREWING_STAND.ordinal()] = 118;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BREWING_STAND_ITEM.ordinal()] = 378;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BRICK.ordinal()] = 46;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BRICK_STAIRS.ordinal()] = 109;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BROWN_GLAZED_TERRACOTTA.ordinal()] = 248;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BROWN_MUSHROOM.ordinal()] = 40;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BROWN_SHULKER_BOX.ordinal()] = 232;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BUCKET.ordinal()] = 324;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.BURNING_FURNACE.ordinal()] = 63;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CACTUS.ordinal()] = 82;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CAKE.ordinal()] = 353;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CAKE_BLOCK.ordinal()] = 93;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CARPET.ordinal()] = 172;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CARROT.ordinal()] = 142;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CARROT_ITEM.ordinal()] = 390;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CARROT_STICK.ordinal()] = 397;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CAULDRON.ordinal()] = 119;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CAULDRON_ITEM.ordinal()] = 379;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHAINMAIL_BOOTS.ordinal()] = 304;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHAINMAIL_CHESTPLATE.ordinal()] = 302;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHAINMAIL_HELMET.ordinal()] = 301;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHAINMAIL_LEGGINGS.ordinal()] = 303;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHEST.ordinal()] = 55;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHORUS_FLOWER.ordinal()] = 201;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHORUS_FRUIT.ordinal()] = 431;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHORUS_FRUIT_POPPED.ordinal()] = 432;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CHORUS_PLANT.ordinal()] = 200;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CLAY.ordinal()] = 83;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CLAY_BALL.ordinal()] = 336;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CLAY_BRICK.ordinal()] = 335;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COAL.ordinal()] = 262;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COAL_BLOCK.ordinal()] = 174;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COAL_ORE.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COBBLESTONE.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COBBLESTONE_STAIRS.ordinal()] = 68;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COBBLE_WALL.ordinal()] = 140;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COCOA.ordinal()] = 128;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMMAND.ordinal()] = 138;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMMAND_CHAIN.ordinal()] = 212;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMMAND_MINECART.ordinal()] = 421;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMMAND_REPEATING.ordinal()] = 211;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COMPASS.ordinal()] = 344;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CONCRETE.ordinal()] = 252;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CONCRETE_POWDER.ordinal()] = 253;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_BEEF.ordinal()] = 363;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_CHICKEN.ordinal()] = 365;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_FISH.ordinal()] = 349;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_MUTTON.ordinal()] = 423;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKED_RABBIT.ordinal()] = 411;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.COOKIE.ordinal()] = 356;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CROPS.ordinal()] = 60;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CYAN_GLAZED_TERRACOTTA.ordinal()] = 245;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.CYAN_SHULKER_BOX.ordinal()] = 229;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_DOOR.ordinal()] = 198;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_DOOR_ITEM.ordinal()] = 430;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_FENCE.ordinal()] = 192;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_FENCE_GATE.ordinal()] = 187;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DARK_OAK_STAIRS.ordinal()] = 165;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DAYLIGHT_DETECTOR.ordinal()] = 152;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DAYLIGHT_DETECTOR_INVERTED.ordinal()] = 179;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DEAD_BUSH.ordinal()] = 33;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DETECTOR_RAIL.ordinal()] = 29;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND.ordinal()] = 263;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_AXE.ordinal()] = 278;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_BARDING.ordinal()] = 418;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_BLOCK.ordinal()] = 58;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_BOOTS.ordinal()] = 312;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_CHESTPLATE.ordinal()] = 310;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_HELMET.ordinal()] = 309;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_HOE.ordinal()] = 292;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_LEGGINGS.ordinal()] = 311;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_ORE.ordinal()] = 57;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_PICKAXE.ordinal()] = 277;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_SPADE.ordinal()] = 276;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIAMOND_SWORD.ordinal()] = 275;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIODE.ordinal()] = 355;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIODE_BLOCK_OFF.ordinal()] = 94;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIODE_BLOCK_ON.ordinal()] = 95;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DIRT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DISPENSER.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DOUBLE_PLANT.ordinal()] = 176;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DOUBLE_STEP.ordinal()] = 44;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DOUBLE_STONE_SLAB2.ordinal()] = 182;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DRAGONS_BREATH.ordinal()] = 436;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DRAGON_EGG.ordinal()] = 123;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.DROPPER.ordinal()] = 159;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EGG.ordinal()] = 343;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ELYTRA.ordinal()] = 442;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EMERALD.ordinal()] = 387;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EMERALD_BLOCK.ordinal()] = 134;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EMERALD_ORE.ordinal()] = 130;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EMPTY_MAP.ordinal()] = 394;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENCHANTED_BOOK.ordinal()] = 402;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENCHANTMENT_TABLE.ordinal()] = 117;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_CHEST.ordinal()] = 131;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_PEARL.ordinal()] = 367;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_PORTAL.ordinal()] = 120;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_PORTAL_FRAME.ordinal()] = 121;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ENDER_STONE.ordinal()] = 122;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.END_BRICKS.ordinal()] = 207;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.END_CRYSTAL.ordinal()] = 425;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.END_GATEWAY.ordinal()] = 210;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.END_ROD.ordinal()] = 199;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EXPLOSIVE_MINECART.ordinal()] = 406;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EXP_BOTTLE.ordinal()] = 383;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.EYE_OF_ENDER.ordinal()] = 380;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FEATHER.ordinal()] = 287;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FENCE.ordinal()] = 86;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FENCE_GATE.ordinal()] = 108;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FERMENTED_SPIDER_EYE.ordinal()] = 375;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FIRE.ordinal()] = 52;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FIREBALL.ordinal()] = 384;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FIREWORK.ordinal()] = 400;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FIREWORK_CHARGE.ordinal()] = 401;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FISHING_ROD.ordinal()] = 345;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FLINT.ordinal()] = 317;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FLINT_AND_STEEL.ordinal()] = 258;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FLOWER_POT.ordinal()] = 141;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FLOWER_POT_ITEM.ordinal()] = 389;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FROSTED_ICE.ordinal()] = 213;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.FURNACE.ordinal()] = 62;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GHAST_TEAR.ordinal()] = 369;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLASS.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLASS_BOTTLE.ordinal()] = 373;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLOWING_REDSTONE_ORE.ordinal()] = 75;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLOWSTONE.ordinal()] = 90;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GLOWSTONE_DUST.ordinal()] = 347;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLDEN_APPLE.ordinal()] = 321;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLDEN_CARROT.ordinal()] = 395;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_AXE.ordinal()] = 285;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_BARDING.ordinal()] = 417;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_BLOCK.ordinal()] = 42;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_BOOTS.ordinal()] = 316;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_CHESTPLATE.ordinal()] = 314;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_HELMET.ordinal()] = 313;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_HOE.ordinal()] = 293;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_INGOT.ordinal()] = 265;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_LEGGINGS.ordinal()] = 315;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_NUGGET.ordinal()] = 370;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_ORE.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_PICKAXE.ordinal()] = 284;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_PLATE.ordinal()] = 148;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_RECORD.ordinal()] = 452;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_SPADE.ordinal()] = 283;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GOLD_SWORD.ordinal()] = 282;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRASS.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRASS_PATH.ordinal()] = 209;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRAVEL.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRAY_GLAZED_TERRACOTTA.ordinal()] = 243;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRAY_SHULKER_BOX.ordinal()] = 227;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GREEN_GLAZED_TERRACOTTA.ordinal()] = 249;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GREEN_RECORD.ordinal()] = 453;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GREEN_SHULKER_BOX.ordinal()] = 233;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.GRILLED_PORK.ordinal()] = 319;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HARD_CLAY.ordinal()] = 173;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HAY_BLOCK.ordinal()] = 171;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HOPPER.ordinal()] = 155;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HOPPER_MINECART.ordinal()] = 407;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HUGE_MUSHROOM_1.ordinal()] = 100;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.HUGE_MUSHROOM_2.ordinal()] = 101;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ICE.ordinal()] = 80;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.INK_SACK.ordinal()] = 350;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_AXE.ordinal()] = 257;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_BARDING.ordinal()] = 416;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_BLOCK.ordinal()] = 43;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_BOOTS.ordinal()] = 308;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_CHESTPLATE.ordinal()] = 306;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_DOOR.ordinal()] = 329;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_DOOR_BLOCK.ordinal()] = 72;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_FENCE.ordinal()] = 102;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_HELMET.ordinal()] = 305;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_HOE.ordinal()] = 291;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_INGOT.ordinal()] = 264;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_LEGGINGS.ordinal()] = 307;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_NUGGET.ordinal()] = 450;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_ORE.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_PICKAXE.ordinal()] = 256;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_PLATE.ordinal()] = 149;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_SPADE.ordinal()] = 255;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_SWORD.ordinal()] = 266;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.IRON_TRAPDOOR.ordinal()] = 168;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ITEM_FRAME.ordinal()] = 388;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JACK_O_LANTERN.ordinal()] = 92;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUKEBOX.ordinal()] = 85;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_DOOR.ordinal()] = 196;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_DOOR_ITEM.ordinal()] = 428;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_FENCE.ordinal()] = 191;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_FENCE_GATE.ordinal()] = 186;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.JUNGLE_WOOD_STAIRS.ordinal()] = 137;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.KNOWLEDGE_BOOK.ordinal()] = 451;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LADDER.ordinal()] = 66;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LAPIS_BLOCK.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LAPIS_ORE.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LAVA.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LAVA_BUCKET.ordinal()] = 326;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEASH.ordinal()] = 419;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER.ordinal()] = 333;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER_BOOTS.ordinal()] = 300;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER_CHESTPLATE.ordinal()] = 298;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER_HELMET.ordinal()] = 297;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEATHER_LEGGINGS.ordinal()] = 299;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEAVES.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEAVES_2.ordinal()] = 162;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LEVER.ordinal()] = 70;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LIGHT_BLUE_GLAZED_TERRACOTTA.ordinal()] = 239;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LIGHT_BLUE_SHULKER_BOX.ordinal()] = 223;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LIME_GLAZED_TERRACOTTA.ordinal()] = 241;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LIME_SHULKER_BOX.ordinal()] = 225;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LINGERING_POTION.ordinal()] = 440;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LOG.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LOG_2.ordinal()] = 163;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.LONG_GRASS.ordinal()] = 32;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MAGENTA_GLAZED_TERRACOTTA.ordinal()] = 238;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MAGENTA_SHULKER_BOX.ordinal()] = 222;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MAGMA.ordinal()] = 214;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MAGMA_CREAM.ordinal()] = 377;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MAP.ordinal()] = 357;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MELON.ordinal()] = 359;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MELON_BLOCK.ordinal()] = 104;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MELON_SEEDS.ordinal()] = 361;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MELON_STEM.ordinal()] = 106;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MILK_BUCKET.ordinal()] = 334;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MINECART.ordinal()] = 327;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MOB_SPAWNER.ordinal()] = 53;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MONSTER_EGG.ordinal()] = 382;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MONSTER_EGGS.ordinal()] = 98;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MOSSY_COBBLESTONE.ordinal()] = 49;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MUSHROOM_SOUP.ordinal()] = 281;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MUTTON.ordinal()] = 422;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.MYCEL.ordinal()] = 111;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NAME_TAG.ordinal()] = 420;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHERRACK.ordinal()] = 88;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_BRICK.ordinal()] = 113;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_BRICK_ITEM.ordinal()] = 404;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_BRICK_STAIRS.ordinal()] = 115;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_FENCE.ordinal()] = 114;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_STALK.ordinal()] = 371;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_STAR.ordinal()] = 398;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_WARTS.ordinal()] = 116;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NETHER_WART_BLOCK.ordinal()] = 215;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.NOTE_BLOCK.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.OBSERVER.ordinal()] = 219;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.OBSIDIAN.ordinal()] = 50;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ORANGE_GLAZED_TERRACOTTA.ordinal()] = 237;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ORANGE_SHULKER_BOX.ordinal()] = 221;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PACKED_ICE.ordinal()] = 175;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PAINTING.ordinal()] = 320;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PAPER.ordinal()] = 338;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PINK_GLAZED_TERRACOTTA.ordinal()] = 242;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PINK_SHULKER_BOX.ordinal()] = 226;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PISTON_BASE.ordinal()] = 34;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PISTON_EXTENSION.ordinal()] = 35;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PISTON_MOVING_PIECE.ordinal()] = 37;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PISTON_STICKY_BASE.ordinal()] = 30;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POISONOUS_POTATO.ordinal()] = 393;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PORK.ordinal()] = 318;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PORTAL.ordinal()] = 91;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POTATO.ordinal()] = 143;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POTATO_ITEM.ordinal()] = 391;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POTION.ordinal()] = 372;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POWERED_MINECART.ordinal()] = 342;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.POWERED_RAIL.ordinal()] = 28;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PRISMARINE.ordinal()] = 169;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PRISMARINE_CRYSTALS.ordinal()] = 409;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PRISMARINE_SHARD.ordinal()] = 408;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PUMPKIN.ordinal()] = 87;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PUMPKIN_PIE.ordinal()] = 399;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PUMPKIN_SEEDS.ordinal()] = 360;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PUMPKIN_STEM.ordinal()] = 105;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPLE_GLAZED_TERRACOTTA.ordinal()] = 246;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPLE_SHULKER_BOX.ordinal()] = 230;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_BLOCK.ordinal()] = 202;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_DOUBLE_SLAB.ordinal()] = 205;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_PILLAR.ordinal()] = 203;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_SLAB.ordinal()] = 206;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.PURPUR_STAIRS.ordinal()] = 204;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.QUARTZ.ordinal()] = 405;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.QUARTZ_BLOCK.ordinal()] = 156;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.QUARTZ_ORE.ordinal()] = 154;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.QUARTZ_STAIRS.ordinal()] = 157;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RABBIT.ordinal()] = 410;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RABBIT_FOOT.ordinal()] = 413;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RABBIT_HIDE.ordinal()] = 414;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RABBIT_STEW.ordinal()] = 412;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RAILS.ordinal()] = 67;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RAW_BEEF.ordinal()] = 362;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RAW_CHICKEN.ordinal()] = 364;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RAW_FISH.ordinal()] = 348;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_10.ordinal()] = 461;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_11.ordinal()] = 462;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_12.ordinal()] = 463;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_3.ordinal()] = 454;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_4.ordinal()] = 455;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_5.ordinal()] = 456;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_6.ordinal()] = 457;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_7.ordinal()] = 458;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_8.ordinal()] = 459;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RECORD_9.ordinal()] = 460;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE.ordinal()] = 330;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_BLOCK.ordinal()] = 153;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_COMPARATOR.ordinal()] = 403;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_COMPARATOR_OFF.ordinal()] = 150;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_COMPARATOR_ON.ordinal()] = 151;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_LAMP_OFF.ordinal()] = 124;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_LAMP_ON.ordinal()] = 125;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_ORE.ordinal()] = 74;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_TORCH_OFF.ordinal()] = 76;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_TORCH_ON.ordinal()] = 77;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.REDSTONE_WIRE.ordinal()] = 56;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_GLAZED_TERRACOTTA.ordinal()] = 250;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_MUSHROOM.ordinal()] = 41;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_NETHER_BRICK.ordinal()] = 216;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_ROSE.ordinal()] = 39;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_SANDSTONE.ordinal()] = 180;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_SANDSTONE_STAIRS.ordinal()] = 181;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.RED_SHULKER_BOX.ordinal()] = 234;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.ROTTEN_FLESH.ordinal()] = 366;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SADDLE.ordinal()] = 328;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SAND.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SANDSTONE.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SANDSTONE_STAIRS.ordinal()] = 129;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SAPLING.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SEA_LANTERN.ordinal()] = 170;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SEEDS.ordinal()] = 294;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SHEARS.ordinal()] = 358;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SHIELD.ordinal()] = 441;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SHULKER_SHELL.ordinal()] = 449;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SIGN.ordinal()] = 322;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SIGN_POST.ordinal()] = 64;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SILVER_GLAZED_TERRACOTTA.ordinal()] = 244;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SILVER_SHULKER_BOX.ordinal()] = 228;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SKULL.ordinal()] = 145;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SKULL_ITEM.ordinal()] = 396;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SLIME_BALL.ordinal()] = 340;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SLIME_BLOCK.ordinal()] = 166;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SMOOTH_BRICK.ordinal()] = 99;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SMOOTH_STAIRS.ordinal()] = 110;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SNOW.ordinal()] = 79;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SNOW_BALL.ordinal()] = 331;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SNOW_BLOCK.ordinal()] = 81;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SOIL.ordinal()] = 61;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SOUL_SAND.ordinal()] = 89;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPECKLED_MELON.ordinal()] = 381;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPECTRAL_ARROW.ordinal()] = 438;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPIDER_EYE.ordinal()] = 374;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPLASH_POTION.ordinal()] = 437;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPONGE.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_DOOR.ordinal()] = 194;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_DOOR_ITEM.ordinal()] = 426;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_FENCE.ordinal()] = 189;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_FENCE_GATE.ordinal()] = 184;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SPRUCE_WOOD_STAIRS.ordinal()] = 135;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STAINED_CLAY.ordinal()] = 160;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STAINED_GLASS.ordinal()] = 96;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STAINED_GLASS_PANE.ordinal()] = 161;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STANDING_BANNER.ordinal()] = 177;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STATIONARY_LAVA.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STATIONARY_WATER.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STEP.ordinal()] = 45;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STICK.ordinal()] = 279;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_AXE.ordinal()] = 274;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_BUTTON.ordinal()] = 78;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_HOE.ordinal()] = 290;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_PICKAXE.ordinal()] = 273;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_PLATE.ordinal()] = 71;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_SLAB2.ordinal()] = 183;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_SPADE.ordinal()] = 272;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STONE_SWORD.ordinal()] = 271;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STORAGE_MINECART.ordinal()] = 341;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STRING.ordinal()] = 286;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STRUCTURE_BLOCK.ordinal()] = 254;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.STRUCTURE_VOID.ordinal()] = 218;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SUGAR.ordinal()] = 352;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SUGAR_CANE.ordinal()] = 337;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SUGAR_CANE_BLOCK.ordinal()] = 84;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.SULPHUR.ordinal()] = 288;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.THIN_GLASS.ordinal()] = 103;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TIPPED_ARROW.ordinal()] = 439;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TNT.ordinal()] = 47;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TORCH.ordinal()] = 51;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TOTEM.ordinal()] = 448;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TRAPPED_CHEST.ordinal()] = 147;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TRAP_DOOR.ordinal()] = 97;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TRIPWIRE.ordinal()] = 133;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.TRIPWIRE_HOOK.ordinal()] = 132;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.VINE.ordinal()] = 107;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WALL_BANNER.ordinal()] = 178;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WALL_SIGN.ordinal()] = 69;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WATCH.ordinal()] = 346;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WATER.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WATER_BUCKET.ordinal()] = 325;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WATER_LILY.ordinal()] = 112;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WEB.ordinal()] = 31;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WHEAT.ordinal()] = 295;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WHITE_GLAZED_TERRACOTTA.ordinal()] = 236;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WHITE_SHULKER_BOX.ordinal()] = 220;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOODEN_DOOR.ordinal()] = 65;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_AXE.ordinal()] = 270;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_BUTTON.ordinal()] = 144;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_DOOR.ordinal()] = 323;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_DOUBLE_STEP.ordinal()] = 126;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_HOE.ordinal()] = 289;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_PICKAXE.ordinal()] = 269;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_PLATE.ordinal()] = 73;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_SPADE.ordinal()] = 268;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_STAIRS.ordinal()] = 54;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_STEP.ordinal()] = 127;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOD_SWORD.ordinal()] = 267;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WOOL.ordinal()] = 36;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WORKBENCH.ordinal()] = 59;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.WRITTEN_BOOK.ordinal()] = 386;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.YELLOW_FLOWER.ordinal()] = 38;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.YELLOW_GLAZED_TERRACOTTA.ordinal()] = 240;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Material.YELLOW_SHULKER_BOX.ordinal()] = 224;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$org$bukkit$Material = arrn;
        return $SWITCH_TABLE$org$bukkit$Material;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$attributes$ItemStat() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$attributes$ItemStat;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[ItemStat.values().length];
        try {
            arrn[ItemStat.ACCURACY_RATE.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.AOE_DAMAGE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.ATTACK_SPEED.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.BLEED_RATE.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.BLOCK_DAMAGE.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.BLOCK_RATE.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.BURN_RATE.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.CRITICAL_DAMAGE.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.CRITICAL_RATE.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.DIRECT_DAMAGE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.DISARM_RATE.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.DODGE_RATE.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.DURABILITY.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.DURABILITY_UNBREAK.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.LOOT_RATE.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.MAX_HEALTH.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.MOVEMENT_SPEED.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.PENETRATION.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.PVE_DAMAGE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.PVE_DEFENSE.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.PVP_DAMAGE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.PVP_DEFENSE.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.RANGE.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[ItemStat.VAMPIRISM.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$attributes$ItemStat = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$attributes$ItemStat;
    }
}


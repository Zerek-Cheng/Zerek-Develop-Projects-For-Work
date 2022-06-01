/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.apache.commons.lang.WordUtils
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Biome
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package su.nightexpress.divineitems.utils;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.api.DivineItemsAPI;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.modules.arrows.ArrowManager;
import su.nightexpress.divineitems.types.ArmorType;
import su.nightexpress.divineitems.types.DamageType;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ItemUtils {
    private static DivineItems plugin = DivineItems.instance;
    private static Random r = new Random();
    private static ScriptEngineManager mgr = new ScriptEngineManager();
    private static ScriptEngine engine = mgr.getEngineByName("JavaScript");
    private static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$entity$EntityType;

    public static double dmgReducer(double d, double d2, double d3) {
        if (d < d3) {
            double d4 = 1.0 - plugin.getCM().getCFG().getDamageCDReduce();
            d2 *= 1.0 - (1.0 - d / d3);
            d2 *= d4;
        } else {
            d2 += d - d3;
        }
        return d2;
    }

    public static String getSlotByItemType(ItemStack itemStack) {
        String string = itemStack.getType().name();
        if (string.contains("HELMET") || string.contains("SKULL_ITEM")) {
            return "head";
        }
        if (string.contains("CHESTPLATE") || string.contains("ELYTRA")) {
            return "chest";
        }
        if (string.contains("LEGGINGS")) {
            return "legs";
        }
        if (string.contains("BOOTS")) {
            return "feet";
        }
        if (string.contains("SHIELD")) {
            return "offhand";
        }
        return "mainhand";
    }

    public static String[] getAllNBTSlots(ItemStack itemStack) {
        if (ItemUtils.isArmor(itemStack)) {
            return new String[]{ItemUtils.getSlotByItemType(itemStack)};
        }
        return new String[]{"head", "chest", "legs", "feet", "offhand", "mainhand"};
    }

    public static double calcDamageByFormula(LivingEntity livingEntity, LivingEntity livingEntity2, double d, ItemStack itemStack) {
        Object object3;
        double d2 = 0;
        double d22;
        String object;
        double d3;
        String var26_37 = null;
        String var26_34 = null;
        double d4;
        if (!plugin.getCM().getCFG().allowAttributesToMobs() && !(livingEntity2 instanceof Player)) {
            return d;
        }
        HashMap<Object, Double> hashMap = new HashMap<Object, Double>();
        if (livingEntity.hasMetadata("DIVINE_ARROW_ID")) {
            object3 = ((MetadataValue) livingEntity.getMetadata("DIVINE_ARROW_ID").get(0)).asString();
            ArrowManager.DivineArrow divineArrow = plugin.getMM().getArrowManager().getArrowById((String) object3);
            for (ArrowManager.ArrowAttribute arrowAttribute : divineArrow.getAttributes().values()) {
                d4 = arrowAttribute.getValue();
                if (arrowAttribute.getAction() == ArrowManager.AttributeAction.MINUS) {
                    d4 = -d4;
                }
                hashMap.put((Object) arrowAttribute.getAttribute(), d4);
            }
        }
        ItemStat[] arritemStat = ItemStat.values();
        int n = arritemStat.length;
        int n2 = 0;
        while (n2 < n) {
            object3 = arritemStat[n2];
            if (!hashMap.containsKey(object3)) {
                hashMap.put(object3, 0.0);
            }
            ++n2;
        }
        double d5 = 1.0;
        if ((double) r.nextInt(100) <= EntityAPI.getItemStat(livingEntity2, ItemStat.CRITICAL_RATE) + (Double) hashMap.get((Object) ItemStat.CRITICAL_RATE)) {
            d5 = EntityAPI.getItemStat(livingEntity2, ItemStat.CRITICAL_DAMAGE) + (Double) hashMap.get((Object) ItemStat.CRITICAL_DAMAGE);
            if (d5 == 0.0) {
                d5 = 1.0;
            }
            if (plugin.getMM().getCombatLogManager().isActive()) {
                plugin.getMM().getCombatLogManager().setCritMeta(livingEntity);
            }
        }
        if (d == 0.0 && itemStack != null && (d = ItemAPI.getItemTotalDamage(itemStack)) == 0.0) {
            d = ItemAPI.getDefaultDamage(itemStack);
        }
        d4 = d22 = ItemUtils.dmgReducer(d, ItemAPI.getItemTotalDamage(itemStack), ItemAPI.getDefaultDamage(itemStack));
        ItemStat itemStat = ItemStat.PVE_DEFENSE;
        ItemStat itemStat2 = ItemStat.PVE_DAMAGE;
        if (livingEntity instanceof Player && livingEntity2 instanceof Player) {
            itemStat = ItemStat.PVP_DEFENSE;
            itemStat2 = ItemStat.PVP_DAMAGE;
        }
        double d6 = ItemAPI.getAttribute(itemStack, ItemStat.DIRECT_DAMAGE) / 100.0;
        double d7 = 0.0;
        double d8 = EntityAPI.getItemStat(livingEntity2, ItemStat.PENETRATION) + (Double) hashMap.get((Object) ItemStat.PENETRATION);
        double d9 = EntityAPI.getItemStat(livingEntity, itemStat) + (Double) hashMap.get((Object) itemStat);
        double d10 = EntityAPI.getItemStat(livingEntity2, itemStat2) + (Double) hashMap.get((Object) itemStat2);
        HashMap<DamageType, Double> hashMap2 = ItemAPI.getDamageTypes(livingEntity2);
        HashMap<ArmorType, Double> hashMap3 = ItemAPI.getDefenseTypes(livingEntity);
        if (hashMap2.isEmpty()) {
            for (DamageType damageType : plugin.getCFG().getDamageTypes().values()) {
                if (!damageType.isDefault()) continue;
                String string = livingEntity2.getLocation().getBlock().getBiome().name();
                hashMap2.put(damageType, d22 * damageType.getDamageModifierByBiome(string));
                d4 = d22 * damageType.getDamageModifierByBiome(string);
                break;
            }
        }
        if (hashMap3.isEmpty()) {
            block7:
            for (DamageType damageType : hashMap2.keySet()) {
                if (!damageType.isDefault()) continue;
                for (ArmorType armorType : plugin.getCFG().getArmorTypes().values()) {
                    if (!armorType.getBlockDamageTypes().contains(damageType.getId())) continue;
                    d3 = ItemAPI.getDefaultDefense(livingEntity);
                    hashMap3.put(armorType, d3);
                    break block7;
                }
            }
        }
        String string = "";
        for (DamageType damageType : hashMap2.keySet()) {
            object = "";
            d3 = hashMap2.get(damageType);
            if (!damageType.isDefault() || d3 != d4) {
                d3 = ItemUtils.dmgReducer(d, d3, ItemAPI.getDefaultDamage(itemStack));
            }
            double d11 = d3 * d6;
            d3 = Math.max(0.0, d3 - d11);
            d7 += d11;
            for (ArmorType armorType : hashMap3.keySet()) {
                double d12 = 0.0;
                if (armorType.getBlockDamageTypes().contains(damageType.getId())) {
                    d12 = hashMap3.get(armorType);
                }
                double d13 = Math.min(20.0, EntityAPI.getEnchantedDefense((Entity) livingEntity2, livingEntity));
                String string2 = armorType.getFormula().replace("%crit%", String.valueOf(d5)).replace("%def%", String.valueOf(d12)).replace("%dmg%", String.valueOf(d3 *= 1.0 - d13 / 25.0)).replace("%penetrate%", String.valueOf(d8));
                object = String.valueOf(object) + "(" + d3 + " - " + string2 + ") + ";
            }
            if (object.isEmpty()) {
                object = "0";
            }
            if (object.length() > 3) {
                object = object.substring(0, object.length() - 2).trim();
            }
            object = String.valueOf(d3) + " - (" + (String) object + ")";
            double d14 = 0.0;
            try {
                d14 = Math.max(0.0, (Double) engine.eval((String) object)) * d5;
            } catch (ScriptException scriptException) {
                // empty catch block
            }
            String string3 = String.valueOf(var26_34) + "(" + d14 + ") + ";
            if (plugin.getMM().getCombatLogManager().isActive()) {
                plugin.getMM().getCombatLogManager().setDTMeta(livingEntity, damageType.getId(), d14 + d11);
            }
            for (String string4 : new ArrayList<String>(damageType.getActions())) {
                if (!string4.contains("[DAMAGE]")) continue;
                damageType.getActions().remove(string4);
            }
            DivineItemsAPI.executeActions((Entity) livingEntity2, damageType.getActions(), itemStack);
        }
        if (var26_34.length() > 3) {
            String string5 = var26_34.substring(0, var26_34.length() - 2).trim();
        }
        double d15 = 0.0;
        if (Utils.getRandDouble(0.0, 100.0) <= EntityAPI.getItemStat(livingEntity, ItemStat.BLOCK_RATE) + (Double) hashMap.get((Object) ItemStat.BLOCK_RATE)) {
            d2 = EntityAPI.getItemStat(livingEntity, ItemStat.BLOCK_DAMAGE) + (Double) hashMap.get((Object) ItemStat.BLOCK_DAMAGE);
            if (plugin.getMM().getCombatLogManager().isActive()) {
                plugin.getMM().getCombatLogManager().setBlockMeta(livingEntity, d2);
            }
        }
        object = plugin.getCM().getCFG().getFormulaOther().replace("%pvpe_dmg%", String.valueOf(d10)).replace("%pvpe_def%", String.valueOf(d9)).replace("%crit%", String.valueOf(d5)).replace("%block%", String.valueOf(d2));
        String string6 = plugin.getCM().getCFG().getFormulaDamage().replace("%dmg%", (CharSequence) var26_37).replace("%other%", (CharSequence) object).replace("%crit%", String.valueOf(d5)).replace("%block%", String.valueOf(d2));
        double d16 = 1.0;
        try {
            d16 = d7 + Math.max(0.0, (Double) engine.eval(string6));
        } catch (ScriptException scriptException) {
            // empty catch block
        }
        livingEntity.removeMetadata("DIVINE_ARROW_ID", (Plugin) plugin);
        double d17 = EntityAPI.getItemStat(livingEntity2, ItemStat.BLEED_RATE);
        if (d17 > 0.0 && Utils.getRandDouble(0.0, 100.0) <= d17) {
            plugin.getTM().addBleedEffect(livingEntity, d16);
        }
        return d16;
    }

    public static void setProjectileData(Projectile projectile, LivingEntity livingEntity, ItemStack itemStack) {
        projectile.setMetadata("DIItem", (MetadataValue) new FixedMetadataValue((Plugin) plugin, (Object) itemStack));
        projectile.setMetadata("dont_pick_me", (MetadataValue) new FixedMetadataValue((Plugin) plugin, (Object) "yes"));
    }

    public static void setEntityData(Entity entity, LivingEntity livingEntity, ItemStack itemStack) {
        entity.setMetadata("DIItem", (MetadataValue) new FixedMetadataValue((Plugin) plugin, (Object) itemStack));
        entity.setMetadata("LauncherZ", (MetadataValue) new FixedMetadataValue((Plugin) plugin,  livingEntity.getCustomName()));
    }

    public static boolean isValidItemType(String string, ItemStack itemStack) {
        String string2 = itemStack.getType().name();
        if (string.equalsIgnoreCase("*") || string.equalsIgnoreCase("ALL")) {
            return true;
        }
        if (string2.contains(string)) {
            return true;
        }
        if (string2.equalsIgnoreCase(string)) {
            return true;
        }
        if (string.equalsIgnoreCase("WEAPON") && ItemUtils.isWeapon(itemStack)) {
            return true;
        }
        if (string.equalsIgnoreCase("TOOL") && plugin.getCM().getCFG().getTools().contains((Object) itemStack.getType())) {
            return true;
        }
        if (string.equalsIgnoreCase("ARMOR") && ItemUtils.isArmor(itemStack)) {
            return true;
        }
        if (string.startsWith("*") && string2.endsWith(string.replace("*", "")) && (plugin.getCM().getCFG().getWeapons().contains((Object) itemStack.getType()) || plugin.getCM().getCFG().getArmors().contains((Object) itemStack.getType()) || plugin.getCM().getCFG().getTools().contains((Object) itemStack.getType()))) {
            return true;
        }
        if (string.endsWith("*") && string2.startsWith(string.replace("*", "")) && (plugin.getCM().getCFG().getWeapons().contains((Object) itemStack.getType()) || plugin.getCM().getCFG().getArmors().contains((Object) itemStack.getType()) || plugin.getCM().getCFG().getTools().contains((Object) itemStack.getType()))) {
            return true;
        }
        return false;
    }

    public static String getValidSkullName(Entity entity) {
        EntityType entityType = entity.getType();
        switch (ItemUtils.$SWITCH_TABLE$org$bukkit$entity$EntityType()[entityType.ordinal()]) {
            case 5: {
                return "MHF_WSkeleton";
            }
            case 58: {
                return "MHF_LavaSlime";
            }
            case 4: {
                return "MHF_EGuardian";
            }
        }
        String string = entityType.name().toLowerCase().replace("_", " ");
        return "MHF_" + WordUtils.capitalizeFully((String) string.replace(" ", ""));
    }

    public static boolean isWeapon(ItemStack itemStack) {
        if (!plugin.getCM().getCFG().getWeapons().contains((Object) itemStack.getType()) && !plugin.getCM().getCFG().getTools().contains((Object) itemStack.getType())) {
            return false;
        }
        return true;
    }

    public static boolean isArmor(ItemStack itemStack) {
        return plugin.getCM().getCFG().getArmors().contains((Object) itemStack.getType());
    }

    public static double calc(String string) {
        double d = 0.0;
        try {
            d = Math.max(0.0, Double.valueOf(engine.eval(string).toString()));
        } catch (ScriptException scriptException) {
            // empty catch block
        }
        return d;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$bukkit$entity$EntityType() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$org$bukkit$entity$EntityType;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[EntityType.values().length];
        try {
            arrn[EntityType.ARROW.ordinal()] = 10;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.BAT.ordinal()] = 61;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.BLAZE.ordinal()] = 57;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.BOAT.ordinal()] = 39;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.CAVE_SPIDER.ordinal()] = 55;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.CHICKEN.ordinal()] = 69;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.COMPLEX_PART.ordinal()] = 89;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.COW.ordinal()] = 68;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.CREEPER.ordinal()] = 46;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.DROPPED_ITEM.ordinal()] = 1;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.EGG.ordinal()] = 7;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.ENDERMAN.ordinal()] = 54;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.ENDER_CRYSTAL.ordinal()] = 83;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.ENDER_DRAGON.ordinal()] = 59;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.ENDER_PEARL.ordinal()] = 14;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.ENDER_SIGNAL.ordinal()] = 15;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.EXPERIENCE_ORB.ordinal()] = 2;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.FALLING_BLOCK.ordinal()] = 21;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.FIREBALL.ordinal()] = 12;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.FIREWORK.ordinal()] = 22;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.FISHING_HOOK.ordinal()] = 85;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.GHAST.ordinal()] = 52;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.GIANT.ordinal()] = 49;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.HORSE.ordinal()] = 76;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.IRON_GOLEM.ordinal()] = 75;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.ITEM_FRAME.ordinal()] = 18;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.LEASH_HITCH.ordinal()] = 8;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.LIGHTNING.ordinal()] = 86;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.MAGMA_CUBE.ordinal()] = 58;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.MINECART.ordinal()] = 40;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.MINECART_CHEST.ordinal()] = 41;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.MINECART_COMMAND.ordinal()] = 38;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.MINECART_FURNACE.ordinal()] = 42;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.MINECART_HOPPER.ordinal()] = 44;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.MINECART_MOB_SPAWNER.ordinal()] = 45;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.MINECART_TNT.ordinal()] = 43;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.MUSHROOM_COW.ordinal()] = 72;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.OCELOT.ordinal()] = 74;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.PAINTING.ordinal()] = 9;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.PIG.ordinal()] = 66;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.PIG_ZOMBIE.ordinal()] = 53;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.PLAYER.ordinal()] = 88;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.PRIMED_TNT.ordinal()] = 20;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SHEEP.ordinal()] = 67;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SILVERFISH.ordinal()] = 56;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SKELETON.ordinal()] = 47;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SLIME.ordinal()] = 51;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SMALL_FIREBALL.ordinal()] = 13;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SNOWBALL.ordinal()] = 11;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SNOWMAN.ordinal()] = 73;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SPIDER.ordinal()] = 48;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SPLASH_POTION.ordinal()] = 16;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.SQUID.ordinal()] = 70;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.THROWN_EXP_BOTTLE.ordinal()] = 17;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.UNKNOWN.ordinal()] = 91;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.VILLAGER.ordinal()] = 82;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.WEATHER.ordinal()] = 87;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.WITCH.ordinal()] = 62;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.WITHER.ordinal()] = 60;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.WITHER_SKULL.ordinal()] = 19;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.WOLF.ordinal()] = 71;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[EntityType.ZOMBIE.ordinal()] = 50;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        $SWITCH_TABLE$org$bukkit$entity$EntityType = arrn;
        return $SWITCH_TABLE$org$bukkit$entity$EntityType;
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  me.clip.placeholderapi.external.EZPlaceholderHook
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 */
package su.nightexpress.divineitems.hooks.placeholders;

import java.util.HashMap;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.types.ArmorType;
import su.nightexpress.divineitems.utils.Utils;

public class PlaceholderAPIHook
extends EZPlaceholderHook {
    public PlaceholderAPIHook(DivineItems divineItems, String string) {
        super((Plugin)divineItems, string);
    }

    public String onPlaceholderRequest(Player player, String string) {
        if (player == null || player.getInventory() == null) {
            return null;
        }
        if (string.startsWith("attribute_")) {
            String string2 = string.replace("attribute_", "");
            try {
                ItemStat itemStat = ItemStat.valueOf(string2.toUpperCase());
                return String.valueOf(Utils.round3(EntityAPI.getItemStat((LivingEntity)player, itemStat)));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return "Invalid Attribute!";
            }
        }
        if (string.startsWith("damage_")) {
            String string3 = string.replace("damage_", "");
            if (player == null || player.getInventory() == null) {
                return "Error.";
            }
            return String.valueOf(Utils.round3(ItemAPI.getFinalDamageByType(string3, (LivingEntity)player)));
        }
        if (string.startsWith("defense_")) {
            String string4 = string.replace("defense_", "");
            ArmorType armorType = DivineItems.instance.getCFG().getArmorTypes().get(string4.toLowerCase());
            if (armorType == null) {
                return "Invalid Defense Type!";
            }
            if (player == null || player.getInventory() == null) {
                return "Error.";
            }
            HashMap<ArmorType, Double> hashMap = ItemAPI.getDefenseTypes((LivingEntity)player);
            if (hashMap.containsKey(armorType)) {
                return String.valueOf(hashMap.get(armorType));
            }
            return "0.0";
        }
        return null;
    }
}


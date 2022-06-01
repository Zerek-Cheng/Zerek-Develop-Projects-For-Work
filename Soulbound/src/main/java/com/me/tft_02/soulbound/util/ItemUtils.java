package com.me.tft_02.soulbound.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.me.tft_02.soulbound.Soulbound;
import com.me.tft_02.soulbound.config.Config;
import com.me.tft_02.soulbound.events.SoulbindItemEvent;

public class ItemUtils {
    public static ItemStack boeItem(final ItemStack itemStack) {
        if (itemStack == null) {
            return itemStack;
        }

        if (isBindOnEquip(itemStack)) {
            return itemStack;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();

        final List<String> itemLore = new ArrayList<String>();
        if (itemMeta.hasLore()) {
            itemLore.addAll(itemMeta.getLore());
        }
        itemLore.add(Misc.EQUIPBIND_TAG);
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack bopItem(final ItemStack itemStack) {
        if (itemStack == null) {
            return itemStack;
        }

        if (isBindOnPickup(itemStack)) {
            return itemStack;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();

        final List<String> itemLore = new ArrayList<String>();
        if (itemMeta.hasLore()) {
            itemLore.addAll(itemMeta.getLore());
        }
        itemLore.add(Misc.PICKUPBIND_TAG);
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack bouItem(final ItemStack itemStack) {
        if (itemStack == null) {
            return itemStack;
        }

        if (isBindOnUse(itemStack)) {
            return itemStack;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();

        final List<String> itemLore = new ArrayList<String>();
        if (itemMeta.hasLore()) {
            itemLore.addAll(itemMeta.getLore());
        }
        itemLore.add(Misc.USEBIND_TAG);
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemType getItemType(final ItemStack itemStack) {
        if (itemStack == null) {
            return ItemType.NORMAL;
        } else if (isSoulbound(itemStack)) {
            return ItemType.SOULBOUND;
        } else if (isBindOnPickup(itemStack)) {
            return ItemType.BIND_ON_PICKUP;
        } else if (isBindOnUse(itemStack)) {
            return ItemType.BIND_ON_USE;
        } else if (isBindOnEquip(itemStack)) {
            return ItemType.BIND_ON_EQUIP;
        } else {
            return ItemType.NORMAL;
        }
    }

    public static boolean isBindedPlayer(final Player player, final ItemStack itemStack) {
        final List<String> itemLore = itemStack.getItemMeta().getLore();
        return itemLore.contains(player.getName()) || itemLore.contains(player.getName().replaceAll("_", " "));
    }

    public static boolean isBindOnEquip(final ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore()) {
            final List<String> itemLore = itemMeta.getLore();
            if (itemLore.contains(Misc.EQUIPBIND_TAG)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBindOnPickup(final ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore()) {
            final List<String> itemLore = itemMeta.getLore();
            if (itemLore.contains(Misc.PICKUPBIND_TAG)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBindOnUse(final ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore()) {
            final List<String> itemLore = itemMeta.getLore();
            if (itemLore.contains(Misc.USEBIND_TAG)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if an item is a chainmail armor piece.
     *
     * @param is
     *            Item to check
     *
     * @return true if the item is chainmail armor, false otherwise
     */
    public static boolean isChainmailArmor(final ItemStack is) {
        switch (is.getType()) {
        case CHAINMAIL_BOOTS:
        case CHAINMAIL_CHESTPLATE:
        case CHAINMAIL_HELMET:
        case CHAINMAIL_LEGGINGS:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks to see if an item is a diamond armor piece.
     *
     * @param is
     *            Item to check
     *
     * @return true if the item is diamond armor, false otherwise
     */
    public static boolean isDiamondArmor(final ItemStack is) {
        switch (is.getType()) {
        case DIAMOND_BOOTS:
        case DIAMOND_CHESTPLATE:
        case DIAMOND_HELMET:
        case DIAMOND_LEGGINGS:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks to see if an item is an equipable item.
     *
     * @param is
     *            Item to check
     *
     * @return true if the item is equipable, false otherwise
     */
    public static boolean isEquipable(final ItemStack is) {
        return isMinecraftArmor(is) || is.getType() == Material.SKULL_ITEM || is.getType() == Material.JACK_O_LANTERN;
    }

    /**
     * Checks to see if an item is a gold armor piece.
     *
     * @param is
     *            Item to check
     *
     * @return true if the item is gold armor, false otherwise
     */
    public static boolean isGoldArmor(final ItemStack is) {
        switch (is.getType()) {
        case GOLD_BOOTS:
        case GOLD_CHESTPLATE:
        case GOLD_HELMET:
        case GOLD_LEGGINGS:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks to see if an item is an iron armor piece.
     *
     * @param is
     *            Item to check
     *
     * @return true if the item is iron armor, false otherwise
     */
    public static boolean isIronArmor(final ItemStack is) {
        switch (is.getType()) {
        case IRON_BOOTS:
        case IRON_CHESTPLATE:
        case IRON_HELMET:
        case IRON_LEGGINGS:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks to see if an item is a leather armor piece.
     *
     * @param is
     *            Item to check
     *
     * @return true if the item is leather armor, false otherwise
     */
    public static boolean isLeatherArmor(final ItemStack is) {
        switch (is.getType()) {
        case LEATHER_BOOTS:
        case LEATHER_CHESTPLATE:
        case LEATHER_HELMET:
        case LEATHER_LEGGINGS:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks to see if an item is a wearable armor piece.
     *
     * @param is
     *            Item to check
     *
     * @return true if the item is armor, false otherwise
     */
    public static boolean isMinecraftArmor(final ItemStack is) {
        return isLeatherArmor(is) || isGoldArmor(is) || isIronArmor(is) || isDiamondArmor(is) || isChainmailArmor(is);
    }

    public static boolean isNormalItem(final ItemStack itemStack) {
        return !itemStack.hasItemMeta() && !itemStack.getItemMeta().hasLore() || ItemUtils.getItemType(itemStack) == ItemType.NORMAL;
    }

    public static boolean isSoulbound(final ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore()) {
            final List<String> itemLore = itemMeta.getLore();
            for (final String lore : itemLore) {
                if (lore.contains(Misc.SOULBOUND_TAG)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ItemStack soulbindItem(final Player player, ItemStack itemStack) {
        if (itemStack == null) {
            return itemStack;
        }

        if (isSoulbound(itemStack)) {
            return itemStack;
        }

        final SoulbindItemEvent soulbindItemEvent = new SoulbindItemEvent(player, itemStack);
        Soulbound.p.getServer().getPluginManager().callEvent(soulbindItemEvent);
        itemStack = soulbindItemEvent.getItemStack();

        if (soulbindItemEvent.isCancelled()) {
            return itemStack;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        final List<String> itemLore = new ArrayList<String>();

        if (itemMeta.hasLore()) {
            final List<String> oldLore = itemMeta.getLore();
            oldLore.remove(Misc.EQUIPBIND_TAG);
            oldLore.remove(Misc.PICKUPBIND_TAG);
            oldLore.remove(Misc.USEBIND_TAG);
            itemLore.addAll(oldLore);
        }

        itemLore.add(Misc.SOULBOUND_TAG);

        if (Config.getShowNameInLore()) {
            itemLore.add(player.getName());
        }

        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack unbindItem(final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore() && isSoulbound(itemStack)) {
            final List<String> oldLore = itemMeta.getLore();
            final int loreSize = oldLore.size();

            if (loreSize <= 2) {
                itemMeta.setLore(null);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            }

            final List<String> itemLore = new ArrayList<String>();
            itemLore.addAll(oldLore);
            final int index = StringUtils.getIndexOfSoulbound(itemLore);

            if (index == -1) {
                return null;
            }

            itemLore.remove(index);
            if (index < itemLore.size()) {
                itemLore.remove(index);
            }

            itemMeta.setLore(itemLore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public enum ItemType {
        BIND_ON_EQUIP,
        BIND_ON_PICKUP,
        BIND_ON_USE,
        NORMAL,
        SOULBOUND;
    }
}

package cc.baka9.slashbladeshop.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
    public static NBTTagCompound getItemStackNbtDate(org.bukkit.inventory.ItemStack item) {
        net.minecraft.server.v1_7_R4.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        return nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
    }

    public static org.bukkit.inventory.ItemStack setItemStackNbtDate(org.bukkit.inventory.ItemStack item, NBTTagCompound compound) {
        net.minecraft.server.v1_7_R4.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        nmsItem.setTag(compound);
        org.bukkit.inventory.ItemStack hasNBTItem = CraftItemStack.asBukkitCopy(nmsItem);
        return hasNBTItem;
    }

    public static boolean ItemHasNbtTagKey(org.bukkit.inventory.ItemStack item, String key) {
        net.minecraft.server.v1_7_R4.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem.hasTag()) {
            NBTTagCompound compound = nmsItem.getTag();
            return compound.hasKey(key);
        }
        return false;
    }

    public static org.bukkit.inventory.ItemStack addItemStackNbtDate(org.bukkit.inventory.ItemStack item, String key, int value) {
        net.minecraft.server.v1_7_R4.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = getItemStackNbtDate(item);
        if (ItemHasNbtTagKey(item, key)) {
            value += compound.getInt(key);
            compound.remove(key);
        }
        compound.setInt(key, value);
        nmsItem.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static void addItemLore(org.bukkit.inventory.ItemStack item, String... lines) {
        ItemMeta meta = item.getItemMeta();
        List<String> lores = meta.hasLore() ? meta.getLore() : new ArrayList();
        String[] arrayOfString;
        int j = (arrayOfString = lines).length;
        for (int i = 0; i < j; i++) {
            String line = arrayOfString[i];
            lores.add(line);
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
    }

    public static void setItemLore(org.bukkit.inventory.ItemStack item, String... lines) {
        ItemMeta meta = item.getItemMeta();
        List<String> lores = new ArrayList();
        String[] arrayOfString;
        int j = (arrayOfString = lines).length;
        for (int i = 0; i < j; i++) {
            String line = arrayOfString[i];
            lores.add(line);
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
    }

    public static void setItemLore(org.bukkit.inventory.ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static void removeItemLore(org.bukkit.inventory.ItemStack item, String... lines) {
        ItemMeta meta = item.getItemMeta();
        List<String> lores = meta.hasLore() ? meta.getLore() : new ArrayList();
        String[] arrayOfString;
        int j = (arrayOfString = lines).length;
        for (int i = 0; i < j; i++) {
            String line = arrayOfString[i];
            lores.remove(line);
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
    }

    public static List<String> getLore(org.bukkit.inventory.ItemStack item) {
        return item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList();
    }

    public static void setItemDisPlayName(org.bukkit.inventory.ItemStack item, String disPlayName) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(disPlayName);
        item.setItemMeta(meta);
    }

    public static void dropItem(Location loc, org.bukkit.inventory.ItemStack item) {
        if (item == null) {
            return;
        }
        loc.getWorld().dropItem(loc, item);
    }

    public static int getInventoryItemCount(Inventory inv, org.bukkit.inventory.ItemStack targetItem) {
        int invTargetItemCount = 0;
        org.bukkit.inventory.ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = inv.getContents()).length;
        for (int i = 0; i < j; i++) {
            org.bukkit.inventory.ItemStack invItem = arrayOfItemStack[i];
            if ((invItem != null) && (invItem.getType() != Material.AIR) && (targetItem.isSimilar(invItem))) {
                invTargetItemCount += invItem.getAmount();
            }
        }
        return invTargetItemCount;
    }

    public static int getPlayerItemCount(Player player, org.bukkit.inventory.ItemStack targetItem) {
        return getInventoryItemCount(player.getInventory(), targetItem);
    }

    public static boolean consumeInventoryItem(Inventory inv, org.bukkit.inventory.ItemStack targetItem, int count) {
        if (!inv.containsAtLeast(targetItem, count)) {
            return false;
        }
        for (int i = 0; i < inv.getSize(); i++) {
            if (count == 0) {
                return true;
            }
            org.bukkit.inventory.ItemStack invItem = inv.getItem(i);
            if ((!ItemIsEmpty(invItem)) && (invItem.isSimilar(targetItem))) {
                int surplus = invItem.getAmount() - count;
                count = Math.abs(Math.min(0, surplus));
                invItem.setAmount(Math.max(0, surplus));
                inv.setItem(i, invItem);
            }
        }
        return true;
    }

    public static boolean consumePlayerItem(Player player, org.bukkit.inventory.ItemStack targetItem, int count) {
        boolean result = consumeInventoryItem(player.getInventory(), targetItem, count);
        player.updateInventory();
        return result;
    }

    public static boolean ItemIsEmpty(org.bukkit.inventory.ItemStack item) {
        return (item == null) || (item.getType() == Material.AIR);
    }

    public static boolean maxInventory(Inventory inv) {
        for (int a = 0; a < inv.getSize(); a++) {
            org.bukkit.inventory.ItemStack item = inv.getItem(a);
            if (item == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean maxInventory(Player p) {
        return maxInventory(p.getInventory());
    }

    public static List<org.bukkit.inventory.ItemStack> getInventoryItems(Inventory inv) {
        List<org.bukkit.inventory.ItemStack> itemList = new ArrayList();
        for (int i = 0; i < inv.getSize(); i++) {
            org.bukkit.inventory.ItemStack invItem = inv.getItem(i);
            if (!ItemIsEmpty(invItem)) {
                itemList.add(invItem);
            }
        }
        return itemList;
    }
}

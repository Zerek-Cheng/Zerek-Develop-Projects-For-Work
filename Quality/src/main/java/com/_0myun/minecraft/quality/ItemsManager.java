package com._0myun.minecraft.quality;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    private static void addLores(ItemStack itemstack, int index) {
        String sign = Quality.INSTANCE.getSignStr();
        ItemMeta itemMeta = itemstack.getItemMeta();
        if (!itemMeta.hasLore()) itemMeta.setLore(new ArrayList<>());
        List<String> lore = itemMeta.getLore();
        List<String> lores = new ArrayList<>();

        String type = Quality.INSTANCE.getType(itemstack);
        String attribute = Quality.INSTANCE.getTypeLoreAttribute(type);
        int quality = Quality.INSTANCE.getQuality(itemstack);
        String value = Quality.INSTANCE.getQualityValue(type, quality);

        lores.add(sign + LangManager.get("lang1") + Quality.INSTANCE.getQualityName(quality));
        lores.add(sign + String.format(attribute, String.valueOf(value)));


        lore.addAll(index, lores);
        itemMeta.setLore(lore);
        itemstack.setItemMeta(itemMeta);
    }

    /**
     * 增加lore
     */
    public static void addLores(ItemStack itemstack) {
        ItemMeta itemMeta = itemstack.getItemMeta();
        addLores(itemstack, itemMeta.hasLore() ? itemMeta.getLore().size() : 0);
    }

    /**
     * 更新LORE
     */
    public static void updateLores(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lores = itemMeta.getLore();
        lores = lores == null ? new ArrayList<>() : lores;
        int line = -1;
        for (int i = 0; i < lores.size(); i++) {
            String lore = lores.get(i);
            if (lore.startsWith(Quality.INSTANCE.getSignStr())) {
                line = i;
                break;
            }
        }
        for (int i = lores.size() - 1; i >= 0; i--) {
            String lore = lores.get(i);
            if (lore.startsWith(Quality.INSTANCE.getSignStr())) {
                lores.remove(i);
            }
        }
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        if (line == -1) {
            addLores(itemStack);
        } else {
            addLores(itemStack, line);
        }
    }


    public static boolean isMakeItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return false;
        return itemStack.getItemMeta().getLore().toString().contains(Quality.INSTANCE.getConfig().getString("lore-make"));
        //return ListUtil.contain(itemStack.getItemMeta().getLore(), Main.plugin.getConfig().getString("safeLore"));
    }

    public static boolean isLuckItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return false;
        return itemStack.getItemMeta().getLore().toString().contains(Quality.INSTANCE.getConfig().getString("lore-lucky"));
        //return ListUtil.contain(itemStack.getItemMeta().getLore(), Main.plugin.getConfig().getString("safeLore"));
    }

    public static int searchMakeItemAmount(Player p) {
        if (!p.isOnline()) return 0;
        int amount = 0;
        PlayerInventory inv = p.getInventory();
        for (int i = inv.getSize() - 1; i >= 0; i--) {
            ItemStack item = inv.getItem(i);
            if (isMakeItem(item)) amount += item.getAmount();
        }
        return amount;
    }

    public static boolean takeMakeItem(Player p, int amount) {
        if (searchMakeItemAmount(p) < amount) return false;
        int result = 0;
        PlayerInventory inv = p.getInventory();
        for (int i = inv.getSize() - 1; i >= 0; i--) {
            ItemStack item = inv.getItem(i);
            if (!isMakeItem(item)) continue;
            if (item.getAmount() <= amount) {//扔不够扣除
                amount -= item.getAmount();//仍需要
                result += item.getAmount();//已经扣除
                item.setAmount(0);
                item.setType(Material.AIR);
                inv.setItem(i, new ItemStack(Material.AIR));
            } else {
                item.setAmount(item.getAmount() - amount);
                result += amount;
                amount = 0;
            }

            if (amount <= 0) break;
        }
        p.updateInventory();
        return result <= 0;
    }

    public static boolean takeLuckItem(Player p, int amount) {
        if (searchMakeItemAmount(p) < amount) return false;
        int result = 0;
        PlayerInventory inv = p.getInventory();
        for (int i = inv.getSize() - 1; i >= 0; i--) {
            ItemStack item = inv.getItem(i);
            if (!isLuckItem(item)) continue;
            if (item.getAmount() <= amount) {//扔不够扣除
                amount -= item.getAmount();//仍需要
                result += item.getAmount();//已经扣除
                item.setAmount(0);
                item.setType(Material.AIR);
                inv.setItem(i, new ItemStack(Material.AIR));
            } else {
                item.setAmount(item.getAmount() - amount);
                result += amount;
                amount = 0;
            }

            if (amount <= 0) break;
        }
        p.updateInventory();
        return amount <= 0;
    }
}

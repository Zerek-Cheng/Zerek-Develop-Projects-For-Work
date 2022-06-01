package com._0myun.minecraft.mosaic;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    private static void addLores(ItemStack itemstack, int index) {
        String sign = Mosaic.INSTANCE.getSignStr();
        ItemMeta itemMeta = itemstack.getItemMeta();
        if (!itemMeta.hasLore()) itemMeta.setLore(new ArrayList<>());
        List<String> lore = itemMeta.getLore();
        List<String> lores = new ArrayList<>();


        for (int i = 0; i < Mosaic.INSTANCE.getMax(); i++) {
            if (!Mosaic.INSTANCE.isSlotUse(itemstack, i)) continue;
            String gemLore = Mosaic.INSTANCE.getGemLore(Mosaic.INSTANCE.getSlotGemType(itemstack, i));
            lores.add(sign + String.format(LangManager.get("lang13"), Mosaic.INSTANCE.getSlotGemType(itemstack, i)));
            lores.add(sign + gemLore);
        }

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
            if (lore.startsWith(Mosaic.INSTANCE.getSignStr())) {
                line = i;
                break;
            }
        }
        for (int i = lores.size() - 1; i >= 0; i--) {
            String lore = lores.get(i);
            if (lore.startsWith(Mosaic.INSTANCE.getSignStr())) {
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


    public static boolean isSafeItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return false;
        return Utils.contains(itemStack.getItemMeta().getLore(), Mosaic.INSTANCE.getConfig().getString("safeLore"));
    }

    public static int searchSafeItemAmount(Player p) {
        if (!p.isOnline()) return 0;
        int amount = 0;
        PlayerInventory inv = p.getInventory();
        for (int i = inv.getSize() - 1; i >= 0; i--) {
            ItemStack item = inv.getItem(i);
            if (isSafeItem(item)) amount += item.getAmount();
        }
        return amount;
    }

    public static boolean takeSafeItem(Player p, int amount) {
        if (searchSafeItemAmount(p) < amount) return false;
        int result = 0;
        PlayerInventory inv = p.getInventory();
        for (int i = inv.getSize() - 1; i >= 0; i--) {
            ItemStack item = inv.getItem(i);
            if (!isSafeItem(item)) continue;
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
}

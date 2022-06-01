/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Storage.Data;
import com.pedrojm96.referralsystem.Util;
import com.pedrojm96.referralsystem.itemPlayer;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class menuList {
    public static void MenuCrete(int n, Player player, int n2) {
        String string = Util.rColor(Messages.MenuList());
        Inventory inventory = Bukkit.createInventory(null, (int)n, (String)string);
        List<itemPlayer> list = ReferralSystem.data.getList(player.getUniqueId().toString(), n2);
        for (itemPlayer itemPlayer2 : list) {
            inventory.setItem(itemPlayer2.getPo(), itemPlayer2.create());
        }
        inventory.setItem(52, menuList.createItem(String.valueOf(n2)));
        inventory.setItem(53, menuList.createItem(Messages.MenuList_Next().replaceAll("<page>", String.valueOf(n2 + 1))));
        if (n2 > 1) {
            inventory.setItem(51, menuList.createItem(Messages.MenuList_Back().replaceAll("<page>", String.valueOf(n2 - 1))));
        }
        player.openInventory(inventory);
    }

    public static ItemStack createItem(String string, List<String> list, int n, int n2) {
        ItemStack itemStack = new ItemStack(Material.getMaterial((int)n), 1, (short)n2);
        ItemMeta itemMeta = itemStack.getItemMeta();
        string = Util.rColor(string);
        itemMeta.setDisplayName(string);
        list = Util.rColorList(list);
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItem(String string) {
        ItemStack itemStack = new ItemStack(Material.NETHER_STAR, 1, (short)0);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String string2 = Util.rColor(string);
        itemMeta.setDisplayName(string2);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void open(Player player, int n) {
        menuList.MenuCrete(54, player, n);
        if (ReferralSystem.config.getBoolean("Sounds-Enable")) {
            boolean bl = Bukkit.getBukkitVersion().split("-")[0].contains("1.8");
            boolean bl2 = Bukkit.getBukkitVersion().split("-")[0].contains("1.7");
            if (bl || bl2) {
                player.playSound(player.getLocation(), Sound.valueOf((String)"CHEST_OPEN"), 2.0f, 2.0f);
            } else {
                player.playSound(player.getLocation(), Sound.valueOf((String)"BLOCK_CHEST_OPEN"), 2.0f, 2.0f);
            }
        }
    }
}


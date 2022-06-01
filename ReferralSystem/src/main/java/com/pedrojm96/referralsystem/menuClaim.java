/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.Item;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Util;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class menuClaim {
    private static int slot;
    private static Inventory menu;

    public static void MenuCrete(int n, Player player) {
        slot = menuClaim.getSlot(n);
        String string = Util.rColor(ReferralSystem.claim.getString("settings-name"));
        menu = Bukkit.createInventory(null, (int)slot, (String)string);
        List<Item> list = ReferralSystem.loadItems(player);
        for (Item item : list) {
            menu.setItem(item.getSlot(), item.create(player));
        }
    }

    public static void open(Player player) {
        menuClaim.MenuCrete(ReferralSystem.claim.getInt("settings-rows"), player);
        player.openInventory(menu);
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

    public static int getSlot(int n) {
        if (n <= 0) {
            int n2 = 9;
            return n2;
        }
        if (n > 6) {
            int n3 = 54;
            return n3;
        }
        int n4 = n * 9;
        return n4;
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 */
package xin.tianwc.tprefix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PreInventory {
    public static HashMap<String, Inventory> inventorys = new HashMap();

    public static void PreMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, (int)27, (String)"\u00a76\u79f0\u53f7\u83dc\u5355");
        int i = 0;
        for (String pre : Main.INSTANCE.getConfig().getKeys(false)) {
            if (pre.equalsIgnoreCase("data")) continue;
            Prefix prefix = new Prefix(pre);
            ItemStack item = prefix.getItem();
            if (!player.hasPermission(prefix.getPermission()) && !prefix.getPermission().equals("tpre.default")) continue;
            inventory.setItem(i, item);
            ++i;
        }
        player.openInventory(inventory);
        inventorys.put(player.getName(), inventory);
    }
}


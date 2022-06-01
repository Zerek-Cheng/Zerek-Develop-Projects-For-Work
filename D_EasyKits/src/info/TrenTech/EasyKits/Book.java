/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package info.TrenTech.EasyKits;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Kit.KitUser;
import info.TrenTech.EasyKits.SQL.SQLKits;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Book {
    public static ItemStack getBook(String meta) {
        ItemStack itemStack = null;
        itemStack = new ItemStack(Material.BOOK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(meta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void pageOne(Player player) {
        Inventory kitInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, 54, "\u00a70\u5168\u90e8\u793c\u5305");
        List<Kit> list = SQLKits.getKitList();
        if (list.size() <= 9) {
            kitInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, 9, "\u00a70\u5168\u90e8\u793c\u5305:");
        } else if (list.size() > 9 && list.size() <= 18) {
            kitInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, 18, "\u00a70\u5168\u90e8\u793c\u5305");
        } else if (list.size() > 18 && list.size() <= 27) {
            kitInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, 27, "\u00a70\u5168\u90e8\u793c\u5305");
        } else if (list.size() > 27 && list.size() <= 36) {
            kitInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, 36, "\u00a70\u5168\u90e8\u793c\u5305");
        } else if (list.size() > 36 && list.size() <= 45) {
            kitInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, 45, "\u00a70\u5168\u90e8\u793c\u5305");
        }
        int index = 0;
        boolean warning = false;
        for (Kit kit : list) {
            double price;
            String cooldown;
            ItemStack kitItem = new ItemStack(Material.BOOK);
            ItemMeta itemMeta = kitItem.getItemMeta();
            itemMeta.setDisplayName("\u793c\u5305: " + kit.getName());
            ArrayList<String> lore = new ArrayList<String>();
            if (Utils.getPluginContainer().econSupport && (price = kit.getPrice()) > 0.0 && !player.hasPermission("EasyKits.bypass.price")) {
                double balance = Utils.getPluginContainer().economy.getBalance((OfflinePlayer)player);
                if (balance < price) {
                    lore.add((Object)ChatColor.DARK_RED + "\u4ef7\u683c: $" + price);
                } else {
                    lore.add((Object)ChatColor.GREEN + "\u4ef7\u683c: $" + price);
                }
                itemMeta.setLore(lore);
            }
            KitUser kitUser = new KitUser(player, kit);
            int limit = kit.getLimit();
            if (limit > 0 && !player.hasPermission("EasyKits.bypass.limit")) {
                if (kitUser.getCurrentLimit() == 0 && kit.getLimit() > 0) {
                    lore.add((Object)ChatColor.DARK_RED + "\u9650\u9818: " + limit);
                } else {
                    lore.add((Object)ChatColor.GREEN + "\u9650\u9818: " + limit);
                }
                itemMeta.setLore(lore);
            }
            if (!(cooldown = kit.getCooldown()).equalsIgnoreCase("0") && !player.hasPermission("EasyKits.bypass.cooldown")) {
                if (kitUser.getCooldownTimeRemaining() != null) {
                    lore.add((Object)ChatColor.DARK_RED + "\u51b7\u537b: " + cooldown);
                } else {
                    lore.add((Object)ChatColor.GREEN + "\u51b7\u537b: " + cooldown);
                }
                itemMeta.setLore(lore);
            }
            kitItem.setItemMeta(itemMeta);
            if (index < 54) {
                kitInv.addItem(new ItemStack[]{kitItem});
                ++index;
                continue;
            }
            warning = true;
        }
        player.openInventory(kitInv);
        if (warning) {
            Notifications notify = new Notifications("Kit-Book-Full", null, null, 0.0, null, 0);
            player.sendMessage(notify.getMessage());
        }
    }

    public static void pageTwo(Player player, Kit kit) {
        ItemStack[] inv = kit.getInventory();
        ItemStack[] arm = kit.getArmor();
        Inventory showInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, 45, "\u00a70\u793c\u5305\u5185\u5bb9: " + kit.getName());
        showInv.setContents(inv);
        int index = 36;
        ItemStack[] array = arm;
        int length = array.length;
        int i = 0;
        while (i < length) {
            ItemStack a = array[i];
            showInv.setItem(index, a);
            ++index;
            ++i;
        }
        ItemStack getKit = new ItemStack(Material.NETHER_STAR);
        ItemMeta getKitMeta = getKit.getItemMeta();
        getKitMeta.setDisplayName((Object)ChatColor.GREEN + "\u00a7d\u70b9\u51fb\u9886\u53d6");
        getKit.setItemMeta(getKitMeta);
        ItemStack backButton = new ItemStack(Material.BOOK);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        backButtonMeta.setDisplayName((Object)ChatColor.GREEN + "\u8fd4\u56de");
        backButton.setItemMeta(backButtonMeta);
        showInv.setItem(43, backButton);
        showInv.setItem(44, getKit);
        player.openInventory(showInv);
    }
}


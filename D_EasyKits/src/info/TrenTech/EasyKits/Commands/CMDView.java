/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CMDView {
    public static void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            Notifications notify = new Notifications("Not-Player", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        Player player = (Player)sender;
        if (!sender.hasPermission("EasyKits.cmd.view")) {
            Notifications notify2 = new Notifications("Permission-Denied", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify2.getMessage());
            return;
        }
        if (args.length == 2) {
            String kitName = args[1];
            Kit kit = new Kit(kitName);
            if (!kit.exists()) {
                Notifications notify3 = new Notifications("Kit-Not-Exist", kit.getName(), sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify3.getMessage());
                return;
            }
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
            getKitMeta.setDisplayName("\u00a7d\u70b9\u51fb\u9886\u53d6");
            getKit.setItemMeta(getKitMeta);
            showInv.setItem(44, getKit);
            player.openInventory(showInv);
        } else {
            sender.sendMessage((Object)ChatColor.YELLOW + "/kit view <kitname>");
        }
    }
}


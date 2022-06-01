/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Book;
import info.TrenTech.EasyKits.Utils.Notifications;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMDBook {
    public static void execute(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Notifications notify = new Notifications("Not-Player", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        if (!sender.hasPermission("EasyKits.cmd.book")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        Player player = (Player)sender;
        ItemStack book = Book.getBook("\u00a77[\u00a7a\u5168\u90e8\u793c\u5305\u00a77]");
        player.getInventory().addItem(new ItemStack[]{book});
    }
}


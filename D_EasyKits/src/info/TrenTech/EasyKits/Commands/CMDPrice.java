/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 */
package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Utils.Notifications;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CMDPrice {
    public static void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("EasyKits.cmd.price")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        if (args.length == 3) {
            String kitName = args[1];
            Kit kit = new Kit(kitName);
            if (!kit.exists()) {
                Notifications notify2 = new Notifications("Kit-Not-Exist", kit.getName(), sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify2.getMessage());
                return;
            }
            String price = args[2];
            try {
                Double.parseDouble(price);
            }
            catch (NumberFormatException e) {
                Notifications notify3 = new Notifications("Invalid-Number", null, sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify3.getMessage());
                return;
            }
            kit.setPrice(Double.parseDouble(price));
            Notifications notify4 = new Notifications("Set-Price", kit.getName(), sender.getName(), Double.parseDouble(price), null, 0);
            sender.sendMessage(notify4.getMessage());
        } else {
            sender.sendMessage((Object)ChatColor.YELLOW + "/kit price <kitname> <price>");
        }
    }
}


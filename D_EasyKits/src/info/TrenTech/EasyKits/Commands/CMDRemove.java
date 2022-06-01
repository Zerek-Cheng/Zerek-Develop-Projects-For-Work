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

import java.sql.SQLException;

public class CMDRemove {
    public static void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("EasyKits.cmd.remove")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        if (args.length == 2) {
            String kitName = args[1];
            Kit kit = new Kit(kitName);
            if (!kit.exists()) {
                Notifications notify2 = new Notifications("Kit-Not-Exist", kit.getName(), sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify2.getMessage());
                return;
            }
            try {
                kit.remove();
                Notifications notify2 = new Notifications("Kit-Deleted", kit.getName(), sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify2.getMessage());
                return;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        }
        sender.sendMessage((Object)ChatColor.YELLOW + "/kit remove <kitname>");
    }
}


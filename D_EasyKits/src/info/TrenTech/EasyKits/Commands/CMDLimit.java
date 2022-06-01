/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 */
package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.SQL.SQLPlayers;
import info.TrenTech.EasyKits.Utils.Notifications;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CMDLimit {
    public static void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("EasyKits.cmd.limit")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        if (args.length == 3) {
            String kitName = args[1];
            Kit kit = new Kit(kitName);
            if (!kit.exists()) {
                Notifications notify2 = new Notifications("Kit-Not-Exist", kitName, sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify2.getMessage());
                return;
            }
            String limit = args[2];
            try {
                Integer.parseInt(limit);
            }
            catch (NumberFormatException e) {
                Notifications notify3 = new Notifications("Invalid-Number", null, sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify3.getMessage());
                return;
            }
            kit.setLimit(Integer.parseInt(limit));
            Notifications notify4 = new Notifications("Set-Kit-Limit", kit.getName(), sender.getName(), 0.0, null, Integer.parseInt(limit));
            sender.sendMessage(notify4.getMessage());
            List<String> list = SQLPlayers.getPlayerList();
            for (String playerUUID : list) {
                if (SQLPlayers.getLimit(playerUUID, kit.getName()) != 0) continue;
                SQLPlayers.setLimit(playerUUID, kit.getName(), Integer.parseInt(limit));
            }
        } else {
            sender.sendMessage((Object)ChatColor.YELLOW + "/kit limit <kitname> <limit>");
        }
    }
}


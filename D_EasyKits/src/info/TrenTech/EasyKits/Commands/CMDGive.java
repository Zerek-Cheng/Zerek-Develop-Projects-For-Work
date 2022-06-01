/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Server
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Kit.KitUser;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CMDGive {
    public static void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("EasyKits.cmd.give")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        if (args.length == 3) {
            UUID uuid = Utils.getUUID(args[2]);
            if (uuid == null) {
                Notifications notify2 = new Notifications("No-Player", null, args[2], 0.0, null, 0);
                sender.sendMessage(notify2.getMessage());
                return;
            }
            Player reciever = Utils.getPluginContainer().getServer().getPlayer(uuid);
            String kitName = args[1];
            Kit kit = new Kit(kitName);
            if (!kit.exists()) {
                Notifications notify3 = new Notifications("Kit-Not-Exist", kit.getName(), args[2], 0.0, null, 0);
                sender.sendMessage(notify3.getMessage());
                return;
            }
            KitUser kitUser = new KitUser(reciever, kit);
            try {
                kitUser.applyKit();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            Notifications notify4 = new Notifications("Kit-Received", kit.getName(), sender.getName(), kit.getPrice(), null, 0);
            reciever.sendMessage(notify4.getMessage());
            notify4 = new Notifications("Kit-Sent", kit.getName(), reciever.getName(), 0.0, null, 0);
            sender.sendMessage(notify4.getMessage());
        } else {
            sender.sendMessage((Object)ChatColor.YELLOW + "/kit give <kitname> <player>");
        }
    }
}


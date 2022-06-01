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

public class CMDReset {
    public static void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("EasyKits.cmd.reset")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        if (args.length == 4) {
            String playerName = args[3];
            UUID uuid = Utils.getUUID(playerName);
            if (uuid == null) {
                Notifications notify2 = new Notifications("No-Player", null, args[2], 0.0, null, 0);
                sender.sendMessage(notify2.getMessage());
                return;
            }
            Player player = Utils.getPluginContainer().getServer().getPlayer(uuid);
            String property = args[1];
            String kitName = args[2];
            Kit kit = new Kit(kitName);
            if (!kit.exists()) {
                Notifications notify3 = new Notifications("Kit-Not-Exist", kit.getName(), sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify3.getMessage());
                return;
            }
            KitUser kitUser = new KitUser(player, kit);
            if (property.equalsIgnoreCase("cooldown")) {
                kitUser.setDateObtained("2000-1-1 12:00:00");
                Notifications notify4 = new Notifications("Set-Cooldown", kitName, player.getName(), 0.0, "NONE", 0);
                sender.sendMessage(notify4.getMessage());
                player.sendMessage(notify4.getMessage());
            } else if (property.equalsIgnoreCase("limit")) {
                kitUser.setCurrentLimit(kit.getLimit());
                Notifications notify4 = new Notifications("Set-Kit-Limit", kitName, player.getName(), 0.0, null, 0);
                sender.sendMessage(notify4.getMessage());
                player.sendMessage(notify4.getMessage());
            } else {
                sender.sendMessage((Object)ChatColor.YELLOW + "/kit reset [cooldown | limit] <kitname> <player>");
            }
        } else {
            sender.sendMessage((Object)ChatColor.YELLOW + "/kit reset [cooldown | limit] <kitname> <player>");
        }
    }
}


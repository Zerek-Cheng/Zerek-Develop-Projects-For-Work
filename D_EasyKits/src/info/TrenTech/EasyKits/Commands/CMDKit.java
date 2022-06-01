/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
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

public class CMDKit {
    public static void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                Notifications notify = new Notifications("Not-Player", null, sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify.getMessage());
                return;
            }
            String kitName = args[0];
            Kit kit = new Kit(kitName);
            if (!kit.exists()) {
                Notifications notify2 = new Notifications("Kit-Not-Exist", kit.getName(), sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify2.getMessage());
                return;
            }
            Player player = (Player) sender;
            KitUser kitUser = new KitUser(player, kit);
            try {
                kitUser.applyKit();
            } catch (Exception e) {
                Utils.getLogger().severe(e.getMessage());
            }
        } else {
            sender.sendMessage(String.valueOf(ChatColor.DARK_GREEN) + String.valueOf(ChatColor.UNDERLINE) + "Command List:\n");
            sender.sendMessage(ChatColor.YELLOW + "/kit -or- /k");
            sender.sendMessage(ChatColor.YELLOW + "/kit [kitname]");
            if (sender.hasPermission("EasyKits.cmd.help")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit help [command]");
            }
            if (sender.hasPermission("EasyKits.cmd.reload")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit reload");
            }
            if (sender.hasPermission("EasyKits.cmd.create")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit create <kitname>");
            }
            if (sender.hasPermission("EasyKits.cmd.remove")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit remove <kitname>");
            }
            if (sender.hasPermission("EasyKits.cmd.limit")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit limit <kitname> <cooldown>");
            }
            if (sender.hasPermission("EasyKits.cmd.cooldown")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit cooldown <kitname> <cooldown>");
            }
            if (sender.hasPermission("EasyKits.cmd.price")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit price <kitname> <price>");
            }
            if (sender.hasPermission("EasyKits.cmd.reset")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit reset [cooldown | limit] <kitname> <player>");
            }
            if (sender.hasPermission("EasyKits.cmd.give")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit give <kitname> <player>");
            }
            if (sender.hasPermission("EasyKits.cmd.book")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit book");
            }
            if (sender.hasPermission("EasyKits.cmd.view")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit view <kitname>");
            }
            if (sender.hasPermission("EasyKits.cmd.list")) {
                sender.sendMessage((Object) ChatColor.YELLOW + "/kit list");
            }
        }
    }
}


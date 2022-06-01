/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Utils.Notifications;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CMDCreate {
    public static void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            Notifications notify = new Notifications("Not-Player", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        Player player = (Player)sender;
        if (!player.hasPermission("EasyKits.cmd.create")) {
            Notifications notify2 = new Notifications("Permission-Denied", null, sender.getName(), 0.0, null, 0);
            player.sendMessage(notify2.getMessage());
            return;
        }
        if (args.length == 2) {
            String kitName = args[1];
            Kit kit = new Kit(kitName);
            if (kit.exists()) {
                Notifications notify3 = new Notifications("Kit-Exist", kitName, null, 0.0, null, 0);
                player.sendMessage(notify3.getMessage());
                return;
            }
            try {
                kit.create(player, player.getInventory().getContents(), player.getInventory().getArmorContents());
                Notifications notify3 = new Notifications("Kit-Created", kitName, sender.getName(), 0.0, null, 0);
                player.sendMessage(notify3.getMessage());
                return;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        }
        player.sendMessage((Object)ChatColor.YELLOW + "/kit create <kitname>");
    }
}


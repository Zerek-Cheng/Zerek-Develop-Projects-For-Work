/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.entity.Player
 */
package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Kit.KitUser;
import info.TrenTech.EasyKits.SQL.SQLKits;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CMDList {
    public static void execute(CommandSender sender) {
        if (!sender.hasPermission("EasyKits.cmd.list")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        List<Kit> list = SQLKits.getKitList();
        sender.sendMessage(String.valueOf(ChatColor.UNDERLINE) + (Object) ChatColor.DARK_GREEN + "Kits:\n");
        for (Kit kit : list) {
            if ((!sender.hasPermission("EasyKits.kits." + kit.getName()) || kit.getName().equalsIgnoreCase(Utils.getConfig().getString("Config.First-Join-Kit"))) && !(sender instanceof ConsoleCommandSender))
                continue;
            String kitMsg = (Object) ChatColor.YELLOW + "- " + kit.getName();
            if (sender instanceof Player) {
                double price;
                Player player = (Player) sender;
                KitUser kitUser = new KitUser(player, kit);
                if (!player.hasPermission("EasyKits.bypass.limit") && kitUser.getCurrentLimit() == 0 && kit.getLimit() > 0) {
                    kitMsg = String.valueOf((Object) ChatColor.STRIKETHROUGH) + (Object) ChatColor.DARK_RED + "- " + kit.getName();
                }
                if (!player.hasPermission("EasyKits.bypass.cooldown") && kitUser.getCooldownTimeRemaining() != null) {
                    kitMsg = String.valueOf((Object) ChatColor.STRIKETHROUGH) + (Object) ChatColor.DARK_RED + "- " + kit.getName();
                }
                if ((price = kit.getPrice()) > 0.0 && !player.hasPermission("EasyKits.bypass.price")) {
                    kitMsg = String.valueOf(String.valueOf(kitMsg)) + ":";
                    double balance = Utils.getPluginContainer().economy.getBalance((OfflinePlayer) player);
                    kitMsg = balance < price ? String.valueOf(String.valueOf(kitMsg)) + (Object) ChatColor.DARK_RED + " $" + price : String.valueOf(String.valueOf(kitMsg)) + (Object) ChatColor.DARK_GREEN + " $" + price;
                }
            }
            sender.sendMessage(kitMsg);
        }
    }
}


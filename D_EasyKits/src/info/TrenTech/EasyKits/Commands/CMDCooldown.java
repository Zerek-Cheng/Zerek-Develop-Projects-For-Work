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

public class CMDCooldown {
    public static void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("EasyKits.cmd.cooldown")) {
            Notifications notify = new Notifications("Permission-Denied", null, null, 0.0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }
        if (args.length >= 3) {
            StringBuilder str = new StringBuilder(args[2]);
            String[] arrstring = args;
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String arg = arrstring[n2];
                if (!(arg.equals(args[0]) || arg.equals(args[1]) || arg.equals(args[2]))) {
                    str.append(" ").append(arg);
                }
                ++n2;
            }
            Kit kit = new Kit(args[1]);
            if (!kit.exists()) {
                Notifications notify2 = new Notifications("Kit-Not-Exist", args[1], sender.getName(), 0.0, null, 0);
                sender.sendMessage(notify2.getMessage());
                return;
            }
            String[] newArgs = str.toString().split(" ");
            if (!CMDCooldown.isValid(newArgs)) {
                Notifications notify3 = new Notifications("Invalid-Argument", args[1], sender.getName(), 0.0, str.toString(), 0);
                sender.sendMessage(notify3.getMessage());
                sender.sendMessage((Object)ChatColor.YELLOW + "/kit cooldown <kitname> <cooldown>");
                return;
            }
            kit.setCooldown(str.toString());
            Notifications notify3 = new Notifications("Set-Cooldown", kit.getName(), sender.getName(), 0.0, str.toString(), 0);
            sender.sendMessage(notify3.getMessage());
        } else {
            sender.sendMessage((Object)ChatColor.YELLOW + "/kit cooldown <kitname> <cooldown>");
        }
    }

    private static boolean isValid(String[] args) {
        int loop = 0;
        String[] arrstring = args;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String arg = arrstring[n2];
            if (!(arg.matches("(\\d+)[w]$") && loop == 0 || arg.matches("(\\d+)[d]$") && (loop == 0 || loop == 1) || arg.matches("(\\d+)[h]$") && (loop == 0 || loop == 1 || loop == 2) || arg.matches("(\\d+)[m]$") && (loop == 0 || loop == 1 || loop == 2 || loop == 3) || arg.matches("(\\d+)[s]$") && (loop == 0 || loop == 1 || loop == 2 || loop == 3 || loop == 4))) {
                if (arg.equalsIgnoreCase("0") && args.length == 1) {
                    return true;
                }
                return false;
            }
            ++loop;
            ++n2;
        }
        return true;
    }
}


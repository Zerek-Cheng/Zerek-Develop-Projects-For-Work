/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.SubCommand;
import com.pedrojm96.referralsystem.Util;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReferral
implements CommandExecutor {
    private static HashMap<List<String>, SubCommand> subcommand = new HashMap();

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] arrstring) {
        List<String> list2;
        if (!commandSender.hasPermission("Referral.use")) {
            String string2 = Util.rColor("&c\u2716 " + Messages.No_Permission());
            commandSender.sendMessage(string2);
            return true;
        }
        if (arrstring.length == 0 || arrstring.length >= 4) {
            commandSender.sendMessage(Messages.plugin_footer());
            commandSender.sendMessage(" ");
            commandSender.sendMessage(Util.rColor("&a- " + Messages.Command_Use()));
            commandSender.sendMessage(Util.rColor("&a- " + Messages.Description_Use()));
            commandSender.sendMessage(" ");
            commandSender.sendMessage(Messages.plugin_footer());
            return true;
        }
        boolean bl = false;
        for (List<String> list3 : subcommand.keySet()) {
            if (!list3.contains(arrstring[0])) continue;
            subcommand.get(list3).rum(commandSender, arrstring);
            bl = true;
            break;
        }
        if (!bl && subcommand.containsKey(list2 = Arrays.asList("numb"))) {
            subcommand.get(list2).rum(commandSender, arrstring);
        }
        return true;
    }

    public static void addsubcommand(List<String> list, SubCommand subCommand) {
        subcommand.put(list, subCommand);
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.Util;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    public abstract void onCommand(CommandSender var1, String[] var2);

    public abstract String getPerm();

    public boolean hasPerm(CommandSender commandSender) {
        if (this.getPerm() == null) {
            return true;
        }
        if (commandSender.hasPermission(this.getPerm())) {
            return true;
        }
        return false;
    }

    public void rum(CommandSender commandSender, String[] arrstring) {
        if (!this.hasPerm(commandSender)) {
            String string = Util.rColor("&c\u2716 " + Messages.No_Permission());
            commandSender.sendMessage(Messages.plugin_footer());
            commandSender.sendMessage("");
            commandSender.sendMessage(string);
            commandSender.sendMessage("");
            commandSender.sendMessage(Messages.plugin_footer());
            return;
        }
        this.onCommand(commandSender, arrstring);
    }
}


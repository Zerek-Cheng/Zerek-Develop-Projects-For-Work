/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package com.pedrojm96.referralsystem.subcommands;

import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.SubCommand;
import com.pedrojm96.referralsystem.sColor;
import org.bukkit.command.CommandSender;

public class cmsHelp
extends SubCommand {
    private String perm;

    public cmsHelp(String string) {
        this.perm = string;
    }

    public cmsHelp() {
        this.perm = null;
    }

    @Override
    public String getPerm() {
        return this.perm;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arrstring) {
        sColor sColor2 = new sColor(commandSender);
        sColor2.sendMessage(Messages.plugin_heade());
        sColor2.sendMessage("");
        sColor2.sendMessage(Messages.Help_CommandList());
        sColor2.sendMessage(" " + Messages.Help_Command_Help());
        sColor2.sendMessage(" &a- " + Messages.Help_Description_Help());
        if (commandSender.hasPermission("referral.code")) {
            sColor2.sendMessage(" " + Messages.Help_Command_Code());
            sColor2.sendMessage(" &a- " + Messages.Help_Description_Code());
        }
        sColor2.sendMessage(" " + Messages.Help_Command_Info());
        sColor2.sendMessage(" &a- " + Messages.Help_Description_Info());
        sColor2.sendMessage(" " + Messages.Help_Command_List());
        sColor2.sendMessage(" &a- " + Messages.Help_Description_List());
        sColor2.sendMessage(" " + Messages.Help_Command_Top());
        sColor2.sendMessage(" &a- " + Messages.Help_Description_Top());
        sColor2.sendMessage(" " + Messages.Help_Command_Claim());
        sColor2.sendMessage(" &a- " + Messages.Help_Description_Claim());
        sColor2.sendMessage(" " + Messages.Help_Command_Activate());
        sColor2.sendMessage(" &a- " + Messages.Help_Description_Activate());
        if (commandSender.hasPermission("referral.admin")) {
            sColor2.sendMessage(" " + Messages.Help_Command_AddPoints());
            sColor2.sendMessage(" &a- " + Messages.Help_Description_AddPoints());
            sColor2.sendMessage(" " + Messages.Help_Command_SetPoints());
            sColor2.sendMessage(" &a- " + Messages.Help_Description_SetPoints());
            sColor2.sendMessage(" " + Messages.Help_Command_Reload());
            sColor2.sendMessage(" &a- " + Messages.Help_Description_Reload());
        }
        sColor2.sendMessage("");
        sColor2.sendMessage(Messages.plugin_footer());
    }
}


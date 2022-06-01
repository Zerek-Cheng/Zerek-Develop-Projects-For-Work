/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package com.pedrojm96.referralsystem.subcommands;

import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.SubCommand;
import com.pedrojm96.referralsystem.Util;
import org.bukkit.command.CommandSender;

public class cmsReload
extends SubCommand {
    private String perm;

    public cmsReload(String string) {
        this.perm = string;
    }

    public cmsReload() {
        this.perm = null;
    }

    @Override
    public String getPerm() {
        return this.perm;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arrstring) {
        String string = Util.rColor("&7config.yml, messages.yml and claim.yml loaded");
        commandSender.sendMessage(Messages.plugin_heade());
        commandSender.sendMessage("");
        ReferralSystem.getInstance().iniConfig();
        ReferralSystem.getInstance().loadMessages();
        ReferralSystem.getInstance().iniClaim();
        commandSender.sendMessage(string);
        commandSender.sendMessage("");
        commandSender.sendMessage(Messages.plugin_footer());
    }
}


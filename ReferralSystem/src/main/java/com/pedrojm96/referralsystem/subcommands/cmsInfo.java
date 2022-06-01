/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem.subcommands;

import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.ReferralAPI;
import com.pedrojm96.referralsystem.SubCommand;
import com.pedrojm96.referralsystem.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmsInfo
extends SubCommand {
    private String perm;

    public cmsInfo(String string) {
        this.perm = string;
    }

    public cmsInfo() {
        this.perm = null;
    }

    @Override
    public String getPerm() {
        return this.perm;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arrstring) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Messages.No_Console());
            return;
        }
        Player player = (Player)commandSender;
        String string = Util.rColor(String.valueOf(Messages.Your_Referrals()) + " &6" + String.valueOf(ReferralAPI.getReferral((OfflinePlayer)player)));
        String string2 = Util.rColor(String.valueOf(Messages.Your_Referral_Points()) + " &6" + String.valueOf(ReferralAPI.getPoints((OfflinePlayer)player)));
        player.sendMessage(Messages.plugin_footer());
        player.sendMessage("");
        player.sendMessage(string);
        player.sendMessage(string2);
        player.sendMessage("");
        player.sendMessage(Messages.plugin_footer());
    }
}


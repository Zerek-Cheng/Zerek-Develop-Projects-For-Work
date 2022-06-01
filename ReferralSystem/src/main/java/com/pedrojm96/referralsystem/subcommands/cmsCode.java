/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem.subcommands;

import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Storage.Data;
import com.pedrojm96.referralsystem.SubCommand;
import com.pedrojm96.referralsystem.Util;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmsCode
extends SubCommand {
    private String perm;

    public cmsCode(String string) {
        this.perm = string;
    }

    public cmsCode() {
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
        String string = player.getUniqueId().toString();
        if (!ReferralSystem.data.checkData(string)) {
            player.sendMessage(Messages.plugin_footer());
            player.sendMessage("");
            int n = Util.inserCode();
            ReferralSystem.data.insert(player, n);
            String string2 = Util.rColor("&a\u2714 " + Messages.Your_Code() + " " + n);
            player.sendMessage(string2);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        int n = ReferralSystem.data.getCode(string);
        String string3 = Util.rColor("&a\u2714 " + Messages.Your_Code() + " " + n);
        player.sendMessage(Messages.plugin_footer());
        player.sendMessage("");
        player.sendMessage(string3);
        player.sendMessage("");
        player.sendMessage(Messages.plugin_footer());
    }
}


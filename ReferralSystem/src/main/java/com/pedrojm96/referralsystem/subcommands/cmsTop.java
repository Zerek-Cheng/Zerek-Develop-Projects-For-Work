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
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmsTop
extends SubCommand {
    private String perm;

    public cmsTop(String string) {
        this.perm = string;
    }

    public cmsTop() {
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
        List<String> list = ReferralSystem.data.getTop();
        player.sendMessage(Messages.plugin_footer());
        player.sendMessage("");
        player.sendMessage(Util.rColor(String.valueOf(Messages.Top_Players()) + ":"));
        player.sendMessage("");
        for (String string : list) {
            String string2 = Util.rColor(string);
            player.sendMessage(string2);
        }
        player.sendMessage("");
        player.sendMessage(Messages.plugin_footer());
    }
}


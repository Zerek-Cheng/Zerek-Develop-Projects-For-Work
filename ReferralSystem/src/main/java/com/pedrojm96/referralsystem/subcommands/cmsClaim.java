/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem.subcommands;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.SubCommand;
import com.pedrojm96.referralsystem.Util;
import com.pedrojm96.referralsystem.menuClaim;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmsClaim
extends SubCommand {
    private String perm;

    public cmsClaim(String string) {
        this.perm = string;
    }

    public cmsClaim() {
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
        if (!ReferralSystem.config.getBoolean("Menu-Claim")) {
            player.sendMessage(Messages.plugin_heade());
            player.sendMessage("");
            String string = Util.rColor("&c\u2716 " + Messages.No_Claim());
            player.sendMessage(string);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        menuClaim.open(player);
    }
}


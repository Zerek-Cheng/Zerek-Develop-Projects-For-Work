/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem.subcommands;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.ReferralAPI;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Storage.Data;
import com.pedrojm96.referralsystem.SubCommand;
import com.pedrojm96.referralsystem.Util;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmsSetPoints
extends SubCommand {
    private String perm;

    public cmsSetPoints(String string) {
        this.perm = string;
    }

    public cmsSetPoints() {
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
        player.sendMessage(Messages.plugin_footer());
        player.sendMessage("");
        if (arrstring.length < 3) {
            String string = Util.rColor("&a- " + Messages.Command_Error());
            String string2 = Util.rColor("&a- " + Messages.Command_SetPoints_Use());
            player.sendMessage(string);
            player.sendMessage(string2);
            player.sendMessage(" ");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer((String)arrstring[1]);
        if (offlinePlayer == null) {
            String string = Util.rColor("&c\u2716 " + Messages.No_Register());
            player.sendMessage(string);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        if (!ReferralSystem.data.checkData(offlinePlayer.getUniqueId().toString())) {
            String string = Util.rColor("&c\u2716 " + Messages.No_Register());
            player.sendMessage(string);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        if (!Util.isint(arrstring[2])) {
            String string = Util.rColor("&c\u2716 " + Messages.No_Numb());
            String string3 = Util.rColor("&a- " + Messages.Command_SetPoints_Use());
            player.sendMessage(string);
            player.sendMessage(string3);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        ReferralAPI.setPoints(offlinePlayer, (int)Integer.valueOf(arrstring[2]));
        String string = Util.rColor(Messages.Points_Set());
        string = string.replaceAll("<player>", arrstring[1]);
        string = string.replaceAll("<points>", arrstring[2]);
        player.sendMessage(string);
        if (ReferralSystem.config.getBoolean("Claim-Sound")) {
            boolean bl = Bukkit.getBukkitVersion().split("-")[0].contains("1.8");
            boolean bl2 = Bukkit.getBukkitVersion().split("-")[0].contains("1.7");
            if (bl || bl2) {
                player.playSound(player.getLocation(), Sound.valueOf((String)"LEVEL_UP"), 2.0f, 2.0f);
            } else {
                player.playSound(player.getLocation(), Sound.valueOf((String)"ENTITY_PLAYER_LEVELUP"), 2.0f, 2.0f);
            }
        }
        player.sendMessage("");
        player.sendMessage(Messages.plugin_footer());
    }
}


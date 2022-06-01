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
import com.pedrojm96.referralsystem.LocalData;
import com.pedrojm96.referralsystem.Messages;
import com.pedrojm96.referralsystem.ReferralAPI;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Storage.Data;
import com.pedrojm96.referralsystem.SubCommand;
import com.pedrojm96.referralsystem.Util;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmsNumb
extends SubCommand {
    private String perm;

    public cmsNumb(String string) {
        this.perm = string;
    }

    public cmsNumb() {
        this.perm = null;
    }

    @Override
    public String getPerm() {
        return this.perm;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] arrstring) {
        int n;
        long l;
        int n2;
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Messages.No_Console());
            return;
        }
        Player player = (Player)commandSender;
        String string = Util.rColor("&a- " + Messages.Checking_Code());
        player.sendMessage(Messages.plugin_footer());
        player.sendMessage("");
        player.sendMessage(string);
        if (!Util.isint(arrstring[0])) {
            String string2 = Util.rColor("&c\u2716 " + Messages.Error_Code());
            player.sendMessage(string2);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        int n3 = Integer.valueOf(arrstring[0]);
        if (!ReferralSystem.data.checkCode(n3)) {
            String string3 = Util.rColor("&c\u2716 " + Messages.Error_Code());
            player.sendMessage(string3);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        String string4 = player.getUniqueId().toString();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer((UUID)player.getUniqueId());
        String string5 = ReferralSystem.data.getPlayer(n3);
        OfflinePlayer offlinePlayer2 = Bukkit.getOfflinePlayer((UUID)UUID.fromString(string5));
        String string6 = player.getAddress().getAddress().getHostAddress();
        if (ReferralSystem.config.getBoolean("Limit-Player-IP") && (n2 = ReferralSystem.data.getNumbPlayerIp(string6)) >= ReferralSystem.config.getInt("Max-Player-IP")) {
            String string7 = Util.rColor("&c\u2716 " + Messages.Max_IP_Limit().replaceAll("<numb>", String.valueOf(n2)));
            player.sendMessage(string7);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        if (ReferralSystem.config.getBoolean("Requires-PlayTime") && (l = ReferralSystem.localdata.get(offlinePlayer.getUniqueId()).getPlaytimeSegundos()) < (long)(n = ReferralSystem.config.getInt("Min-PlayTime"))) {
            String string8 = Util.rColor("&a- " + Messages.Requires_PlayTime().replaceAll("<time>", Util.formatime(n)));
            String string9 = Util.rColor("&a- " + Messages.PlayTime().replaceAll("<time>", Util.formatime(l)));
            player.sendMessage(string8);
            player.sendMessage(string9);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        if (ReferralSystem.data.checkData(string4)) {
            if (ReferralSystem.data.getReferring(string4) == null) {
                if (string4.equalsIgnoreCase(string5)) {
                    String string10 = Util.rColor("&c\u2716 " + Messages.No_Use_Your_Code());
                    player.sendMessage(string10);
                    player.sendMessage("");
                    player.sendMessage(Messages.plugin_footer());
                    return;
                }
                String string11 = Util.rColor("&a\u2714 " + Messages.Activate_Code());
                player.sendMessage(string11);
                ReferralAPI.addReferralsPlayer(offlinePlayer2, offlinePlayer);
                if (ReferralSystem.config.getBoolean("Sounds-Enable")) {
                    boolean bl = Bukkit.getBukkitVersion().split("-")[0].contains("1.8");
                    n = Bukkit.getBukkitVersion().split("-")[0].contains("1.7") ? 1 : 0;
                    if (bl || n != 0) {
                        player.playSound(player.getLocation(), Sound.valueOf((String)"LEVEL_UP"), 2.0f, 2.0f);
                    } else {
                        player.playSound(player.getLocation(), Sound.valueOf((String)"ENTITY_PLAYER_LEVELUP"), 2.0f, 2.0f);
                    }
                }
                if (ReferralSystem.config.getBoolean("Particles-Enable")) {
                    Util.forParticles(player);
                }
                String string12 = String.valueOf(ReferralSystem.config.getInt("Points-Reward-Player"));
                String string13 = Util.rColor("&a\u2714 " + Messages.Reward_Player());
                string13 = string13.replaceAll("<points>", string12);
                player.sendMessage(string13);
                player.sendMessage("");
                player.sendMessage(Messages.plugin_footer());
                Player player2 = Bukkit.getServer().getPlayer(offlinePlayer2.getUniqueId());
                if (player2 != null) {
                    if (ReferralSystem.config.getBoolean("Sounds-Enable")) {
                        boolean bl = Bukkit.getBukkitVersion().split("-")[0].contains("1.8");
                        boolean bl2 = Bukkit.getBukkitVersion().split("-")[0].contains("1.7");
                        if (bl || bl2) {
                            player.playSound(player.getLocation(), Sound.valueOf((String)"LEVEL_UP"), 2.0f, 2.0f);
                        } else {
                            player.playSound(player.getLocation(), Sound.valueOf((String)"ENTITY_PLAYER_LEVELUP"), 2.0f, 2.0f);
                        }
                    }
                    String string14 = "&a\u2714 " + Messages.Reward_Referrer();
                    String string15 = String.valueOf(ReferralSystem.config.getInt("Points-Reward-Referrer"));
                    string14 = string14.replaceAll("<player>", player.getName());
                    string14 = string14.replaceAll("<points>", string15);
                    string14 = Util.rColor(string14);
                    player2.sendMessage(Messages.plugin_footer());
                    player2.sendMessage("");
                    player2.sendMessage(string14);
                    player2.sendMessage("");
                    player2.sendMessage(Messages.plugin_footer());
                }
                return;
            }
            String string16 = Util.rColor("&c\u2716 " + Messages.Already_Used());
            player.sendMessage(string16);
            player.sendMessage("");
            player.sendMessage(Messages.plugin_footer());
            return;
        }
        String string17 = Util.rColor("&a\u2714 " + Messages.Activate_Code());
        player.sendMessage(string17);
        int n4 = Util.inserCode();
        ReferralSystem.data.insert(player, n4);
        ReferralAPI.addReferralsPlayer(offlinePlayer2, offlinePlayer);
        if (ReferralSystem.config.getBoolean("Sounds-Enable")) {
            n = Bukkit.getBukkitVersion().split("-")[0].contains("1.8") ? 1 : 0;
            boolean bl = Bukkit.getBukkitVersion().split("-")[0].contains("1.7");
            if (n != 0 || bl) {
                player.playSound(player.getLocation(), Sound.valueOf((String)"LEVEL_UP"), 2.0f, 2.0f);
            } else {
                player.playSound(player.getLocation(), Sound.valueOf((String)"ENTITY_PLAYER_LEVELUP"), 2.0f, 2.0f);
            }
        }
        if (ReferralSystem.config.getBoolean("Particles-Enable")) {
            Util.forParticles(player);
        }
        String string18 = String.valueOf(ReferralSystem.config.getInt("Points-Reward-Player"));
        String string19 = Util.rColor("&a\u2714 " + Messages.Reward_Player());
        string19 = string19.replaceAll("<points>", string18);
        player.sendMessage(string19);
        player.sendMessage("");
        player.sendMessage(Messages.plugin_footer());
        Player player3 = Bukkit.getServer().getPlayer(offlinePlayer2.getUniqueId());
        if (player3 != null) {
            if (ReferralSystem.config.getBoolean("Sounds-Enable")) {
                boolean bl = Bukkit.getBukkitVersion().split("-")[0].contains("1.8");
                boolean bl3 = Bukkit.getBukkitVersion().split("-")[0].contains("1.7");
                if (bl || bl3) {
                    player.playSound(player.getLocation(), Sound.valueOf((String)"LEVEL_UP"), 2.0f, 2.0f);
                } else {
                    player.playSound(player.getLocation(), Sound.valueOf((String)"ENTITY_PLAYER_LEVELUP"), 2.0f, 2.0f);
                }
            }
            String string20 = "&a\u2714 " + Messages.Reward_Referrer();
            String string21 = String.valueOf(ReferralSystem.config.getInt("Points-Reward-Referrer"));
            string20 = string20.replaceAll("<player>", player.getName());
            string20 = string20.replaceAll("<points>", string21);
            string20 = Util.rColor(string20);
            player3.sendMessage(Messages.plugin_footer());
            player3.sendMessage("");
            player3.sendMessage(string20);
            player3.sendMessage("");
            player3.sendMessage(Messages.plugin_footer());
        }
    }
}


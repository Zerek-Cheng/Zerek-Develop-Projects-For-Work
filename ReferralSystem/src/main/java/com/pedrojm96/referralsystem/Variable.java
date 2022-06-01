/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  me.clip.placeholderapi.PlaceholderAPI
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ReferralSystem;
import java.util.ArrayList;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Variable {
    public static List<String> vu = new ArrayList<String>();

    public static String replaceVariables(String string, Player player) {
        String string2 = string;
        if (string2.contains("<player>")) {
            string2 = string2.replaceAll("<player>", player.getName());
        }
        if (string2.contains("<displayname>")) {
            string2 = string2.replaceAll("<displayname>", player.getDisplayName());
        }
        if (string2.contains("<world>")) {
            string2 = string2.replaceAll("<world>", player.getWorld().getName());
        }
        string2 = Variable.replaceUcode(string2);
        if (ReferralSystem.Placeholder) {
            string2 = PlaceholderAPI.setPlaceholders((Player)player, (String)string2);
        }
        return string2;
    }

    public static String replaceVariables(String string, CommandSender commandSender) {
        String string2 = string;
        if (string2.contains("<player>")) {
            string2 = string2.replaceAll("<player>", commandSender.getName());
        }
        string2 = Variable.replaceUcode(string2);
        return string2;
    }

    public static String replaceUcode(String string) {
        vu.clear();
        Variable.ini();
        String string2 = string;
        while (string2.contains("<ucode")) {
            String string3 = string2.split("<ucode")[1].split(">")[0];
            string2 = string2.replaceAll("<ucode" + string3 + ">", String.valueOf((char)Integer.parseInt(string3, 16)));
        }
        if (string2.contains("<a>")) {
            string2 = string2.replaceAll("<a>", "\u00e1");
        }
        if (string2.contains("<e>")) {
            string2 = string2.replaceAll("<e>", "\u00e9");
        }
        if (string2.contains("<i>")) {
            string2 = string2.replaceAll("<i>", "\u00ed");
        }
        if (string2.contains("<o>")) {
            string2 = string2.replaceAll("<o>", "\u00f3");
        }
        if (string2.contains("<u>")) {
            string2 = string2.replaceAll("<u>", "\u00fa");
        }
        int n = 1;
        for (String string4 : vu) {
            if (string2.contains("<" + n + ">")) {
                string2 = string2.replaceAll("<" + n + ">", string4);
            }
            ++n;
        }
        return string2;
    }

    public static void ini() {
        vu.add("\u2716");
        vu.add("\u2665");
        vu.add("\u2605");
        vu.add("\u25cf");
        vu.add("\u2666");
        vu.add("\u263b");
        vu.add("\u25ba");
        vu.add("\u25c4");
        vu.add("\u25ac");
        vu.add("\u272a");
        vu.add("\u2714");
        vu.add("\u2749");
        vu.add("\u25a0");
        vu.add("\u2580");
        vu.add("\u2584");
        vu.add("\u2620");
        vu.add("\u262d");
        vu.add("\u2122");
        vu.add("\u25e2");
        vu.add("\u25e3");
        vu.add("\u2763");
        vu.add("\u2600");
        vu.add("\u2740");
        vu.add("\u262a");
        vu.add("\u2623");
        vu.add("\u2612");
        vu.add("\u260e");
        vu.add("\u2591");
        vu.add("\u2611");
        vu.add("\u00bb");
        vu.add("\u00ab");
    }
}


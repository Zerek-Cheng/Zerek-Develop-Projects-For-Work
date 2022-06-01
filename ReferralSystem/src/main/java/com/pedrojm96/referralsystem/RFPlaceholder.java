/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  me.clip.placeholderapi.external.EZPlaceholderHook
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ReferralAPI;
import com.pedrojm96.referralsystem.ReferralSystem;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RFPlaceholder
extends EZPlaceholderHook {
    public RFPlaceholder(ReferralSystem referralSystem) {
        super((Plugin)referralSystem, "rs");
    }

    public String onPlaceholderRequest(Player player, String string) {
        if (player == null) {
            return "";
        }
        if (string.equals("points")) {
            return String.valueOf(ReferralAPI.getPoints((OfflinePlayer)player));
        }
        if (string.equals("referrals")) {
            return String.valueOf(ReferralAPI.getReferral((OfflinePlayer)player));
        }
        return null;
    }
}


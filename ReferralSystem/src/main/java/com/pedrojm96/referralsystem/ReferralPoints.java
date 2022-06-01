/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  me.robin.leaderheads.datacollectors.OnlineDataCollector
 *  me.robin.leaderheads.objects.BoardType
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ReferralAPI;
import java.util.Arrays;
import java.util.List;
import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ReferralPoints {
    public ReferralPoints() {

    public Double getScore(Player player) {
        int n = ReferralAPI.getPoints((OfflinePlayer)player);
        return n;
    }
}


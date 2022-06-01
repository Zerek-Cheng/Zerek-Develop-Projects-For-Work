/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.pedrojm96.supercredits.SCAPI
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.black_ixx.playerpoints.PlayerPoints
 *  org.black_ixx.playerpoints.PlayerPointsAPI
 *  org.bukkit.OfflinePlayer
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.LocalData;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Storage.Data;
import com.pedrojm96.supercredits.SCAPI;
import java.util.Map;
import java.util.UUID;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.OfflinePlayer;

public class ReferralAPI {
    public static void addReferralsPlayer(OfflinePlayer offlinePlayer, OfflinePlayer offlinePlayer2) {
        ReferralSystem.data.setReferring(offlinePlayer2.getUniqueId().toString(), offlinePlayer.getUniqueId().toString());
        ReferralAPI.addReferrals(offlinePlayer.getUniqueId());
        ReferralAPI.addPoints(offlinePlayer, ReferralSystem.config.getInt("Points-Reward-Referrer"));
        ReferralAPI.addPoints(offlinePlayer2, ReferralSystem.config.getInt("Points-Reward-Player"));
    }

    private static void addReferrals(UUID uUID) {
        int n = ReferralAPI.getReferral(uUID) + 1;
        ReferralSystem.data.setReferrals(uUID.toString(), n);
        if (ReferralSystem.localdata.containsKey(uUID)) {
            ReferralSystem.localdata.get(uUID).setReferrals(n);
        }
    }

    public static void addPoints(OfflinePlayer offlinePlayer, int n) {
        if (ReferralSystem.config.getBoolean("UseExternalPoints.enable")) {
            if (ReferralSystem.externalcred) {
                if (offlinePlayer.isOnline()) {
                    SCAPI.addValue((OfflinePlayer)offlinePlayer, (int)n);
                    SCAPI.addLocalValue((OfflinePlayer)offlinePlayer, (int)n);
                } else {
                    SCAPI.addValue((OfflinePlayer)offlinePlayer, (int)n);
                }
            } else if (ReferralSystem.externalpoints) {
                ReferralSystem.playerPoints.getAPI().give(offlinePlayer.getUniqueId(), n);
            } else if (ReferralSystem.externalecon) {
                ReferralSystem.economy.depositPlayer(offlinePlayer, (double)n);
            } else {
                ReferralAPI.addPoints(offlinePlayer.getUniqueId(), n);
            }
        } else {
            ReferralAPI.addPoints(offlinePlayer.getUniqueId(), n);
        }
    }

    private static void addPoints(UUID uUID, int n) {
        int n2 = ReferralAPI.getPoints(uUID) + n;
        ReferralSystem.data.setPoints(uUID.toString(), n2);
        if (ReferralSystem.localdata.containsKey(uUID)) {
            ReferralSystem.localdata.get(uUID).setPoints(n2);
        }
    }

    public static void setPoints(OfflinePlayer offlinePlayer, int n) {
        if (ReferralSystem.config.getBoolean("UseExternalPoints.enable")) {
            if (ReferralSystem.externalcred) {
                SCAPI.setValue((OfflinePlayer)offlinePlayer, (int)n);
            } else if (ReferralSystem.externalpoints) {
                ReferralSystem.playerPoints.getAPI().set(offlinePlayer.getUniqueId(), n);
            } else if (ReferralSystem.externalecon) {
                int n2 = n - ReferralAPI.getPoints(offlinePlayer);
                if (n2 > 0) {
                    ReferralSystem.economy.depositPlayer(offlinePlayer, (double)n2);
                } else {
                    ReferralSystem.economy.withdrawPlayer(offlinePlayer, (double)(- n2));
                }
            } else {
                ReferralAPI.setPoints(offlinePlayer.getUniqueId(), n);
            }
        } else {
            ReferralAPI.setPoints(offlinePlayer.getUniqueId(), n);
        }
    }

    private static void setPoints(UUID uUID, int n) {
        ReferralSystem.data.setPoints(uUID.toString(), n);
        if (ReferralSystem.localdata.containsKey(uUID)) {
            ReferralSystem.localdata.get(uUID).setPoints(n);
        }
    }

    public static int getPoints(OfflinePlayer offlinePlayer) {
        if (ReferralSystem.config.getBoolean("UseExternalPoints.enable")) {
            if (ReferralSystem.externalcred) {
                return SCAPI.getValue((OfflinePlayer)offlinePlayer);
            }
            if (ReferralSystem.externalpoints) {
                return ReferralSystem.playerPoints.getAPI().look(offlinePlayer.getUniqueId());
            }
            if (ReferralSystem.externalecon) {
                return (int)ReferralSystem.economy.getBalance(offlinePlayer);
            }
        }
        return ReferralAPI.getPoints(offlinePlayer.getUniqueId());
    }

    private static int getPoints(UUID uUID) {
        if (ReferralSystem.localdata.containsKey(uUID)) {
            return ReferralSystem.localdata.get(uUID).getPoints();
        }
        return ReferralSystem.data.getPoints(uUID.toString());
    }

    public static int getReferral(OfflinePlayer offlinePlayer) {
        return ReferralAPI.getReferral(offlinePlayer.getUniqueId());
    }

    private static int getReferral(UUID uUID) {
        if (ReferralSystem.localdata.containsKey(uUID)) {
            return ReferralSystem.localdata.get(uUID).getReferrals();
        }
        return ReferralSystem.data.getReferrals(uUID.toString());
    }
}


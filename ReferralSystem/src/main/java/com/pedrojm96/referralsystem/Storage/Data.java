/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem.Storage;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Storage.DMYSQL;
import com.pedrojm96.referralsystem.Storage.DSQLite;
import com.pedrojm96.referralsystem.Storage.dataCore;
import com.pedrojm96.referralsystem.itemPlayer;
import java.util.List;
import org.bukkit.entity.Player;

public class Data {
    private String table = "referralsystem3";
    private dataCore core;

    public Data() {
        if (ReferralSystem.config.getString("dataStorage.type").equalsIgnoreCase("MySQL")) {
            this.core = new DMYSQL(this.table);
            if (!this.core.checkStorage()) {
                this.core.build();
            }
        } else {
            this.core = new DSQLite(this.table);
            if (!this.core.checkStorage()) {
                this.core.build();
            }
        }
    }

    public void setReferrals(String string, int n) {
        this.core.setReferrals(string, n);
    }

    public void setPoints(String string, int n) {
        this.core.setPoints(string, n);
    }

    public int getPoints(String string) {
        return this.core.getPoints(string);
    }

    public void setReferring(String string, String string2) {
        this.core.setReferring(string, string2);
    }

    public int getReferrals(String string) {
        return this.core.getReferrals(string);
    }

    public boolean checkData(String string) {
        return this.core.checkData(string);
    }

    public void insert(Player player, int n) {
        this.core.insert(player, n);
    }

    public boolean checkCode(int n) {
        return this.core.checkCode(n);
    }

    public String getPlayer(int n) {
        return this.core.getPlayer(n);
    }

    public String getReferring(String string) {
        return this.core.getReferring(string);
    }

    public int getCode(String string) {
        return this.core.getCode(string);
    }

    public List<String> getTop() {
        return this.core.getTop();
    }

    public int getNumbPlayerIp(String string) {
        return this.core.getNumbPlayerIp(string);
    }

    public long getPlayTime(String string) {
        return this.core.getPlayTime(string);
    }

    public void setPlayTime(String string, long l) {
        this.core.setPlayTime(string, l);
    }

    public List<itemPlayer> getList(String string, int n) {
        return this.core.getList(string, n);
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem.Storage;

import com.pedrojm96.referralsystem.itemPlayer;
import java.util.List;
import org.bukkit.entity.Player;

public interface dataCore {
    public int getPoints(String var1);

    public int getCode(String var1);

    public long getPlayTime(String var1);

    public List<String> getTop();

    public List<itemPlayer> getList(String var1, int var2);

    public int getReferrals(String var1);

    public String getReferring(String var1);

    public int getNumbPlayerIp(String var1);

    public String getPlayer(int var1);

    public void setPoints(String var1, int var2);

    public void setReferrals(String var1, int var2);

    public void setReferring(String var1, String var2);

    public void setPlayTime(String var1, long var2);

    public boolean checkData(String var1);

    public boolean checkCode(int var1);

    public boolean checkStorage();

    public void insert(Player var1, int var2);

    public boolean update();

    public boolean destroy();

    public boolean build();
}


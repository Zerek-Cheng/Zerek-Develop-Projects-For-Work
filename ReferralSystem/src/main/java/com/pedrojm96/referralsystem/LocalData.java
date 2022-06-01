/*
 * Decompiled with CFR 0_133.
 */
package com.pedrojm96.referralsystem;

public class LocalData {
    private int referrals;
    private int points;
    private long playtime;
    private long time;

    public void setPoints(int n) {
        this.points = n;
    }

    public void addPoints(int n) {
        this.points += n;
    }

    public int getPoints() {
        return this.points;
    }

    public void setReferrals(int n) {
        this.referrals = n;
    }

    public int getReferrals() {
        return this.referrals;
    }

    public void addReferrals(int n) {
        this.referrals += n;
    }

    public void setPlaytime(long l) {
        this.playtime = l;
    }

    public long getPlaytime() {
        long l = System.currentTimeMillis();
        long l2 = l - this.time;
        long l3 = l2 + this.playtime;
        return l3;
    }

    public long getPlaytimeSegundos() {
        long l = System.currentTimeMillis();
        long l2 = l - this.time;
        long l3 = l2 + this.playtime;
        return l3 / 1000L;
    }

    public void setTime(long l) {
        this.time = l;
    }

    public long getTime() {
        return this.time;
    }
}


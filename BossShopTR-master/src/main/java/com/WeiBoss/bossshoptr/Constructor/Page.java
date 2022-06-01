package com.WeiBoss.bossshoptr.Constructor;

import org.bukkit.entity.Player;

public class Page {
    private Player p;
    private String shop;
    private Integer day;
    private Integer page;

    public Page(Player p, String shop, Integer day, Integer page) {
        this.p = p;
        this.shop = shop;
        this.day = day;
        this.page = page;
    }

    public Player getPlayer() {
        return p;
    }

    public String getShop() {
        return shop;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getPage() {
        return page;
    }

    public void setPlayer(Player p) {
        this.p = p;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
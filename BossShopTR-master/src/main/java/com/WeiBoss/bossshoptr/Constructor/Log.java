package com.WeiBoss.bossshoptr.Constructor;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

public class Log {
    private Integer id;
    private OfflinePlayer player;
    private ItemStack item;
    private Date date;
    private String shop;
    private String name;

    public Log(Integer id, OfflinePlayer player, ItemStack item, Date date, String shop, String name) {
        this.id = id;
        this.player = player;
        this.item = item;
        this.date = date;
        this.shop = shop;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public ItemStack getItem() {
        return item;
    }

    public Date getDate() {
        return date;
    }

    public String getShop() {
        return shop;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPlayer(OfflinePlayer player) {
        this.player = player;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public void setName(String name) {
        this.name = name;
    }
}
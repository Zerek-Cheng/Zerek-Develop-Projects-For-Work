package com._0myun.minecraft.netease.shopgodsender.bin.api;

import java.util.ArrayList;
import java.util.List;

public class ShipParams {
    private String gameid;
    private String uuid;
    private List<Long> orderid_list = new ArrayList<>();

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Long> getOrderid_list() {
        return orderid_list;
    }

    public void setOrderid_list(List<Long> orderid_list) {
        this.orderid_list = orderid_list;
    }
}

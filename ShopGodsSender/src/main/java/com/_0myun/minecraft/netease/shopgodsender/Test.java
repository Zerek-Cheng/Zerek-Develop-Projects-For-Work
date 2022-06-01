package com._0myun.minecraft.netease.shopgodsender;

import com._0myun.minecraft.netease.shopgodsender.api.ShopApi;

import java.util.Arrays;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
        ShopApi.setGameId("12312");
        ShopApi.setKey("1231231");
        ShopApi.requestOrders(UUID.fromString("1fa37f20-a544-482b-9115-671792251f47"));
        System.out.println(ShopApi.shipOrder(UUID.fromString("1fa37f20-a544-482b-9115-671792251f47"), Arrays.asList(new Long[]{4628309361345616484l})));
    }
}

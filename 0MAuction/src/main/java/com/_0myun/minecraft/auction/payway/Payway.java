package com._0myun.minecraft.auction.payway;

public abstract class Payway {
    public abstract String getName();

    public abstract void give(String player, int amount);

    public abstract boolean take(String player, int amount);

    public abstract int get(String player);

    public boolean has(String player, int amount) {
        return get(player) >= amount;
    }
    public abstract String getDate();
}

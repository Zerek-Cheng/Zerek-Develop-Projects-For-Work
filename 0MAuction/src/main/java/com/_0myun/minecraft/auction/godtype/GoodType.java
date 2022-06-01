package com._0myun.minecraft.auction.godtype;

import java.util.UUID;

public abstract class GoodType<T> {
    public abstract String getName();

    public abstract String toString(T good);

    public abstract T fromString(String str);

    public abstract boolean giveGood(UUID uuid, T good);

    public abstract String getData();
}

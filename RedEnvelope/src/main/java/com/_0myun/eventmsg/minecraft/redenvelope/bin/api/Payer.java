package com._0myun.eventmsg.minecraft.redenvelope.bin.api;

import org.bukkit.OfflinePlayer;

public abstract class Payer {
    public abstract String getName();

    public abstract boolean has(OfflinePlayer p, int amount);

    public abstract boolean take(OfflinePlayer p, int amount);

    public abstract boolean give(OfflinePlayer p, int amount);
}

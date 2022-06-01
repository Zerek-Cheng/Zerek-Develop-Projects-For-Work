package com._0myun.eventmsg.minecraft.redenvelope.bin.api;

import com._0myun.eventmsg.minecraft.redenvelope.RedEnvelope;
import org.bukkit.OfflinePlayer;

public class Gold extends Payer {
    @Override
    public String getName() {
        return "金币";
    }

    @Override
    public boolean has(OfflinePlayer p, int amount) {
        return RedEnvelope.economy.has(p, amount);
    }

    @Override
    public boolean take(OfflinePlayer p, int amount) {
        if (!has(p,amount))return false;
        return RedEnvelope.economy.withdrawPlayer(p, amount).transactionSuccess();
    }

    @Override
    public boolean give(OfflinePlayer p, int amount) {
        return RedEnvelope.economy.depositPlayer(p, amount).transactionSuccess();
    }
}

package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.api;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.VexRedEnvelope;
import org.bukkit.OfflinePlayer;

public class Gold extends Payer {
    @Override
    public String getName() {
        return "金币";
    }

    @Override
    public boolean has(OfflinePlayer p, int amount) {
        return VexRedEnvelope.economy.has(p, amount);
    }

    @Override
    public boolean take(OfflinePlayer p, int amount) {
        if (!has(p,amount))return false;
        return VexRedEnvelope.economy.withdrawPlayer(p, amount).transactionSuccess();
    }

    @Override
    public boolean give(OfflinePlayer p, int amount) {
        return VexRedEnvelope.economy.depositPlayer(p, amount).transactionSuccess();
    }
}

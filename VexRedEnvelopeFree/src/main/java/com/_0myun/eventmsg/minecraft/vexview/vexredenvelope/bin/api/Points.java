package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.api;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.VexRedEnvelope;
import org.bukkit.OfflinePlayer;

public class Points extends Payer {
    @Override
    public String getName() {
        return "点券";
    }

    @Override
    public boolean has(OfflinePlayer p, int amount) {
        return VexRedEnvelope.points.look(p.getUniqueId()) >= amount;
    }

    @Override
    public boolean take(OfflinePlayer p, int amount) {
        if (!has(p, amount)) return false;
        return VexRedEnvelope.points.take(p.getUniqueId(), amount);
    }

    @Override
    public boolean give(OfflinePlayer p, int amount) {
        return VexRedEnvelope.points.give(p.getUniqueId(), amount);
    }
}

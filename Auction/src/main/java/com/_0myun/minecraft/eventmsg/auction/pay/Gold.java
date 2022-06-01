package com._0myun.minecraft.eventmsg.auction.pay;

import com._0myun.minecraft.eventmsg.auction.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Gold extends PayType {

    @Override
    public boolean has(OfflinePlayer p, int amount) {
        return Main.economy.has(p, amount);
    }

    @Override
    public boolean take(OfflinePlayer p, int amount) {
        return Main.economy.withdrawPlayer(p, amount).transactionSuccess();
    }

    @Override
    public boolean refund(OfflinePlayer p, int amount) {
        return Main.economy.depositPlayer(p, amount).transactionSuccess();
    }

    @Override
    public String getName() {
        return "金币";
    }

}

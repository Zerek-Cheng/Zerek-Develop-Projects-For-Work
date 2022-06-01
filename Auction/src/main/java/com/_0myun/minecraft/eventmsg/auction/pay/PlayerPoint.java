package com._0myun.minecraft.eventmsg.auction.pay;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerPoint extends PayType {
    private PlayerPointsAPI api = ((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI();

    @Override
    public boolean has(OfflinePlayer p, int amount) {
        return api.look(p.getUniqueId()) >= amount;
    }

    @Override
    public boolean take(OfflinePlayer p, int amount) {
        return api.take(p.getUniqueId(), amount);
    }

    @Override
    public boolean refund(OfflinePlayer p, int amount) {
        return api.give(p.getUniqueId(), amount);
    }

    @Override
    public String getName() {
        return "点券";
    }


}

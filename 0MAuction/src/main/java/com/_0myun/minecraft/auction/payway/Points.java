package com._0myun.minecraft.auction.payway;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;

public class Points extends Payway {

    public PlayerPointsAPI api = null;

    public Points() {
        this.api = ((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI();
    }

    @Override
    public String getName() {
        return "点券";
    }

    @Override
    public void give(String player, int amount) {
        this.api.give(player, amount);
    }

    @Override
    public boolean take(String player, int amount) {
        return this.api.take(player, amount);
    }

    @Override
    public int get(String player) {
        return this.api.look(player);
    }

    public String getDate() {
        return "points";
    }
}

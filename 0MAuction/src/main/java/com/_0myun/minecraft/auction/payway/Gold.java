package com._0myun.minecraft.auction.payway;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Gold extends Payway {
    public Economy economy = null;

    public Gold() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
    }

    @Override
    public String getName() {
        return "游戏币";
    }

    @Override
    public void give(String player, int amount) {
        this.economy.depositPlayer(player, amount);
    }

    @Override
    public boolean take(String player, int amount) {
        return this.economy.withdrawPlayer(player, amount).transactionSuccess();
    }

    @Override
    public int get(String player) {
        return (int) this.economy.getBalance(player);
    }

    public String getDate() {
        return "gold";
    }

}

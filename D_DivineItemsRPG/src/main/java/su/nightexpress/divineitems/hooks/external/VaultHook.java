/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.RegisteredServiceProvider
 */
package su.nightexpress.divineitems.hooks.external;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.hooks.Hook;

public class VaultHook {
    private Economy economy;

    public VaultHook(DivineItems divineItems) {
    }

    public double getBalans(Player player) {
        if (!Hook.VAULT.isEnabled()) {
            return 0.0;
        }
        double d = this.economy.getBalance((OfflinePlayer)player);
        return d;
    }

    public boolean give(Player player, double d) {
        if (Hook.VAULT.isEnabled()) {
            EconomyResponse economyResponse = this.economy.depositPlayer((OfflinePlayer)player, d);
            return economyResponse.transactionSuccess();
        }
        return false;
    }

    public boolean take(Player player, double d) {
        if (Hook.VAULT.isEnabled()) {
            EconomyResponse economyResponse = this.economy.withdrawPlayer((OfflinePlayer)player, d);
            return economyResponse.transactionSuccess();
        }
        return false;
    }

    public void setupEconomy() {
        RegisteredServiceProvider registeredServiceProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (registeredServiceProvider == null) {
            return;
        }
        this.economy = (Economy)registeredServiceProvider.getProvider();
    }
}


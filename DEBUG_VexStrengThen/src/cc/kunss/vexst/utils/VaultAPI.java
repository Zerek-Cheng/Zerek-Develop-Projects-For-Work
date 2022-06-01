/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 */
package cc.kunss.vexst.utils;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class VaultAPI {
    private static Economy economy;

    public static boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = (Economy)rsp.getProvider();
        return economy != null;
    }

    public static double getMoney(String name) {
        if (economy == null) {
            throw new UnsupportedOperationException("\u8fd8\u6ca1\u8fde\u63a5\u5230Vault");
        }
        return economy.getBalance(name);
    }

    public static void giveMoney(String name, double money) {
        if (economy == null) {
            throw new UnsupportedOperationException("\u8fd8\u6ca1\u8fde\u63a5\u5230Vault");
        }
        economy.depositPlayer(name, money);
    }

    public static void removeMoney(String name, double money) {
        if (economy == null) {
            throw new UnsupportedOperationException("\u8fd8\u6ca1\u8fde\u63a5\u5230Vault");
        }
        economy.withdrawPlayer(name, money);
    }
}


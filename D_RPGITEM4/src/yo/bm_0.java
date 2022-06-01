/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 */
package yo;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import yo.ao_0;
import yo.bj_0;

public class bm_0
extends bj_0 {
    private static Economy b = null;

    @Override
    public void a() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Vault");
        if (plugin == null || !plugin.isEnabled()) {
            return;
        }
        RegisteredServiceProvider economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            b = (Economy)economyProvider.getProvider();
        }
        this.a = b != null;
    }

    public void a(String player, double amount, String locale) {
        if (amount <= 0.0 || b == null) {
            return;
        }
        if (!b.hasAccount(player)) {
            b.createPlayerAccount(player);
        }
        b.depositPlayer(player, amount);
        Player p = Bukkit.getPlayerExact((String)player);
        if (p != null) {
            p.sendMessage((Object)ChatColor.GREEN + String.format(ao_0.a("message.money.deposit", locale), amount));
        }
    }

    public void a(Player player, double amount) {
        this.a(player.getName(), amount, ao_0.a(player));
    }

    public boolean b(Player player, double amount) {
        return this.b(player.getName(), amount, ao_0.a(player));
    }

    public boolean b(String player, double amount, String locale) {
        if (amount <= 0.0 || b == null) {
            return true;
        }
        if (!b.hasAccount(player)) {
            b.createPlayerAccount(player);
        }
        if (b.getBalance(player) < amount) {
            Player p = Bukkit.getPlayerExact((String)player);
            if (p != null) {
                p.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.money.notenough", locale), amount));
            }
            return false;
        }
        return true;
    }

    public boolean c(String player, double amount, String locale) {
        if (amount <= 0.0 || b == null) {
            return true;
        }
        if (this.b(player, amount, locale)) {
            b.withdrawPlayer(player, amount);
            Player p = Bukkit.getPlayerExact((String)player);
            if (p != null) {
                p.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.money.withdraw", locale), amount));
            }
            return true;
        }
        return false;
    }

    public boolean c(Player player, double amount) {
        return this.c(player.getName(), amount, ao_0.a(player));
    }

    public Economy d() {
        return b;
    }
}


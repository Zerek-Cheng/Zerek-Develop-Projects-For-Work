/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  com.pixelmonmod.pixelmon.Pixelmon
 *  com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount
 *  com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccountManager
 *  com.pixelmonmod.pixelmon.api.events.EconomyEvent
 *  com.pixelmonmod.pixelmon.api.events.EconomyEvent$PostTransactionEvent
 *  com.pixelmonmod.pixelmon.api.events.EconomyEvent$PreTransactionEvent
 *  com.pixelmonmod.pixelmon.api.events.EconomyEvent$transactionType
 *  com.pixelmonmod.pixelmon.storage.PlayerStorage
 *  net.milkbowl.vault.Vault
 *  net.milkbowl.vault.economy.Economy
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.EventBus
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.ServicePriority
 *  org.bukkit.plugin.java.JavaPlugin
 */
package luohuayu.CatPixelmonSync;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccountManager;
import com.pixelmonmod.pixelmon.api.events.EconomyEvent;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;

import java.util.Optional;
import java.util.UUID;

import luohuayu.CatPixelmonSync.EconomyHook;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class CatPixelmonSync
        extends JavaPlugin {
    public static CatPixelmonSync instance;
    public Pixelmon pixelmon;
    public IPixelmonBankAccountManager moneyManager;

    public void onLoad() {
        instance = this;
        try {
            this.setupPixelmon();
            this.setupEconomy();
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin((Plugin) this);
        }
    }

    private void setupPixelmon() throws Exception {
        this.pixelmon = Pixelmon.instance;
        this.moneyManager = Pixelmon.moneyManager;
    }

    private void setupEconomy() throws Exception {
        Vault vault = (Vault) Bukkit.getPluginManager().getPlugin("Vault");
        Bukkit.getServicesManager().register(Economy.class, new EconomyHook(), vault, ServicePriority.Highest);
    }

    public IPixelmonBankAccount getPixelmonBankAccount(String playerName) {
        UUID uuid = Bukkit.getOfflinePlayer((String) playerName).getUniqueId();
        return this.getPixelmonBankAccount(uuid);
    }

    public IPixelmonBankAccount getPixelmonBankAccount(UUID uuid) {
        Optional account = this.moneyManager.getBankAccount(uuid);
        return account.isPresent() ? (IPixelmonBankAccount) account.get() : null;
    }

    public boolean changeMoney(IPixelmonBankAccount account, int change) {
        PlayerStorage ps = (PlayerStorage) account;
        int oldBalance = ps.pokeDollars;
        EconomyEvent.transactionType type = change < 0 ? EconomyEvent.transactionType.withdraw : EconomyEvent.transactionType.deposit;
        EconomyEvent.PreTransactionEvent preEvent = new EconomyEvent.PreTransactionEvent(ps.getPlayer(), type, ps.pokeDollars, change);
        if (!Pixelmon.EVENT_BUS.post((Event) preEvent)) {
            int newBalance = ps.pokeDollars + preEvent.change;
            if (ps.pokeDollars != newBalance) {
                ps.pokeDollars = newBalance;
                if (ps.pokeDollars < 0) {
                    ps.pokeDollars = 0;
                }
                if (ps.getPlayer() != null) {
                    ps.updatePlayer(ps.pokeDollars);
                }
            }
        } else {
            return false;
        }
        Pixelmon.EVENT_BUS.post((Event) new EconomyEvent.PostTransactionEvent(ps.getPlayer(), type, oldBalance, ps.pokeDollars));
        return true;
    }
}


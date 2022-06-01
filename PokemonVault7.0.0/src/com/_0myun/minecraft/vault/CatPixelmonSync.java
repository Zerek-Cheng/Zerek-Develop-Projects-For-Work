package com._0myun.minecraft.vault;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.EconomyEvent;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Level;

public class CatPixelmonSync
        extends JavaPlugin {
    public static CatPixelmonSync instance;
    public Pixelmon pixelmon;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "神奇宝贝重铸7.0.0兼容作者：【灵梦云科技0MYUN.COM】");
        getLogger().log(Level.INFO, "插件定制+q2025255093");
        getLogger().log(Level.INFO, "基于神奇宝贝重铸634修改(作者落花雨)");
    }

    public void onLoad() {
        instance = this;
        try {
            setupPixelmon();
            setupEconomy();
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void setupPixelmon()
            throws Exception {
        this.pixelmon = Pixelmon.instance;

    }

    private void setupEconomy() {
        Vault vault = (Vault) Bukkit.getPluginManager().getPlugin("Vault");
        Bukkit.getServicesManager().register(Economy.class, new EconomyHook(), vault, ServicePriority.Highest);
    }

    public PlayerPartyStorage getPixelmonBankAccount(String playerName) {
        UUID uuid = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        return getPixelmonBankAccount(uuid);
    }

    public PlayerPartyStorage getPixelmonBankAccount(UUID uuid) {
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(uuid);
        return party;
        /*
        net.minecraft.entity.player.EntityPlayerMP playerFromUUID = PixelmonStorage.pokeBallManager.getPlayerFromUUID(FMLCommonHandler.instance().getMinecraftServerInstance(), uuid);
        Optional<? extends PlayerStorage> account = PixelmonStorage.pokeBallManager.getPlayerStorage(playerFromUUID);
        return account.isPresent() ? (PlayerStorage) account.get() : null;*/
    }

    public boolean changeMoney(PlayerPartyStorage account, int change) {
        PlayerPartyStorage ps = (PlayerPartyStorage) account;
        int oldBalance = ps.getMoney();
        EconomyEvent.transactionType type = change < 0 ? EconomyEvent.transactionType.withdraw : EconomyEvent.transactionType.deposit;
        EconomyEvent.PreTransactionEvent preEvent = new EconomyEvent.PreTransactionEvent(ps.getPlayer(), type, oldBalance, change);
        if (!Pixelmon.EVENT_BUS.post(preEvent)) {
            int newBalance = oldBalance + preEvent.change;
            if (ps.getMoney() != newBalance) {
                ps.setMoney(newBalance);
                if (ps.getMoney() < 0) {
                    ps.setMoney(0);
                }
                if (ps.getPlayer() != null) {
                    ps.setMoney(ps.getMoney());
                }
            }
            Pixelmon.EVENT_BUS.post(new EconomyEvent.PostTransactionEvent(ps.getPlayer(), type, oldBalance, ps.getMoney()));
        } else {
            return false;
        }
        return true;
    }
}

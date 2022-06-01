package luohuayu.CatPixelmonSync;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.EconomyEvent;
import com.pixelmonmod.pixelmon.commands.PokeGiveEgg;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.UUID;

public class CatPixelmonSync
        extends JavaPlugin {
    public static CatPixelmonSync instance;
    public Pixelmon pixelmon;

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

    private void setupEconomy()
            throws Exception {
        Vault vault = (Vault) Bukkit.getPluginManager().getPlugin("Vault");
        Bukkit.getServicesManager().register(Economy.class, new EconomyHook(), vault, ServicePriority.Highest);
    }

    public PlayerStorage getPixelmonBankAccount(String playerName) {
        UUID uuid = Bukkit.getOfflinePlayer(playerName).getUniqueId();
        return getPixelmonBankAccount(uuid);
    }

    public PlayerStorage getPixelmonBankAccount(UUID uuid) {

        net.minecraft.entity.player.EntityPlayerMP playerFromUUID = PixelmonStorage.pokeBallManager.getPlayerFromUUID(FMLCommonHandler.instance().getMinecraftServerInstance(), uuid);
        Optional<? extends PlayerStorage> account = PixelmonStorage.pokeBallManager.getPlayerStorage(playerFromUUID);
        return account.isPresent() ? (PlayerStorage) account.get() : null;
    }

    public boolean changeMoney(PlayerStorage account, int change) {
        PlayerStorage ps = (PlayerStorage) account;
        int oldBalance = ps.getCurrency();
        EconomyEvent.transactionType type = change < 0 ? EconomyEvent.transactionType.withdraw : EconomyEvent.transactionType.deposit;
        EconomyEvent.PreTransactionEvent preEvent = new EconomyEvent.PreTransactionEvent(ps.getPlayer(), type, oldBalance, change);
        if (!Pixelmon.EVENT_BUS.post(preEvent)) {
            int newBalance = oldBalance + preEvent.change;
            if (ps.getCurrency() != newBalance) {
                ps.setCurrency(newBalance);
                if (ps.getCurrency() < 0) {
                    ps.setCurrency(0);
                }
                if (ps.getPlayer() != null) {
                    ps.setCurrency(ps.getCurrency());
                }
            }
            Pixelmon.EVENT_BUS.post(new EconomyEvent.PostTransactionEvent(ps.getPlayer(), type, oldBalance, ps.getCurrency()));
        } else {
            return false;
        }
        return true;
    }
}

/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.Bukkit
 *  org.bukkit.Server
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.FileConfigurationOptions
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 *  org.bukkit.plugin.java.JavaPlugin
 */
package info.TrenTech.EasyKits;

import info.TrenTech.EasyKits.Commands.CommandHandler;
import info.TrenTech.EasyKits.Events.MainListener;
import info.TrenTech.EasyKits.Events.SignListener;
import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.SQL.SQLKits;
import info.TrenTech.EasyKits.SQL.SQLPlayers;
import info.TrenTech.EasyKits.SQL.SQLUtils;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class EasyKits
        extends JavaPlugin {
    public final Logger logger = Logger.getLogger("Minecraft");
    public Economy economy;
    public static HashMap<UUID, String> players = new HashMap();
    public boolean econSupport = true;
    private CommandHandler cmdExecutor;
    public static HashMap<String, String> messages = new HashMap();
    @Override
    public void onEnable() {
        this.setPlugin();
        EasyKits.registerEvents((Plugin) this, new MainListener(), new SignListener());
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        new Notifications().getMessages();
        this.cmdExecutor = new CommandHandler();
        this.getCommand("kit").setExecutor((CommandExecutor) this.cmdExecutor);
        if (!this.setupEconomy()) {
            this.logger.warning(String.format("[%s] Vault \u65e0\u6cd5\u52a0\u8f7d", this.getDescription().getName()));
            this.econSupport = false;
        } else {
            this.logger.info(String.format("[%s] \u5df2\u52a0\u8f7d", this.getDescription().getName()));
        }
        try {
            SQLUtils.connect();
        } catch (Exception e) {
            this.logger.severe(String.format("[%s] \u65e0\u6cd5\u8fde\u63a5\u6570\u636e\u5e93", this.getDescription().getName()));
            return;
        }
        if (!SQLKits.tableExist()) {
            SQLKits.createTable();
            this.logger.info("\u8fde\u63a5\u6570\u636e\u5e93\u6210\u529f");
        }
        this.logger.info("\u00a7d\u00a7l\u63d2\u4ef6\u4ea4\u6d41\u7fa4 1447991");
        Iterator<Player> onlinePlayers = (Iterator<Player>) this.getServer().getOnlinePlayers().iterator();
        onlinePlayers.forEachRemaining((player) -> {
            players.put(player.getUniqueId(), player.getName());
        });
        this.fixKitValues();
        this.fixPlayerValues();
        this.fixConfigValues();
    }

    public static /* varargs */ void registerEvents(Plugin plugin, Listener... listeners) {
        Listener[] arrlistener = listeners;
        int n = arrlistener.length;
        int n2 = 0;
        while (n2 < n) {
            Listener listener = arrlistener[n2];
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
            ++n2;
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            this.economy = (Economy) economyProvider.getProvider();
        }
        if (this.economy != null) {
            return true;
        }
        return false;
    }

    public void setPlugin() {
        Utils.setPlugin(new EasyKitsMod(this, this.logger, this.getConfig()));
    }

    public void fixConfigValues() {
        int limit;
        double price;
        String cooldown = this.getConfig().getString("Config.Default-Cooldown");
        if (cooldown.equalsIgnoreCase("-1")) {
            this.logger.warning(String.format("[%s] Patching Config Value", this.getDescription().getName()));
            this.getConfig().set("Config.Default-Cooldown", (Object) "0");
        }
        if ((price = this.getConfig().getDouble("Config.Default-Price")) == -1.0) {
            this.logger.warning(String.format("[%s] Patching Config Value", this.getDescription().getName()));
            this.getConfig().set("Config.Default-Price", (Object) 0);
        }
        if ((limit = this.getConfig().getInt("Config.Default-Kit-Limit")) == -1) {
            this.logger.warning(String.format("[%s] Patching Config Value", this.getDescription().getName()));
            this.getConfig().set("Config.Default-Kit-Limit", (Object) 0);
        }
    }

    public void fixKitValues() {
        List<Kit> list = SQLKits.getKitList();
        for (Kit kit : list) {
            if (SQLKits.getKitLimit(kit.getName()) == -1) {
                this.logger.warning(String.format("[%s] Patching Kit Table", this.getDescription().getName()));
                SQLKits.setKitLimit(kit.getName(), 0);
            }
            if (SQLKits.getKitPrice(kit.getName()) == -1.0) {
                this.logger.warning(String.format("[%s] Patching Kit Table", this.getDescription().getName()));
                SQLKits.setKitPrice(kit.getName(), 0.0);
            }
            if (!SQLKits.getKitCooldown(kit.getName()).equalsIgnoreCase("-1")) continue;
            this.logger.warning(String.format("[%s] Patching Kit Table", this.getDescription().getName()));
            SQLKits.setKitCooldown(kit.getName(), "0");
        }
    }

    public void fixPlayerValues() {
        if (1==1){
            return;
        }
        List<String> playerList = SQLPlayers.getPlayerList();
        for (String playerUUID : playerList) {
            List<Kit> kitList = SQLKits.getKitList();
            for (Kit kit : kitList) {
                if (SQLPlayers.getLimit(playerUUID, kit.getName()) != -1) continue;
                this.logger.warning(String.format("[%s] Patching Player Table", this.getDescription().getName()));
                SQLPlayers.setLimit(playerUUID, kit.getName(), 0);
            }
        }
    }
}


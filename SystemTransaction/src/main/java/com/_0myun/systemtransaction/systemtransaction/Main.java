package com._0myun.systemtransaction.systemtransaction;

import com._0myun.systemtransaction.systemtransaction.inventory.SellGoodGui;
import com._0myun.systemtransaction.systemtransaction.inventory.SellMenu;
import com._0myun.systemtransaction.systemtransaction.listener.GuiListener;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public final class Main extends JavaPlugin {
    public static Economy economy = null;
    @Getter
    private YamlConfiguration config;

    @Override
    public void onEnable() {
        this.setupEconomy();
        this.loadConfig();
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.consoleCommand(sender, args) && sender instanceof Player) {
            if (!this.playerCommand((Player) sender, args)) {
                sender.sendMessage(LangUtil.getLang("lang1").replace("<command>", label));
            }
        }
        return super.onCommand(sender, command, label, args);
    }

    public boolean consoleCommand(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.loadConfig();
            LangUtil.reload();
            sender.sendMessage(LangUtil.getLang("lang2"));
            return true;
        }
        return false;
    }

    public boolean playerCommand(Player p, String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("sell")) {
            if (args.length == 1) {
                SellMenu.openSellInv(p, 0);
            } else {
                SellMenu.openSellInv(p, Integer.valueOf(args[1]));
            }
            p.sendMessage(LangUtil.getLang("lang5"));
            return true;
        } else if (args.length >= 2 && args[0].equalsIgnoreCase("sellgood")) {
            String sign = args[1];
            GoodsManager.Good good = new GoodsManager().searchGoodBySign(sign);
            if (good == null) {
                p.sendMessage(LangUtil.getLang("lang8"));
                return true;
            }
            SellGoodGui.openSellInv(p, good);
        }
        return false;
    }

    private void loadConfig() {
        getLogger().log(Level.INFO, "数据文件加载中....");
        saveResource("config.yml", false);
        saveResource("lang.yml", false);
        this.config = YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/config.yml"));
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> EconomyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (EconomyProvider != null) {
            economy = EconomyProvider.getProvider();
        }
        return (economy != null);
    }


    public static Main getPlugin() {
        return (Main) Bukkit.getPluginManager().getPlugin("SystemTransaction");
    }
}

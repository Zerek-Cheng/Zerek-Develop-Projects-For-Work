package com._0myun.minecraft.eventmsg.itembreaker;

import com._0myun.minecraft.eventmsg.itembreaker.bin.BreakAble;
import com._0myun.minecraft.eventmsg.itembreaker.bin.Product;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public final class ItemBreaker extends JavaPlugin {
    @Getter
    private static ItemBreaker plugin = null;
    @Getter
    private YamlConfiguration data = null;
    @Getter
    public static PlayerPointsAPI points;
    @Getter
    public static Economy economy;

    @Override
    public void onEnable() {
        plugin = this;
        points = ((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI();
        this.setupEconomy();
        ConfigurationSerialization.registerClass(Product.class, "com._0myun.minecraft.eventmsg.itembreaker.bin.Product");
        ConfigurationSerialization.registerClass(BreakAble.class, "com._0myun.minecraft.eventmsg.itembreaker.bin.BreakAble");
        this.loadConfig();
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    @Override
    public void onDisable() {
        this.saveData();
    }

    public void loadConfig() {
        saveDefaultConfig();
        saveResource("data.yml", false);
        try {
            this.data = new YamlConfiguration();
            data.load(new File(this.getDataFolder() + "/data.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            this.data.save(new File(this.getDataFolder() + "/data.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (!(sender instanceof Player)) {
                sender.sendMessage("不支持控制台");
                return true;
            }
            Player p = (Player) sender;
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                this.loadConfig();
                sender.sendMessage("重载成功");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("save") && sender.isOp()) {
                ItemStack itemInHand = p.getItemInHand();
                if (itemInHand.getType() == Material.AIR) {
                    p.sendMessage(LangUtils.get("lang3"));
                    return true;
                }
                String id = DataManager.add(itemInHand);
                sender.sendMessage(LangUtils.get("lang1") + id);
                this.saveData();
                return true;
            }
            ItemStack itemInHand = p.getItemInHand();
            if (itemInHand.getType() == Material.AIR) {
                p.sendMessage(LangUtils.get("lang3"));
                return true;
            }
            boolean canBreak = ConfigManager.canBreak(itemInHand);
            if (!canBreak) {
                p.sendMessage(LangUtils.get("lang4"));
                return true;
            }
            this.goBreak(p, itemInHand);
        } finally {
            return true;
        }
    }

    public void goBreak(Player p, ItemStack breakItem) {
        BreakAble rule = ConfigManager.searchRule(breakItem);
        PlayerInventory inv = p.getInventory();
        int[] empty = {0};
        inv.forEach(item -> {
            if (item == null || item.getType().equals(Material.AIR) || item.getAmount() <= 0) empty[0]++;
        });
        List<Product> products = rule.getProducts();
        if (empty[0] < products.size()) {
            p.sendMessage(LangUtils.get("lang5") + String.valueOf(empty[0]));
            return;
        }
        breakItem.setType(Material.AIR);
        breakItem.setAmount(0);
        p.setItemInHand(breakItem);

        getLogger().log(Level.INFO, p.getDisplayName() + "请求分解...检测到lore:" + rule.getLore());
        products.forEach(product -> {
            ItemStack itemGet = product.getItem();
            inv.addItem(itemGet);
        });
        if (rule.getMinGold() > 0) {
            int amount = rule.randGold();
            getEconomy().depositPlayer(p, amount);
            p.sendMessage(LangUtils.get("lang6").replace("<amount>", String.valueOf(amount)).replace("<type>", "金币"));
        }
        if (rule.getMinPoint() > 0) {
            int amount = rule.randPoint();
            points.give(p.getUniqueId(), amount);
            p.sendMessage(LangUtils.get("lang6").replace("<amount>", String.valueOf(amount)).replace("<type>", "点券"));
        }
        p.sendMessage(LangUtils.get("lang2"));
    }
}

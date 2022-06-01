package com._0myun.minecraft.randgift;

import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public final class RandGift extends JavaPlugin {
    static RandGift plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = this;
        Bukkit.getPluginManager().registerEvents(new EditerListener(), this);
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            this.reloadConfig();
            p.sendMessage("ok");
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("set") && sender.isOp()) {
            String name = args[1];

            SetterHolder sh = new SetterHolder();
            sh.setName(name);
            Inventory inv = Bukkit.createInventory(sh, 54);
            sh.setInv(inv);

            p.openInventory(inv);
            sender.sendMessage("编辑器打开...");
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("get") && sender.hasPermission("RandGift.get")) {
            String name = args[1];
            List<String> items = getItems(name);
            int amount = getAmount(name);
            if (items == null) {
                p.sendMessage("未定义随机物品...");
                return true;
            }
            if (amount > items.size()) {
                p.sendMessage("Error...获取数量比仓库数量大...");
                return true;
            }
            for (int i = 0; i < amount; i++) {
                String itemStr = items.remove(new Random().nextInt(items.size()));
                try {
                    ItemStack item = StreamSerializer.getDefault().deserializeItemStack(itemStr);
                    p.getInventory().addItem(item);
                    String displayName = item.getItemMeta().getDisplayName();
                    p.sendMessage(String.format(getConfig().getString("lang"), displayName == null ? String.valueOf(item.getType()) : displayName));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                p.updateInventory();
            }
            getLogger().log(Level.INFO, String.format("玩家[%s]成功发送随机物品%s", p.getName(), name));
            return true;
        }
        p.sendMessage("命令错误或权限不足");
        return true;
    }

    public ConfigurationSection get(String name) {
        if (!getConfig().contains("data." + name)) {
            getConfig().createSection("data." + name);
        }
        return getConfig().getConfigurationSection("data." + name);
    }

    public List<String> getItems(String name) {
        return this.get(name).getStringList("items");
    }

    public void setItems(String name, List<String> items) {
        this.get(name).set("items", items);
    }

    public int getAmount(String name) {
        return this.get(name).getInt("amount");
    }

    public void setAmount(String name, int amount) {
        this.get(name).set("amount", amount);
    }
}

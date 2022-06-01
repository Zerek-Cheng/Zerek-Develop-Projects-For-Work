package com._0myun.minecraft.banimodtemer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Level;

public final class Main extends JavaPlugin {
    public static Main INSTANCE;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("ok");
        return true;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        for (int i = 0; i < 3; i++) {
            getLogger().log(Level.WARNING, "§4定制插件就找灵梦云科技0MYUN.COM，联系QQ2025255093");
        }
    }

    public boolean isBan(Block block) {
        if (block == null || block.getType().equals(Material.AIR)) return false;
        return isBan(block.getTypeId() + ":" + block.getData());
    }

    public boolean isBan(ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR)) return false;
        return isBan(item.getTypeId() + ":" + item.getDurability());
    }

    public boolean isBan(String id) {
        if (getConfig().getBoolean("debug")) System.out.println("id = " + id);
        List<String> items = getConfig().getStringList("items");
        String[] split = id.split(":");
        if (items.contains(id)) return true;
        if (items.contains(split[0] + ":*")) return true;
        if (items.contains(split[0])) return true;
        return false;
    }
}

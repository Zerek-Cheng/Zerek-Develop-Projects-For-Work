package com._0myun.minecraft.slotrules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Main extends JavaPlugin implements Listener {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new PlayerChecker(e.getPlayer()).runTaskTimer(this, 20, 20);
    }

    public boolean existRule(int slot) {
        return getConfig().isSet("rules." + slot);
    }

    public ConfigurationSection getSlotConfig(int slot) {
        return getConfig().getConfigurationSection("rules." + slot);
    }

    public int getMode(int slot) {
        return getSlotConfig(slot).getInt("mode");
    }

    public List<String> getList(int slot) {
        return getSlotConfig(slot).getStringList("list");
    }

    public boolean check(int slot, ItemStack itemStack) {
        if (!existRule(slot)) return true;//规则不存在
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return true;
        int mode = getMode(slot);
        List<String> list = getList(slot);
        if (mode == 1) {//白名单
            if (list.contains(String.valueOf(itemStack.getTypeId()) + ":*")
                    || list.contains(String.valueOf(itemStack.getTypeId()) + ":" + String.valueOf(itemStack.getDurability()))
                    || list.contains(String.valueOf(itemStack.getTypeId())))
                return true;
            return false;
        } else if (mode == 2) {//黑名单
            if (list.contains(String.valueOf(itemStack.getTypeId()) + ":*")
                    || list.contains(String.valueOf(itemStack.getTypeId()) + ":" + String.valueOf(itemStack.getDurability()))
                    || list.contains(String.valueOf(itemStack.getTypeId())))
                return false;
            return true;
        }
        return true;
    }
}

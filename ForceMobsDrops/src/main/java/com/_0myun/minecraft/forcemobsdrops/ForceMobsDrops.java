package com._0myun.minecraft.forcemobsdrops;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.Random;

public final class ForceMobsDrops extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        reloadConfig();
        sender.sendMessage("ok");
        return true;
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        List<ItemStack> drops = e.getDrops();
        LivingEntity entity = e.getEntity();
        if (getConfig().getBoolean("debug"))
            System.out.println("entity.getType().toString() = " + entity.getType().toString());
        if (!getConfig().isSet("rules." + entity.getType().toString())) return;

        drops.clear();
        List<Map<?, ?>> maps = getConfig().getMapList("rules."+entity.getType().toString());
        maps.forEach(map -> {
            String id = (String) map.get("id");
            String[] ids = id.split(":");
            int min = (Integer) map.get("min");
            int max = (Integer) map.get("max");
            int amount = new Random().nextInt(max - min) + min;
            drops.add(new ItemStack(Integer.valueOf(ids[0]), amount, Short.valueOf(ids[1])));
        });

    }
}

package com._0myun.minecraft.bangeneratechest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public final class BanGenerateChest extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    getLogger().warning("定制插件Q2025255093 灵梦云团队");
                }
            }
        }.runTaskLater(this, 100l);
        new BukkitRunnable() {

            @Override
            public void run() {
                saveConfig();
            }
        }.runTaskTimerAsynchronously(this, 100l, 100l);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        if (block == null || !block.getType().equals(Material.CHEST)) return;
        if (!getConfig().getStringList("enable").contains(block.getLocation().getWorld().getName())) return;
        if (exist(block.getLocation())) return;
        e.setCancelled(true);
        p.sendMessage(getConfig().getString("lang"));
    }

    @EventHandler
    public void onBlock(BlockPlaceEvent e) {
        Block block = e.getBlock();
        if (exist(block.getLocation())) return;
        add(block.getLocation());
        if (!getConfig().getStringList("enable").contains(block.getLocation().getWorld().getName())) return;
    }

    public void add(Location loc) {
        loc.setPitch(0);
        loc.setYaw(0);
        List<String> data = getConfig().getStringList("data");
        data.add(loc.toString());
        getConfig().set("data", data);
    }

    public boolean exist(Location loc) {
        loc.setPitch(0);
        loc.setYaw(0);
        List<String> data = getConfig().getStringList("data");
        return data.contains(loc.toString());
    }
}

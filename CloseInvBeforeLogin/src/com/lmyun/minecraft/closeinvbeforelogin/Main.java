package com.lmyun.minecraft.closeinvbeforelogin;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {
    private List<UUID> players = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.players.add(e.getPlayer().getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!players.contains(e.getPlayer().getUniqueId())) {
                    this.cancel();
                    return;
                }
                if (AuthMeApi.getInstance().isAuthenticated(e.getPlayer())) {
                    players.remove(e.getPlayer().getUniqueId());
                    this.cancel();
                    return;
                }
                e.getPlayer().closeInventory();
                System.out.println(e.getPlayer().getOpenInventory().getTitle());
            }
        }.runTaskTimer(this, 36, 36);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.players.remove(e.getPlayer().getUniqueId());
    }

}

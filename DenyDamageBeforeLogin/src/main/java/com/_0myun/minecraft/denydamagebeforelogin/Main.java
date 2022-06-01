package com._0myun.minecraft.denydamagebeforelogin;

import com.lenis0012.bukkit.loginsecurity.LoginSecurity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main
        extends JavaPlugin
        implements Listener {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void onPlayerDamagedL(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (!(damager instanceof Player)) {
            return;
        }
        Player p = (Player) damager;
        if (LoginSecurity.getSessionManager().getPlayerSession(p).isLoggedIn()) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDamagedH(EntityDamageByEntityEvent e) {
        onPlayerDamagedL(e);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (LoginSecurity.getSessionManager().getPlayerSession(p).isLoggedIn()) {
            return;
        }
        p.sendMessage(getConfig().getString("lang1"));
        e.setCancelled(true);
    }
}

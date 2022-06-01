package com._0myun.minecraft.denyuseharvestersbox;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.entity.EntityType.MINECART_CHEST;

public final class DenyUseHarvestersBox extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this,this);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onUse(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        Entity clicked = e.getRightClicked();
        if (!clicked.getType().equals(MINECART_CHEST)) return;
        e.setCancelled(true);
        p.sendMessage("禁用矿车箱子");
    }

}

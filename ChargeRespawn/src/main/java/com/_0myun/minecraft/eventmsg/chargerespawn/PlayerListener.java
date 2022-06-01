package com._0myun.minecraft.eventmsg.chargerespawn;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        RespawnManager.death(p);
        p.sendMessage(LangUtils.get("lang1", p));
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (RespawnManager.isDeath(p)){}// p.sendMessage(LangUtils.get("lang2", p));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();
        if (!RespawnManager.isDeath(p)) return;
        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) return;
        e.setCancelled(true);
       // p.sendMessage(LangUtils.get("lang2", p));
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (!RespawnManager.isDeath(p)) return;
        e.setCancelled(true);
       // p.sendMessage(LangUtils.get("lang2", p));
    }

    @EventHandler
    public void onHold(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        if (!RespawnManager.isDeath(p)) return;
        e.setCancelled(true);
      //  p.sendMessage(LangUtils.get("lang2", p));
    }

    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (!RespawnManager.isDeath(p)) return;
        e.setCancelled(true);
     //   p.sendMessage(LangUtils.get("lang2", p));
    }

    @EventHandler
    public void onTp(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (!RespawnManager.isDeath(p)) return;
        e.setCancelled(true);
     //   p.sendMessage(LangUtils.get("lang2", p));
    }

    @EventHandler
    public void onOpenInv(InventoryOpenEvent e) {
        HumanEntity he = e.getPlayer();
        if (!(he instanceof Player)) return;
        Player p = (Player) he;
        if (!RespawnManager.isDeath(p)) return;
        e.setCancelled(true);
     //   p.sendMessage(LangUtils.get("lang2", p));
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!RespawnManager.isDeath(p)) return;
        e.setCancelled(true);
      //  p.sendMessage(LangUtils.get("lang2", p));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamager();
        if (entity instanceof Player) {
            Player p = (Player) entity;
            if (RespawnManager.isDeath(p)) e.setCancelled(true);
        }
        if (damager instanceof Player) {
            Player p = (Player) damager;
            if (RespawnManager.isDeath(p)) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage_Point(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (!(entity instanceof Player)) return;
        Player p = (Player) entity;
        if (RespawnManager.isPointRespawner(p)) {
            e.setCancelled(true);
        }
    }
}

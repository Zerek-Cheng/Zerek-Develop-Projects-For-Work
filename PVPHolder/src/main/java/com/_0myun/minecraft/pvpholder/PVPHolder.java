package com._0myun.minecraft.pvpholder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;

import java.awt.geom.Point2D;
import java.util.*;

public final class PVPHolder extends JavaPlugin implements Listener {
    static List<UUID> pvp = new ArrayList<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            return true;
        }
        Player p = (Player) sender;
        if (pvp.contains(p.getUniqueId())) {
            pvp.remove(p.getUniqueId());
            p.sendMessage("成功开启pvp限制");
            return true;
        }
        pvp.add(p.getUniqueId());
        p.sendMessage("成功关闭pvp限制");
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        Player entity = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        if (inLoc(damager.getLocation()) && inLoc(entity.getLocation())) return;
        if (pvp.containsAll(Arrays.asList(entity.getUniqueId(), damager.getUniqueId()))) return;
        e.setCancelled(true);
        damager.sendMessage(getConfig().getString("lang"));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageN(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        Player entity = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        if (inLoc(damager.getLocation()) && inLoc(entity.getLocation())) return;
        if (pvp.containsAll(Arrays.asList(entity.getUniqueId(), damager.getUniqueId()))) return;
        e.setCancelled(true);
        damager.sendMessage(getConfig().getString("lang"));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamagel(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        Player entity = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        if (inLoc(damager.getLocation()) && inLoc(entity.getLocation())) return;
        if (pvp.containsAll(Arrays.asList(entity.getUniqueId(), damager.getUniqueId()))) return;
        e.setCancelled(true);
        damager.sendMessage(getConfig().getString("lang"));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamagelArrow(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Arrow)) return;
        Player entity = (Player) e.getEntity();
        Arrow arrow = (Arrow) e.getDamager();
        ProjectileSource shooter = arrow.getShooter();
        if (!(shooter instanceof Player)) return;
        Player damager = (Player) shooter;
        if (inLoc(damager.getLocation()) && inLoc(entity.getLocation())) return;
        if (pvp.containsAll(Arrays.asList(entity.getUniqueId(), damager.getUniqueId()))) return;
        e.setCancelled(true);
        damager.sendMessage(getConfig().getString("lang"));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageNArrow(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Arrow)) return;
        Player entity = (Player) e.getEntity();
        Arrow arrow = (Arrow) e.getDamager();
        ProjectileSource shooter = arrow.getShooter();
        if (!(shooter instanceof Player)) return;
        Player damager = (Player) shooter;
        if (inLoc(damager.getLocation()) && inLoc(entity.getLocation())) return;
        if (pvp.containsAll(Arrays.asList(entity.getUniqueId(), damager.getUniqueId()))) return;
        e.setCancelled(true);
        damager.sendMessage(getConfig().getString("lang"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamageHArrow(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Arrow)) return;
        Player entity = (Player) e.getEntity();
        Arrow arrow = (Arrow) e.getDamager();
        ProjectileSource shooter = arrow.getShooter();
        if (!(shooter instanceof Player)) return;
        Player damager = (Player) shooter;
        if (inLoc(damager.getLocation()) && inLoc(entity.getLocation())) return;
        if (pvp.containsAll(Arrays.asList(entity.getUniqueId(), damager.getUniqueId()))) return;
        e.setCancelled(true);
        damager.sendMessage(getConfig().getString("lang"));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        Player p = e.getPlayer();
        if (inLoc(from) && !inLoc(to)) {
            p.sendMessage(getConfig().getString("lang3"));
        }
        if (!inLoc(from) && inLoc(to)) {
            p.sendMessage(getConfig().getString("lang2"));
        }
    }

    public boolean inLoc(Location loc) {
        List<Map<?, ?>> area = getConfig().getMapList("area");
        for (Map config : area) {
            if (!loc.getWorld().getName().equalsIgnoreCase(String.valueOf(config.get("world")))) continue;
            Location from = new Location(Bukkit.getWorld(String.valueOf(config.get("world")))
                    , getMin(Integer.valueOf(String.valueOf(config.get("fx"))), Integer.valueOf(String.valueOf(config.get("tx"))))
                    , loc.getY()
                    , getMin(Integer.valueOf(String.valueOf(config.get("fz"))), Integer.valueOf(String.valueOf(config.get("tz")))));
            Location to = new Location(Bukkit.getWorld(String.valueOf(config.get("world")))
                    , getMan(Integer.valueOf(String.valueOf(config.get("fx"))), Integer.valueOf(String.valueOf(config.get("tx"))))
                    , loc.getY()
                    , getMan(Integer.valueOf(String.valueOf(config.get("fz"))), Integer.valueOf(String.valueOf(config.get("tz")))));
            if (loc.getX() < from.getX() || loc.getX() > to.getX()) return false;
            if (loc.getZ() < from.getZ() || loc.getZ() > to.getZ()) return false;
            return true;
        }
        return false;
    }

    public int getMin(int n1, int n2) {
        return n1 > n2 ? n2 : n1;
    }

    public int getMan(int n1, int n2) {
        return n1 > n2 ? n1 : n2;
    }

}

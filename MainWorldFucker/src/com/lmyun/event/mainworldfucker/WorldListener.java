package com.lmyun.event.mainworldfucker;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class WorldListener implements Listener {
    Main pluign = Main.getPlugin();

    @EventHandler
    public void onTp(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();
        String playerGroup = Main.permission.getPlayerGroups(p)[0];
        if (!to.getWorld().getName().equalsIgnoreCase(this.pluign.pluginConfig.get("Config").getString("generator.name"))//不是世界
                || this.pluign.pluginConfig.get("Config").getStringList("generator.allowGroup").contains(playerGroup)
                || this.pluign.worldLicense.contains(p.getName())) {
            return;
        }
        ItemStack licenseItem = this.getLicenseItem(p);
        if (licenseItem != null) {
            p.sendMessage(LangUtil.getLang("lang2"));
            this.pluign.worldLicense.add(p.getName());
            int first = p.getInventory().first(licenseItem);
            if (licenseItem.getAmount() == 1) {
                licenseItem.setType(Material.AIR);
            } else {
                licenseItem.setAmount(licenseItem.getAmount() - 1);
            }
            p.getInventory().setItem(first, licenseItem);
            Main plugin = this.pluign;
            new BukkitRunnable() {
                @Override
                public void run() {
                    plugin.worldLicense.remove(p.getName());
                    plugin.getLogger().log(Level.INFO, "玩家" + p.getName() + "的异世界进入权限已经移除");
                }
            }.runTaskLater(this.pluign, 24 * 60 * 30);
            return;
        }
        e.setCancelled(true);
        p.performCommand("spawn");
        p.sendMessage(LangUtil.getLang("lang1"));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();
        String playerGroup = Main.permission.getPlayerGroups(p)[0];
        if (!to.getWorld().getName().equalsIgnoreCase(this.pluign.pluginConfig.get("Config").getString("generator.name"))//不是世界
                || this.pluign.pluginConfig.get("Config").getStringList("generator.allowGroup").contains(playerGroup)
                || this.pluign.worldLicense.contains(p.getName())) {
            return;
        }
        e.setCancelled(true);
        p.performCommand("spawn");
        p.sendMessage(LangUtil.getLang("lang1"));
    }


    public ItemStack getLicenseItem(Player p) {
        Iterator<ItemStack> iterator = Arrays.asList(p.getInventory().getContents()).iterator();
        for (; iterator.hasNext(); ) {
            ItemStack item = iterator.next();
            if (item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null) {
                continue;
            }
            List<String> lores = item.getItemMeta().getLore();
            for (String lore : lores) {
                if (lore.contains(this.pluign.pluginConfig.get("Config").getString("generator.joinItemLore"))) {
                    return item;
                }
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        if (from.distance(to) < 0.1) {
            return;
        }
        Player p = e.getPlayer();
        double distance = this.pluign.pluginConfig.get("Config").getDouble("generator.attractDistance");
        List<Entity> entities = p.getNearbyEntities(distance, distance, distance);
        for (Entity entity : entities) {
            if (!(entity instanceof Creature)) {
                continue;
            }
            Creature creature = (Creature) entity;
            creature.setTarget(p);
        }
    }
}
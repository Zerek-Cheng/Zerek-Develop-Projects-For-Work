// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.listener;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import com.github.shawhoi.nybattle.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import com.github.shawhoi.nybattle.util.BossBarUtil;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventHandler;
import com.github.shawhoi.nybattle.playerdata.PlayerData;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.Listener;

public class BlockBreak implements Listener
{
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent e) {
        if (PlayerData.PlayerInGame(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent e) {
        if (PlayerData.PlayerInGame(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (PlayerData.PlayerInGame((Player)e.getWhoClicked()) && e.getInventory() == e.getWhoClicked().getInventory() && e.getSlot() == 38 && e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().equals("??a\u6ed1\u7fd4\u4f1e")) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent e) {
        if (PlayerData.PlayerInGame(e.getPlayer())) {
            BossBarUtil.setPlayerViewBossBar(e.getPlayer());
            final Player p = e.getPlayer();
            if (e.getPlayer().getInventory().getItem(38) != null /* e.getPlayer().getInventory().getItem(38).getType() == Material.ELYTRA */&& distance(e.getPlayer().getLocation(), e.getPlayer().getLocation()) <= 10.0) {
                e.getPlayer().getInventory().setItem(38, new ItemStack(Material.AIR));
                final LivingEntity le = (LivingEntity)p.getWorld().spawnEntity(p.getLocation(), EntityType.CHICKEN);
                le.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999, 1));
                le.setCustomName("NyBattle");
                le.setCustomNameVisible(false);
                le.setPassenger((Entity)p);
                final LivingEntity pc = (LivingEntity)p.getWorld().spawnEntity(p.getLocation().add(0.0, 4.0, 0.0), EntityType.CHICKEN);
                pc.setCustomName("NyBattle");
                pc.setCustomNameVisible(false);
                pc.setLeashHolder((Entity)p);
                new BukkitRunnable() {
                    boolean has = false;
                    
                    public void run() {
                        if (le == null || pc == null) {
                            if (le == null && pc != null) {
                                pc.remove();
                            }
                            else if (le != null && pc == null) {
                                le.remove();
                            }
                            this.cancel();
                            return;
                        }
                        if (BlockBreak.distance(p.getLocation(), p.getLocation()) <= 1.0) {
                            pc.setLeashHolder((Entity)null);
                            pc.remove();
                            le.setPassenger((Entity)null);
                            le.remove();
                        }
                    }
                }.runTaskTimer((Plugin)Main.getInstance(), 1L, 5L);
            }
        }
    }
    
    public static double distance(final Location fl, final Location loc) {
        double distance = 0.0;
        for (int i = loc.getBlockY(); i > 0; --i) {
            loc.setY((double)i);
            if (loc.getBlock().getType() != null && loc.getBlock().getType() != Material.AIR) {
                distance = fl.distance(loc);
                break;
            }
        }
        return distance;
    }
}

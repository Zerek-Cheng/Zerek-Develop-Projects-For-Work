package com._0myun.minecraft.peacewarrior.listeners.battle;

import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.utils.LocationUtil;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class PlayerWeakListener implements Listener {
    HashMap<UUID, Long> finish = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        try {
            if (!DBManager.playerDataDao.queryForUUID(p.getUniqueId()).getStat().equals(PlayerData.Stat.PLAY)) return;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        double health = p.getHealth() - e.getDamage();
        if (health > 4d) return;
        if (health <= 0) return;
        finish.put(p.getUniqueId(), System.currentTimeMillis() + 20 * 2000);
        p.sendMessage(R.INSTANCE.lang("lang20"));

        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 4));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 20, 3));
        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 20, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 2));

        new BukkitRunnable() {

            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    if (p.getHealth() > 4) break;
                    for (Location loc : LocationUtil.findOval(p.getLocation(), 2d, 2d)) {
                        loc.getWorld().playEffect(loc, Effect.getByName(R.INSTANCE.getConfig().getString("effect-weak")), 0);
                    }
                    try {
                        Thread.sleep(999);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(R.INSTANCE);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!finish.containsKey(p.getUniqueId())) return;
        if (finish.get(p.getUniqueId()) < System.currentTimeMillis()) {
            finish.remove(p.getUniqueId());
            return;
        }
        e.setCancelled(true);
        p.sendMessage(R.INSTANCE.lang("lang21"));
    }
}

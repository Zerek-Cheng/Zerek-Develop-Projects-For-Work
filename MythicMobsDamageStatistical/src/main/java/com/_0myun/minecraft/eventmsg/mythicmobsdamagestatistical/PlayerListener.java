package com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.logging.Level;

public class PlayerListener implements Listener {
    Main plugin = Main.getPlugin();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamager();
        int damage = Double.valueOf(e.getDamage()).intValue();
        if (!(damager instanceof Player)) return;
        if (!this.plugin.getMmApi().isMythicMob(entity)) return;
        DataManager.record(damager.getUniqueId(), damage);
        if (plugin.getConfig().getBoolean("debug"))
            plugin.getLogger().log(Level.CONFIG, damager.getUniqueId() + "记录" + damage);
    }
}

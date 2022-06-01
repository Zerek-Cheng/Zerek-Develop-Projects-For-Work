package com.lmyun.event.skill;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AssaultTask
        extends BukkitRunnable {
    Assault plugin;
    Player player;
    double damage;
    int remaining;
    List<UUID> alreadyDamageEntities = new ArrayList<>();

    public AssaultTask(Assault plugin, Player player, double damage, int remaining) {
        this.plugin = plugin;
        this.player = player;
        this.damage = damage;
        this.remaining = remaining;
    }

    private AssaultTask(Assault plugin, Player player, double damage, int remaining, List<UUID> alreadyDamageEntities) {
        this.plugin = plugin;
        this.player = player;
        this.damage = damage;
        this.remaining = remaining;
        this.alreadyDamageEntities = alreadyDamageEntities;
    }

    public void run() {
        for (Entity entity : this.player.getNearbyEntities(this.plugin.config.getDouble("assaultRange"), this.plugin.config.getDouble("assaultRange"), this.plugin.config.getDouble("assaultRange"))) {
            if (!(entity instanceof LivingEntity) || this.alreadyDamageEntities.contains(entity.getUniqueId())) {
                continue;
            }
            this.alreadyDamageEntities.add(entity.getUniqueId());
            ((LivingEntity) entity).damage(this.damage, this.player);
        }
        if (this.remaining > 0) {
            new AssaultTask(this.plugin, this.player, this.damage, this.remaining - 1, this.alreadyDamageEntities).runTaskLater(this.plugin, this.plugin.config.getInt("assaultDamageTick"));
        } else {
            this.plugin.assaultList.remove(this.player.getName());
        }
    }
}

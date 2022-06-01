package com._0myun.minecraft.eventmsg.mmrespawncooldowntitle;

import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.spawning.spawners.MythicSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin implements Listener {

    private HashMap<String, YamlConfiguration> pluginConfig = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.loadConfig();
        sender.sendMessage("重载ok");
        return true;
    }

    @Override
    public void onEnable() {
        this.loadConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                getLogger().log(Level.WARNING, "定制插件就找灵梦云科技0MYUN.COM。联系Q903115511,联系不上就联系Q2025255093");
            }
        }.runTaskTimerAsynchronously(this, 24 * 5, 24 * 60 * 60);
    }

    public void loadConfig() {
        saveResource("Config.yml", false);
        this.pluginConfig.put("Config", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Config.yml")));
    }

    @EventHandler
    public void onMobsDeath(EntityDeathEvent e) {
        BukkitAPIHelper api = new BukkitAPIHelper();
        LivingEntity entity = e.getEntity();
        if (!api.isMythicMob(entity)) {
            return;
        }
        ActiveMob mob = api.getMythicMobInstance(entity);
        MythicSpawner spawner = mob.getSpawner();
        if (!spawner.isOnCooldown()) {
            return;
        }
        AbstractLocation mobLoc = mob.getLocation();
        ArmorStand armorStand = (ArmorStand) Bukkit.getWorld(mobLoc.getWorld().getName()).spawnEntity(new Location(Bukkit.getWorld(mobLoc.getWorld().getName())
                , mobLoc.getX()
                , mobLoc.getY() + 2
                , mobLoc.getZ()), EntityType.ARMOR_STAND);
        armorStand.setCustomName("wait.....");
        this.setTitleEntitiesInfo(armorStand);
        new BukkitRunnable() {
            UUID titleUUID = armorStand.getUniqueId();

            @Override
            public void run() {
                if (!spawner.isOnCooldown()) {
                    Entity entityT = Bukkit.getEntity(this.titleUUID);
                    List<Entity> nearbyEntities = entityT.getNearbyEntities(10, 10, 10);
                    for (Entity p : nearbyEntities) {
                        if (p instanceof Player) {
                            ((Player) p).sendTitle("怪物已经复活！", "....");
                        }
                    }
                    entityT.remove();
                    this.cancel();
                    return;
                }
                armorStand.setCustomName(pluginConfig.get("Config").getString("title").replace("<time>", String.valueOf(spawner.getRemainingCooldownSeconds())));
            }
        }.runTaskTimerAsynchronously(this, 12, 12);
    }

    private void setTitleEntitiesInfo(ArmorStand as) {
        as.setCanPickupItems(false);
        as.setVisible(false);
        as.setInvulnerable(true);//无懈可击
        as.setGravity(false);//反重力
        as.setCustomNameVisible(true);//名称显示
        as.setMarker(true);
    }
}

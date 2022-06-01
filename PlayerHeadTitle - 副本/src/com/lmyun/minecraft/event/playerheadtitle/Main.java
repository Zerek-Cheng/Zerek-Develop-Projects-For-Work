package com.lmyun.minecraft.event.playerheadtitle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    protected HashMap<String, FileConfiguration> pluginConfig = new HashMap<>();
    protected HashMap<UUID, TitleEntitiesManager> titleEntities = new HashMap<>();

    @Override
    public void onEnable() {
        this.loadConfig();
        Bukkit.getPluginManager().registerEvents(new Listener(this), this);
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        spawnTitleEntities(p.getUniqueId());
        return true;
    }

    public void removeTitleEntities(UUID player) {
        TitleEntitiesManager titleManager = this.titleEntities.get(player);
        if (titleManager != null) {
            Entity titleEntity1 = Bukkit.getEntity(titleManager.l1);
            titleEntity1.remove();
            Entity titleEntity2 = Bukkit.getEntity(titleManager.l2);
            titleEntity2.remove();
            Entity titleEntity3 = Bukkit.getEntity(titleManager.l3);
            titleEntity3.remove();
        }
        this.titleEntities.remove(player);
    }

    public void spawnTitleEntities(UUID player) {
        Player p = Bukkit.getPlayer(player);
        World pWorld = p.getWorld();
        Location loc = p.getLocation();
        List<String> titles = this.pluginConfig.get("Config").getStringList("title");
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            title = title.replace("%player%", p.getDisplayName());
            title = title.replace("%time%", new Date().toString());
            titles.set(i, title);
        }
        TitleEntitiesManager titleManager = new TitleEntitiesManager();
        ArmorStand asLine1 = (ArmorStand) pWorld.spawnEntity(loc, EntityType.ARMOR_STAND);
        ArmorStand asLine2 = (ArmorStand) pWorld.spawnEntity(loc, EntityType.ARMOR_STAND);
        ArmorStand asLine3 = (ArmorStand) pWorld.spawnEntity(loc, EntityType.ARMOR_STAND);
        this.setTitleEntitiesInfo(asLine1);//设置标准属性
        this.setTitleEntitiesInfo(asLine2);
        this.setTitleEntitiesInfo(asLine3);
        asLine1.setCustomName(titles.get(0));
        asLine2.setCustomName(titles.get(1));
        asLine3.setCustomName(titles.get(2));
        titleManager.setL1(asLine1);
        titleManager.setL2(asLine2);
        titleManager.setL3(asLine3);
        titleManager.tp(p);
        this.titleEntities.put(p.getUniqueId(), titleManager);
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

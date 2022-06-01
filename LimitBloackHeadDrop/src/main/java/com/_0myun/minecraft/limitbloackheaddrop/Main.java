package com._0myun.minecraft.limitbloackheaddrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    public static Plugin INSTANCE = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        new Saver().runTaskTimerAsynchronously(this, 30 * 20, 30 * 20);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        LivingEntity entity = e.getEntity();
        if (!(e.getEntity() instanceof Skeleton)) return;
        Skeleton skeleton = (Skeleton) e.getEntity();
        CreatureSpawnEvent.SpawnReason reason = e.getSpawnReason();
        //!skeleton.getSkeletonType().equals(Skeleton.SkeletonType.NORMAL)//不是普通的
        if ((reason.equals(CreatureSpawnEvent.SpawnReason.SPAWNER) || reason.equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)))//是主动生成
            return;
        addSpawn(entity.getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Skeleton)) return;
        Skeleton skeleton = (Skeleton) e.getEntity();
        if (skeleton.getSkeletonType().equals(Skeleton.SkeletonType.NORMAL)) return;

        LivingEntity entity = e.getEntity();
        List<ItemStack> drops = e.getDrops();
        drops.removeIf(itemStack -> itemStack.getType().equals(Material.SKULL_ITEM));
        drops.add(new ItemStack(Material.SKULL_ITEM, 1, (short) 1));
        boolean has = false;
        for (int i = 0; i < drops.size(); i++) {
            ItemStack item = drops.get(i);
            if (item.getTypeId() == 397 || item.getTypeId() == 144 || item.getType().equals(Material.SKULL_ITEM))
                has = true;
        }
        if (!has) return;
        Player killer = entity.getKiller();

        if (isSpawn(entity.getUniqueId())) {
            if (Math.random() < getWildDropRand()) return;
            e.getDrops().clear();
            killer.sendMessage(getConfig().getString("lang1"));
            return;
        }
        removeSpawn(entity.getUniqueId());
        if (getToday() >= getSpawnBoxDropCount()) {
            e.getDrops().clear();
            killer.sendMessage(getConfig().getString("lang2"));
            return;
        }
        if (Math.random() < getSpawnBoxDropRand()) {
            addToday();
            return;
        }
        e.getDrops().clear();
        killer.sendMessage(getConfig().getString("lang1"));
    }

    public int getSpawnBoxDropCount() {
        return getConfig().getInt("SpawnBoxDropCount");
    }

    public double getSpawnBoxDropRand() {
        return getConfig().getDouble("SpawnBoxDropRand");
    }

    public double getWildDropRand() {
        return getConfig().getDouble("WildDropRand");
    }

    public String getDay() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public int getToday() {
        if (!getConfig().isSet("data.day." + getDay())) {
            getConfig().set("data.day." + getDay(), 0);
        }
        return getConfig().getInt("data.day." + getDay());
    }

    public void addToday() {
        getConfig().set("data.day." + getDay(), getToday() + 1);
    }

    public List<String> getSpawn() {
        return getConfig().getStringList("data.spawn");
    }

    public void addSpawn(UUID uuid) {
        List<String> spawn = getSpawn();
        spawn.add(uuid.toString());
        getConfig().set("data.spawn", spawn);
    }

    public void removeSpawn(UUID uuid) {
        List<String> spawn = getSpawn();
        spawn.remove(uuid.toString());
        getConfig().set("data.spawn", spawn);
    }

    public boolean isSpawn(UUID uuid) {
        return getSpawn().contains(uuid.toString());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("reload ok");
        return true;
    }
}

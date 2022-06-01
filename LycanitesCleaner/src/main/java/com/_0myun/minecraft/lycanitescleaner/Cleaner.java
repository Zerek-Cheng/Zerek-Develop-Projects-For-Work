package com._0myun.minecraft.lycanitescleaner;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.logging.Level;

public class Cleaner extends BukkitRunnable {
    public static HashMap<World, BukkitTask> tasks = new HashMap<>();

    @Override
    public void run() {
        Bukkit.getWorlds().forEach(world -> {
            Main.INSTANCE.getLogger().log(Level.INFO,"开始清理恐怖生物...");
            if (tasks.containsKey(world)) {
                tasks.get(world).cancel();
                tasks.remove(world);
            }
            BukkitTask task = new WordCleaner(world).runTaskAsynchronously(Main.INSTANCE);
            tasks.put(world, task);
        });
    }
}

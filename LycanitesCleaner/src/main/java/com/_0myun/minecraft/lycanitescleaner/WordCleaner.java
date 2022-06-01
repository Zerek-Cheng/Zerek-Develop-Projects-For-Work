package com._0myun.minecraft.lycanitescleaner;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static java.util.logging.Level.INFO;

public class WordCleaner extends BukkitRunnable {
    World world;

    public WordCleaner(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        int[] amount = {0};
        Main.INSTANCE.reloadConfig();
        Main.INSTANCE.getLogger().log(INFO, "世界" + world.getName() + "清理线程启动...");
        List<String> list = Main.INSTANCE.getConfig().getStringList("clean");
        world.getLivingEntities().forEach(entity -> {
            if (list.contains(entity.getType().toString())) return;
            amount[0]++;
            entity.remove();
        });
        Main.INSTANCE.getLogger().log(INFO, "世界" + world.getName() + "清理完成...共清理" + amount[0] + "个实体");
        Cleaner.tasks.remove(world);
    }
}

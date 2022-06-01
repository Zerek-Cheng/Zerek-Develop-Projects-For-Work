package com._0myun.minecraft.worldreward;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Refresher extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getWorlds().forEach(world -> {
            Long refreshTime = Main.INSTANCE.getRefreshTime(world);
            if (refreshTime == null || refreshTime == 0) return;
            Long next = Main.INSTANCE.getNextRefreshTime(world);
            if (System.currentTimeMillis() < next) return;

            Main.INSTANCE.setNextRefreshTime(world);
            Main.INSTANCE.refreshBox(world);
            System.out.println("世界奖励箱子刷新" + world.getName());
        });
    }
}

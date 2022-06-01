package com._0myun.minecraft.denyitemsinworld;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Checker extends BukkitRunnable {

    @Override
    public void run() {
        try {

            Bukkit.getOnlinePlayers().forEach(p -> DenyItemsInWorld.plugin.check(p));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

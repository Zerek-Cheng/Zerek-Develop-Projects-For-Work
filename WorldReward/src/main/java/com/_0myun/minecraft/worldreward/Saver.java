package com._0myun.minecraft.worldreward;

import org.bukkit.scheduler.BukkitRunnable;

public class Saver extends BukkitRunnable {
    @Override
    public void run() {
        Main.INSTANCE.saveConfig();
    }
}

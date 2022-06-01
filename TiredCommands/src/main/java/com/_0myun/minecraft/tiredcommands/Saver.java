package com._0myun.minecraft.tiredcommands;

import org.bukkit.scheduler.BukkitRunnable;

public class Saver extends BukkitRunnable {
    @Override
    public void run() {
        Main.plugin.saveConfig();
    }
}

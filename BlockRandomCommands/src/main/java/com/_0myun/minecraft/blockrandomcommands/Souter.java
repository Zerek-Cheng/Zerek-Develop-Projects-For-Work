package com._0myun.minecraft.blockrandomcommands;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class Souter extends BukkitRunnable {

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            Main.INSTANCE.getLogger().log(Level.INFO, "定制插件就找灵梦云科技0MYUN.COM,Q2025255093");
        }
    }
}

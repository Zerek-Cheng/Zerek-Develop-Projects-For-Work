package com._0myun.minecraft.tiredcommands;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class souter extends BukkitRunnable {
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            Main.plugin.getLogger().log(Level.WARNING,"§e定制插件就找灵梦云科技0MYUN.COM,QQ2025255093");
        }
    }
}

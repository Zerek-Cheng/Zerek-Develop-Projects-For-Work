package com._0myun.minecraft.catserver.pokemoneffectbroadcast;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class outer extends BukkitRunnable {
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            Main.INSTANCE.getLogger().log(Level.WARNING,"§b定制插件就找灵梦云0MYUN.COM,q2025255093");
        }
    }
}

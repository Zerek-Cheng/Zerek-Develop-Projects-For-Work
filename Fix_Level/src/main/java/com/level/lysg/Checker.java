package com.level.lysg;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Checker extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> Main.INSTANCE.checkPlayer(p));
    }
}

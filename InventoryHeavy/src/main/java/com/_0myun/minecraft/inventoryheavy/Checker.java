package com._0myun.minecraft.inventoryheavy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Checker extends BukkitRunnable {
    R plugin = null;
    static long time = 0;

    public Checker(R r) {
        plugin = r;
    }

    @Override
    public void run() {
        time++;
        for (Player p : Bukkit.getOnlinePlayers()) {
            double speed = 0.3 - (0.2 * plugin.getPrecent(p));
            if (speed < 0) speed = 0;
            p.setWalkSpeed((float) speed);
            if (time % 60 == 0 && plugin.getPrecent(p) >= 0.4f) {
                p.sendMessage(String.format(plugin.getConfig().getString("lang1"), plugin.getHeavy(p), plugin.getMaxHeavy(p)));
            }
        }
    }
}

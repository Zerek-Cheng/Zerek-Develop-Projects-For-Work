package com._0myun.minecraft.payforteleporttime;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerChecker extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            long timeOut = Main.plugin.getTimeOut(p.getUniqueId());
            if (timeOut <= 0) return;
            if (timeOut > System.currentTimeMillis()) return;
            p.sendMessage(Main.plugin.getConfig().getString("lang2"));
            p.teleport(Main.plugin.getDefaultLocation());
            Main.plugin.delTimeOut(p.getUniqueId());
        });
    }
}

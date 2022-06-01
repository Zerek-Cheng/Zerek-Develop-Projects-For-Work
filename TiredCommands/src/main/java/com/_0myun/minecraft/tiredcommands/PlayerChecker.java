package com._0myun.minecraft.tiredcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerChecker extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PhysicalManager.refresh(p.getUniqueId());
        }
    }
}

package com._0myun.minecraft.bossshoppro.limiteuses.regularuse;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Refresher extends BukkitRunnable {
    @Override
    public void run() {
        try {
            RuleManager.getConfig().forEach(rule -> {
                String[] split = rule.split(":");
                if (split.length < 3) return;
                Bukkit.getOnlinePlayers().forEach(p -> {
                    String name = p.getName();
                    long playerUseTime = DataManager.getPlayerUseTime(name, split[0], split[1]);
                    long now = System.currentTimeMillis();
                    if (now > playerUseTime) DataManager.resetPlayerUseTime(name, split[0], split[1]);
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        RegularUse.getPlugin().saveData();
    }
}

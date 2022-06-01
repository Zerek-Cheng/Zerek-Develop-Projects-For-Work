package com._0myun.minecraft.dailyquest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class TimeOutChecker extends BukkitRunnable {
    @Override
    public void run() {
        try {
            FileConfiguration config = DailyQuests.plugin.getConfig();
            long timeOut = config.getLong("time") * 1000;
            ConfigurationSection time = config.getConfigurationSection("data.time");
            time.getKeys(false).forEach(key -> {
                long timeStart = time.getLong(key);
                if (timeStart == 0) return;
                if (System.currentTimeMillis() > (timeStart + timeOut)) {
                    config.set("data.time." + key, null);
                    config.set("data.quest." + key, null);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(key));
                    DailyQuests.plugin.setTodayFail(UUID.fromString(key), DailyQuests.plugin.getTodayFail(UUID.fromString(key)) + 1);
                    if (offlinePlayer.isOnline()) {
                        Bukkit.getPlayer(UUID.fromString(key)).sendMessage(DailyQuests.plugin.getLang("lang2"));
                    }
                    DailyQuests.plugin.saveConfig();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

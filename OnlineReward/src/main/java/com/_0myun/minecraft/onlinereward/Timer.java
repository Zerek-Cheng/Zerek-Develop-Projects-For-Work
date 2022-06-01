package com._0myun.minecraft.onlinereward;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Timer extends BukkitRunnable {
    @Override
    public void run() {
        Main.INSTANCE.refreshData();
        Bukkit.getOnlinePlayers().forEach(p -> Main.INSTANCE.addTime(p.getUniqueId()));

        Main.INSTANCE.getConfig().getConfigurationSection("every").getKeys(false).forEach(name -> {
            ConfigurationSection config = Main.INSTANCE.getConfig().getConfigurationSection("every." + name);
            Bukkit.getOnlinePlayers().forEach(p -> {
                boolean once = config.getBoolean("once");
                int time = config.getInt("time");
                List<String> commands = config.getStringList("commands");
                if (once && Main.INSTANCE.hasGeted(p.getUniqueId(), name)) return;
                if (!Main.INSTANCE.canNext(p.getUniqueId(), name, time)) return;
                Main.INSTANCE.setGeted(p.getUniqueId(), name);
                Main.INSTANCE.setLast(p.getUniqueId(), name);
                boolean op = p.isOp();
                try {
                    if (!op) p.setOp(true);
                    commands.forEach(command -> p.performCommand(command.replace("<player>", p.getName())));
                } finally {
                    if (!op) p.setOp(false);
                }
                Main.INSTANCE.saveConfig();
            });
        });
    }
}

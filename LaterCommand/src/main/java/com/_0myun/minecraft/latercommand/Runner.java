package com._0myun.minecraft.latercommand;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.List;

public class Runner extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            List<String> data = LaterCommand.INSTANCE.getPlayerLaterData(p.getUniqueId());
            if (data == null || data.size() == 0) return;
            data.forEach(later -> {
                if (!later.contains(":")) return;
                String[] split = later.split(":");
                Long time = Long.valueOf(split[0]);
                String command = LaterCommand.INSTANCE.getLaterCommand(split[1]);
                if (time == null || command == null) return;
                if (new Date().before(new Date(time))) return;
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("<player>", p.getName()));
                LaterCommand.INSTANCE.removePlayerLaterData(p.getUniqueId(), later);
                LaterCommand.INSTANCE.saveConfig();
            });
        });
    }
}

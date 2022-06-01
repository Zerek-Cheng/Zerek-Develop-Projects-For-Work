package com._0myun.minecraft.playerblockexper;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerTask extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            Location loc = p.getLocation().clone();
            loc.setY(loc.getY() - 1);
            Block block = loc.getBlock();
            if (!Main.plugin.isBlock(block)) return;
            Integer sec = Main.sec.get(p.getUniqueId());
            if (sec == null) sec = 0;
            sec++;
            if (sec >= 60) {
                Main.plugin.giveExp(p);
                Main.plugin.addTodayMin(p.getUniqueId());
                Main.plugin.saveConfig();
                sec = 0;
            }
            Main.sec.put(p.getUniqueId(), sec);

        });
    }
}

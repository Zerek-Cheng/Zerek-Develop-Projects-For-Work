package com._0myun.minecraft.blockrandomcommands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public class PlayerListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Location loc = b.getLocation();
        if (SqliteManager.exist(loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ()))
            return;
        ConfigurationSection config = Main.INSTANCE.getConfig().getConfigurationSection("rules." + b.getTypeId() + ":" + (int) b.getData());
        if (config == null) config = Main.INSTANCE.getConfig().getConfigurationSection("rules." + b.getTypeId());
        if (config == null) return;
        double rand = config.getDouble("rand");
        List<String> cmds = config.getStringList("cmds");
        if (Math.random() > rand) return;
        boolean op = p.isOp();
        try {
            if (!op) p.setOp(true);
            for (int i = 0; i < cmds.size(); i++) {
                String cmd = cmds.get(i);
                p.performCommand(cmd.replace("<player>", p.getName()));
            }

        } finally {
            if (!op) p.setOp(false);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) return;
        Location loc = e.getBlock().getLocation();
        SqliteManager.add(loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ());
    }
}

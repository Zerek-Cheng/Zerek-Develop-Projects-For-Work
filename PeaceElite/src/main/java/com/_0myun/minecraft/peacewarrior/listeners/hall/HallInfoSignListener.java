package com._0myun.minecraft.peacewarrior.listeners.hall;

import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.MapManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Sign;
import com.j256.ormlite.stmt.DeleteBuilder;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class HallInfoSignListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreateSign(SignChangeEvent e) {
        Player p = e.getPlayer();
        List<String> lines = Arrays.asList(e.getLines());
        if (lines.size() < 2) return;
        if (!lines.get(0).equalsIgnoreCase(R.INSTANCE.getConfig().getString("sign"))) return;
        String map = lines.get(1);
        if (MapManager.getMapByName(map) == null) {
            p.sendMessage(R.INSTANCE.lang("lang2"));
            e.setCancelled(true);
            return;
        }
        Location loc = e.getBlock().getLocation();
        Sign sign = Sign.builder().map(map).world(loc.getWorld().getName()).x(loc.getBlockX()).y(loc.getBlockY()).z(loc.getBlockZ()).build();

        try {
            DeleteBuilder<Sign, Integer> builder = DBManager.signDao.deleteBuilder();
            builder.where().eq("world", loc.getWorld().getName())
                    .and().eq("x", loc.getBlockX())
                    .and().eq("y", loc.getBlockY())
                    .and().eq("z", loc.getBlockZ());
            builder.delete();
            DBManager.signDao.createIfNotExists(sign);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        p.sendMessage(R.INSTANCE.lang("lang0"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        Location loc = block.getLocation();
        try {
            DeleteBuilder<Sign, Integer> builder = DBManager.signDao.deleteBuilder();
            builder.where().eq("world", loc.getWorld().getName())
                    .and().eq("x", loc.getBlockX())
                    .and().eq("y", loc.getBlockY())
                    .and().eq("z", loc.getBlockZ());
            builder.delete();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

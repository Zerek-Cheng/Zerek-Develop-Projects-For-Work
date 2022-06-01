package com._0myun.minecraft.peacewarrior.listeners.hall;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.Sign;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleJoinEvent;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.SQLException;
import java.util.List;

public class HallClickSignListener implements Listener {
    @EventHandler
    public void onClickSign(PlayerInteractEvent e) throws SQLException {
        Block block = e.getClickedBlock();
        if (block == null) return;
        Location loc = block.getLocation();
        List<Sign> signs = DBManager.signDao.queryBuilder().where()
                .eq("world", loc.getWorld().getName()).and()
                .eq("x", loc.getBlockX()).and()
                .eq("y", loc.getBlockY()).and()
                .eq("z", loc.getBlockZ()).query();
        if (signs.isEmpty()) return;
        Battle battle = DBManager.battleDao.queryForMap(signs.get(0).getMap());

        var event = new PWBattleJoinEvent(e.getPlayer(), battle);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        Player p = e.getPlayer();
        BattleManager.join(p, battle);
    }
}

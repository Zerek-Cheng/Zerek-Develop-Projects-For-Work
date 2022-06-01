package com._0myun.minecraft.peacewarrior.listeners.sys;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.Position;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class SysPlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        try {
            PlayerData data = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
            if (data != null) return;
            data = DBManager.playerDataDao.createIfNotExists(PlayerData.builder().uuid(p.getUniqueId().toString()).player(p.getName()).build());
            R.INSTANCE.getLogger().info("玩家" + data.getPlayer() + "数据库初始化");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        try {
            PlayerData sqlData = DBManager.playerDataDao.queryForUUID(e.getPlayer().getUniqueId());
            if (sqlData.getStat().equals(PlayerData.Stat.NONE) || sqlData.getStat().equals(PlayerData.Stat.BANNED))
                return;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        BattleManager.quit(e.getPlayer());
        R.INSTANCE.getLogger().info("玩家" + e.getPlayer().getName() + "退出游戏.强制退出战斗!");

        e.getPlayer().teleport(((Position) R.INSTANCE.getConfig().get("hall")).toBukkitLocation());
    }
}

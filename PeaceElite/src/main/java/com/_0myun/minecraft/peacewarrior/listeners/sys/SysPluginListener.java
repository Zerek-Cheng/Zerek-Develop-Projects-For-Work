package com._0myun.minecraft.peacewarrior.listeners.sys;

import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.MapManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.events.PWPluginInitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class SysPluginListener implements Listener {
    @EventHandler
    public void onInit(PWPluginInitEvent e) {
        try {
            for (PlayerData pD : DBManager.playerDataDao.queryForStat(PlayerData.Stat.WAIT, PlayerData.Stat.PLAY)) {
                pD.setStat(PlayerData.Stat.NONE);
                pD.setMap(null);
                DBManager.playerDataDao.update(pD);
                R.INSTANCE.getLogger().info("玩家" + pD.getPlayer() + "状态异常已修复");
            }

            for (Battle battle : DBManager.battleDao.queryForStat(Battle.Stat.READY, Battle.Stat.PLAY)) {
                battle.setStat(Battle.Stat.WAIT);
                battle.setProgress(0);
                battle.setAlive(0);
                battle.setPlayer_amount(0);
                DBManager.battleDao.update(battle);
                R.INSTANCE.getLogger().info("地图" + battle.getMap() + "状态异常已修复");
            }

            for (BattleMap map : MapManager.maps) {
                Battle battle = DBManager.battleDao.queryForMap(map.getName());
                if (battle != null) continue;
                battle = Battle.builder().map(map.getName()).stat(Battle.Stat.WAIT).progress(0).build();
                DBManager.battleDao.createIfNotExists(battle);
                R.INSTANCE.getLogger().info("地图" + map.getName() + "初始化第一场");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

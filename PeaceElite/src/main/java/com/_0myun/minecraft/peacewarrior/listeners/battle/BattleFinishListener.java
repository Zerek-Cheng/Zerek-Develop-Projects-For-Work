package com._0myun.minecraft.peacewarrior.listeners.battle;

import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleFinishEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class BattleFinishListener implements Listener {
    @EventHandler
    public synchronized void onFinish(PWBattleFinishEvent e) {
        PlayerOpenChestListener.clear(e.getBattle().getMap());

        Battle oldBattle = e.getBattle();
        String map = oldBattle.getMap();
        try {
            Battle battle = DBManager.battleDao.queryForMap(map, Battle.Stat.WAIT, Battle.Stat.READY, Battle.Stat.STOP);
            if (battle != null) return;
            battle = Battle.builder().map(map).stat(Battle.Stat.WAIT).progress(0).build();
            DBManager.battleDao.createIfNotExists(battle);
            R.INSTANCE.getLogger().info("地图" + map + "初始化下一场");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

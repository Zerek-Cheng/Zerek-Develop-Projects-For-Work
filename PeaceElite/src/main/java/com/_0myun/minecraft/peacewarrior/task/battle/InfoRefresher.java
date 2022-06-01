package com._0myun.minecraft.peacewarrior.task.battle;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.List;

public class InfoRefresher extends BukkitRunnable {
    @Override
    public void run() {
        try {
            List<Battle> battles = DBManager.battleDao.queryForStat(Battle.Stat.WAIT, Battle.Stat.READY, Battle.Stat.PLAY);
            for (Battle battle : battles) {
                BattleManager.updateBattlePlayer(battle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

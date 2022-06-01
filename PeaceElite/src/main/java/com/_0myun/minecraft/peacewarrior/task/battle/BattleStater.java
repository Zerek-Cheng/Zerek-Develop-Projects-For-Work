package com._0myun.minecraft.peacewarrior.task.battle;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.MapManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleReadyEvent;
import com._0myun.minecraft.peacewarrior.utils.Replacer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class BattleStater extends BukkitRunnable {
    HashMap<String, Long> ready = new HashMap<>();

    @Override
    public synchronized void run() {
        try {
            List<Battle> battles = DBManager.battleDao.queryForStat(Battle.Stat.WAIT, Battle.Stat.READY);
            for (Battle battle : battles) {
                BattleMap map = MapManager.getMapByName(battle.getMap());
                if (battle.getStat().equals(Battle.Stat.WAIT)) {
                    if (battle.getPlayer_amount() < map.getPlayer_min()) continue;
                    battle.setStat(Battle.Stat.READY);
                    DBManager.battleDao.update(battle);
                    String lang = Replacer.replace(R.INSTANCE.lang("lang16"), battle);
                    lang = Replacer.replace(lang, map);
                    Bukkit.broadcastMessage(lang);
                    battle.setStat(Battle.Stat.READY);
                    DBManager.battleDao.update(battle);
                    Bukkit.getPluginManager().callEvent(new PWBattleReadyEvent(battle));
                } else if (battle.getStat().equals(Battle.Stat.READY)) {
                    if (!ready.containsKey(map.getName()))
                        ready.put(battle.getMap(), System.currentTimeMillis() + (10 * 1000l));
                    if (System.currentTimeMillis() < ready.get(battle.getMap())) continue;
                    String lang = Replacer.replace(R.INSTANCE.lang("lang17"), battle);
                    lang = Replacer.replace(lang, map);
                    Bukkit.broadcastMessage(lang);
                    ready.remove(battle.getMap());
                    BattleManager.start(battle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

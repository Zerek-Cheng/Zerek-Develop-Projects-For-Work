package com._0myun.minecraft.peacewarrior.task.battle;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.MapManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.utils.Area;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class AreaDamageTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            new Thread(() -> {
                try {
                    Location loc = p.getLocation();
                    PlayerData playerData = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
                    if (!playerData.getStat().equals(PlayerData.Stat.PLAY)) return;
                    BattleMap map = MapManager.getMapByName(playerData.getMap());
                    Battle battle = DBManager.battleDao.queryForMap(playerData.getMap());
                    if (!battle.getStat().equals(Battle.Stat.PLAY)) return;

                    Map config = (Map) map.getNarrow().get(battle.getProgress());
                    if (config == null || config.keySet().isEmpty()) return;
                    int damage = Integer.valueOf(String.valueOf(config.get("damage")));
                    Area area = BattleManager.areas.get(playerData.getMap());
/*                    System.out.println(battle.getProgress());
                    System.out.println("config = " + config);
                    System.out.println("damage = " + damage);*/
                    if (area.inside(loc)) return;
                    //System.out.println(area);
                    p.damage(damage);
                    p.sendMessage(String.format(R.INSTANCE.lang("lang19"), damage));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
    }
}

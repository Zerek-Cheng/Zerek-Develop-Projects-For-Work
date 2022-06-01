package com._0myun.minecraft.peacewarrior.listeners.battle;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.MapManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.data.Position;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * 玩家死亡后就失败了呗
 */
public class PlayerFailedListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent e) {
        try {
            Player p = e.getEntity();
            p.getInventory().clear();
            PlayerData pD = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
            if (!pD.getStat().equals(PlayerData.Stat.PLAY)) return;
            Battle battle = DBManager.battleDao.queryForMap(pD.getMap());
            BattleMap map = MapManager.getMapByName(pD.getMap());
            if (!battle.getStat().equals(Battle.Stat.PLAY) && !battle.getStat().equals(Battle.Stat.FINISH)) return;
            BattleManager.quit(p);
            BattleManager.reward(p, map, false);
            p.updateInventory();

            DBManager.playerDataDao.update(pD);
            pD.setDeath(pD.getDeath() + 1);
            pD.setTotal(pD.getTotal() + 1);
            pD.setStat(PlayerData.Stat.NONE);
            pD.setMap(null);
            DBManager.playerDataDao.update(pD);

            BattleManager.checkWin(battle);
            p.teleport(((Position) R.INSTANCE.getConfig().get("hall")).toBukkitLocation());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

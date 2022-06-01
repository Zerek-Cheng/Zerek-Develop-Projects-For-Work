package com._0myun.minecraft.peacewarrior.listeners.battle;

import com._0myun.minecraft.peacewarrior.BattleManager;
import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.MapManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.data.Position;
import com._0myun.minecraft.peacewarrior.utils.Area;
import lk.vexview.api.VexViewAPI;
import lk.vexview.event.KeyBoardPressEvent;
import lk.vexview.gui.VexGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class MapKeyBoardListener implements Listener {
    @EventHandler
    public void onFly(KeyBoardPressEvent e) {
        Player p = e.getPlayer();
        int key = e.getKey();
        if (e.getEventKeyState() == false) return;
        if (!(key == R.INSTANCE.getConfig().getInt("vv-key-map", 50))) return;
        if (VexViewAPI.getPlayerCurrentGui(p) != null) return;
        PlayerData pd = null;
        try {
            pd = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (!pd.getStat().equals(PlayerData.Stat.PLAY)) return;

        BattleMap map = MapManager.getMapByName(pd.getMap());
        Position min = map.getPosition_min();
        Position max = map.getPosition_max();

        Area area = BattleManager.areas.get(pd.getMap());
        Position pos1 = area.getPos1();
        Position pos2 = area.getPos2();
        Area pre = BattleManager.pres.get(pd.getMap());
        Position pos3 = pre.getPos1();
        Position pos4 = pre.getPos2();/*
        System.out.println("min = " + min);
        System.out.println("max = " + max);
        System.out.println("pos1 = " + pos1);
        System.out.println("pos2 = " + pos2);
        System.out.println("pos3 = " + pos3);
        System.out.println("pos4 = " + pos4);
*/
        VexGui gui = new VexGui(
                String.format(R.INSTANCE.getConfig().getString("vv-url-map")
                        , pos1.getX() - min.getX()
                        , pos1.getZ() - min.getZ()
                        , pos2.getX() - min.getX()
                        , pos2.getZ() - min.getZ()
                        , pos3.getX() - min.getX()
                        , pos3.getZ() - min.getZ()
                        , pos4.getX() - min.getX()
                        , pos4.getZ() - min.getZ()) + "&map=" + map.getName()
                ,-1, -1, 150, 150, 150, 150
        );
        System.out.println(gui.getNetCode());
        VexViewAPI.openGui(p, gui);
    }
}

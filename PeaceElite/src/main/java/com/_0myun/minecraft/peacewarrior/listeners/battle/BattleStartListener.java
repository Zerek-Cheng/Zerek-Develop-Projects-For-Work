package com._0myun.minecraft.peacewarrior.listeners.battle;

import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.FlyManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.FlyLine;
import com._0myun.minecraft.peacewarrior.data.Position;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleFlyEndEvent;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleStartEvent;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BattleStartListener implements Listener {
    @EventHandler
    public void onStart(PWBattleStartEvent e) {
        Battle battle = e.getBattle();
        FlyLine line = FlyManager.lines.get(battle.getMap());
        Position pos1 = line.getPos1();
        Position pos2 = line.getPos2();
        R.INSTANCE.getLogger().info("战场" + battle.getMap() + "游戏开始,航线视图发送任务启动");
        BukkitRunnable runnable = new BukkitRunnable() {

            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(500l);
                        DBManager.battleDao.refresh(battle);
                        if (battle.getStat().equals(Battle.Stat.FINISH)) return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    Integer percent = FlyManager.precent.get(battle.getMap());
                    if (percent == null) percent = 1;
                    ++percent;
                    FlyManager.precent.put(battle.getMap(), percent);

                    List<PlayerData> playerDatas = DBManager.playerDataDao.queryForMap(battle.getMap(), PlayerData.Stat.WAIT, PlayerData.Stat.PLAY);
                    for (PlayerData playerData : playerDatas) {
                        Player p = Bukkit.getPlayer(playerData.getPlayer());
                        if (FlyManager.fly.contains(p.getUniqueId())) continue;
/*                        VexImageShow image = new VexImageShow(
                                R.INSTANCE.getConfig().getInt("vv-id-flight", 1000)
                                //,"http://127.0.0.1:808/map.png"
                                //, String.format(R.INSTANCE.getConfig().getString("vv-url-flight"), pos1.getX(), pos1.getZ(), pos2.getX(), pos2.getZ(), percent)
                                , "http://127.0.0.1:808/flight.png?x1=88&y1=0&x2=98&y2=123&finish=10"
                                , 0
                                , 0
                                , 100
                                , 100
                                , 100
                                , 100
                                , 3
                        );
                        System.out.println(String.format(R.INSTANCE.getConfig().getString("vv-url-flight"), pos1.getX(), pos1.getZ(), pos2.getX(), pos2.getZ(), percent));
                        //VexViewAPI.removeHUD(p, R.INSTANCE.getConfig().getInt("vv-id-flight", 1000));
                        VexViewAPI.sendHUD(p, image);*/
                        if (percent >= 100) {
                            FlyManager.fly(p);
                            p.closeInventory();
                            FlyManager.fly.remove(p.getUniqueId());
                            p.sendMessage(R.INSTANCE.lang("lang18"));
                            continue;
                        }

                        if (percent % 3 != 0) continue;
                        VexGui gui = new VexGui(
                                String.format(R.INSTANCE.getConfig().getString("vv-url-flight"), pos1.getX(), pos1.getZ(), pos2.getX(), pos2.getZ(), percent) + "&map=" + battle.getMap()
                                , 0, 0, 100, 100, 100, 100);
                        VexViewAPI.openGui(p, gui);
                    }
                }

                FlyManager.precent.remove(battle.getMap());
                FlyManager.lines.remove(battle.getMap());
                FlyManager.precent.remove(battle.getMap());
                Bukkit.getPluginManager().callEvent(new PWBattleFlyEndEvent(battle));
            }
        };
        runnable.runTaskLaterAsynchronously(R.INSTANCE, 10);
    }
}

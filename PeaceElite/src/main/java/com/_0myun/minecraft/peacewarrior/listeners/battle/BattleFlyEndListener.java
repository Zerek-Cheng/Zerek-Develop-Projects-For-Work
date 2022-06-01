package com._0myun.minecraft.peacewarrior.listeners.battle;

import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.events.battle.PWBattleFlyEndEvent;
import com._0myun.minecraft.peacewarrior.task.battle.SafeAreaThread;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BattleFlyEndListener implements Listener {
    @EventHandler
    public void onFlyEnd(PWBattleFlyEndEvent e) {
        Battle battle = e.getBattle();
        R.INSTANCE.getLogger().info("地图" + battle.getMap() + "跳伞阶段已结束...5秒后开启缩圈线程!");
        new SafeAreaThread(battle).runTaskLaterAsynchronously(R.INSTANCE,50l);
    }
}

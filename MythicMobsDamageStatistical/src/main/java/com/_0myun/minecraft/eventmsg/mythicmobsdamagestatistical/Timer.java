package com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical;

import com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Rule;

import java.util.*;
import java.util.logging.Level;


public class Timer implements Runnable {
    @Override
    public void run() {
        try {
            RulesManager.getAllRules().getKeys(false).forEach(name -> {
                Rule rule = RulesManager.getRule(name);
                long time = TimeManager.getTime(name);
                if (System.currentTimeMillis() < time) return;
                try {
                    List<UUID> getter = DataManager.getTop10(name);
                    getter.forEach(uuid -> {
                        DataManager.reward(name, uuid);
                        Main.getPlugin().getLogger().log(Level.INFO, "玩家" + uuid.toString() + "获得了" + name + "周期伤害奖励");
                    });
                } finally {
                    DataManager.resetStatistical(name);
                    TimeManager.update(name);//更新到下一个奖励时间点
                    Main.getPlugin().saveConfig();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

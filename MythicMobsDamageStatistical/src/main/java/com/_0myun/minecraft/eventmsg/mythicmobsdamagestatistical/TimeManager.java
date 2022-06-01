package com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical;

import com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Rule;
import org.bukkit.configuration.ConfigurationSection;

import java.util.logging.Level;

public class TimeManager {
    public static ConfigurationSection getData() {
        return Main.getPlugin().getConfig().getConfigurationSection("time");
    }

    public static void setTime(String name, long time) {
        getData().set(name, time);
    }

    public static long getTime(String name) {
        return getData().getLong(name);
    }

    public static void update(String name) {
        Rule rule = RulesManager.getRule(name);
        if (rule == null) return;
        int timeOfRound = rule.getTimeOfRound();
        long time = getTime(name);
        setTime(name, System.currentTimeMillis() + (timeOfRound * 1000l));
        Main.getPlugin().getLogger().log(Level.INFO, "伤害统计奖励周期" + name + "刷新....'");
    }
}

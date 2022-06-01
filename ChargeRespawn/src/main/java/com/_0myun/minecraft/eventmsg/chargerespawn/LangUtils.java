package com._0myun.minecraft.eventmsg.chargerespawn;

import lombok.var;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class LangUtils {
    public static String get(String lang) {
        return variable(Main.getPlugin().getConfig().getString("lang." + lang));
    }

    public static String get(String lang, Player p) {
        return variable(Main.getPlugin().getConfig().getString("lang." + lang), p);
    }

    public static String variable(String lang) {
        var variable = new HashMap<String, String>();
        variable.put("interval", String.valueOf(Config.getRespawnConfig().getInterval()));
        variable.put("dayFree", String.valueOf(Config.getRespawnConfig().getDayFree()));
        variable.put("coinCount", String.valueOf(Config.getRespawnConfig().getCoinCount()));
        variable.put("point.invincible", String.valueOf(Config.getRespawnConfig().getPointConfig().getInvincible()));
        variable.put("point.cost", String.valueOf(Config.getRespawnConfig().getPointConfig().getCost()));

        String[] tmp = {lang};
        variable.forEach((k, v) -> {
            tmp[0] = tmp[0].replace("<" + k + ">", v);
        });
        return tmp[0];
    }

    public static String variable(String lang, Player p) {
        lang = variable(lang);
        var variable = new HashMap<String, String>();
        variable.put("free.needWait", String.valueOf(RespawnManager.getFreeRespawnNeedWait(p) / 1000));
        variable.put("data.free", String.valueOf(Config.getPlayerData(p).getFree()));
        variable.put("data.coin", String.valueOf(Config.getPlayerData(p).getCoin()));
        String[] tmp = {lang};
        variable.forEach((k, v) -> {
            tmp[0] = tmp[0].replace("<" + k + ">", v);
        });
        return tmp[0];
    }
}

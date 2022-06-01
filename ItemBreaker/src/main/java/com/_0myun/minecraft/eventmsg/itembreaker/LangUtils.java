package com._0myun.minecraft.eventmsg.itembreaker;

public class LangUtils {
    public static String get(String lang) {
        return ItemBreaker.getPlugin().getConfig().getString("lang." + lang);
    }
}

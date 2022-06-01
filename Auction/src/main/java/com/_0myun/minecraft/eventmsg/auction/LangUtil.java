package com._0myun.minecraft.eventmsg.auction;

public class LangUtil {
    public static String getLang(String lang) {
        return Main.getPlugin().getConfig().getString("ui." + lang).replace("§","");
        //todo阉割颜色
    }
}

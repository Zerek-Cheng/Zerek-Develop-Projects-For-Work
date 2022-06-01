package com._0myun.minecraft.auction;

import java.util.List;

public class LangManager {
    public static String getLang(String lang) {
        return Auction.INSTANCE.getConfig().getString("lang." + lang, "无效文字" + lang);
    }


    public static List<String> getLangs(String lang) {
        return Auction.INSTANCE.getConfig().getStringList("lang." + lang);
    }
}

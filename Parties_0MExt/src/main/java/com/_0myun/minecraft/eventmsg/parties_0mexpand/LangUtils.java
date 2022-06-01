package com._0myun.minecraft.eventmsg.parties_0mexpand;

public class LangUtils {
    public static String get(String lang) {
        return Parties_0MExpand.plugin.getConfig().getString("lang." + lang);
    }
}

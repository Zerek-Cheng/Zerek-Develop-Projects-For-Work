package com.lmyun.event.banandbuff;

import org.bukkit.configuration.file.FileConfiguration;

public class LangUtil {
    private static FileConfiguration lang;

    static {
        lang = Main.getPlugin().getPluginConfig().get("Lang");
    }

    public static String getLang(String lang) {
        return LangUtil.lang.getString(lang);
    }
}

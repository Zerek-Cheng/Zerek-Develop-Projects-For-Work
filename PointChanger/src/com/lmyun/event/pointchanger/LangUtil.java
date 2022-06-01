package com.lmyun.event.pointchanger;

import org.bukkit.configuration.file.FileConfiguration;

public class LangUtil {
    private static FileConfiguration lang;

    static {
        lang = Main.getPlugin().pluginConfig.get("Lang");
    }

    public static String getLang(String lang) {
        return LangUtil.lang.getString(lang);
    }
}

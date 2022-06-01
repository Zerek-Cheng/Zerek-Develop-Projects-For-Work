package com.lmyun.event.grouploreandanti;

import org.bukkit.configuration.file.FileConfiguration;

public class LangUtil {
    public static FileConfiguration lang;

    public static String getLang(String lang) {
        return LangUtil.lang.getString(lang);
    }
}

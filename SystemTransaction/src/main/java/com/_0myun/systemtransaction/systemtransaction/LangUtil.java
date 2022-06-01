package com._0myun.systemtransaction.systemtransaction;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LangUtil {
    private static FileConfiguration lang;

    static {
        reload();
    }

    public static void reload() {
        lang = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder().getPath() + "/lang.yml"));
    }

    public static String getLang(String lang) {
        return LangUtil.lang.getString(lang).replace("&", "§");
    }
}

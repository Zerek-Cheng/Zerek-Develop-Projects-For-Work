package com._0myun.minecraft.mosaic;

import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;

public class LangManager {
    @Setter
    private static ConfigurationSection lang;

    public static String get(String name) {
        return lang.getString(name);
    }
}

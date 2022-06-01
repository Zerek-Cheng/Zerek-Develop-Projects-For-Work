package com._0myun.minecraft.pixelmonplus;

import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;

public class Lang {
    @Setter
    public static ConfigurationSection data;

    public static String get(String name) {
        return data.getString(name);
    }
}

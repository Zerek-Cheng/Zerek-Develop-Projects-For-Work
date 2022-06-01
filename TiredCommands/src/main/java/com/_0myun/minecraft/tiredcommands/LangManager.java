package com._0myun.minecraft.tiredcommands;

public class LangManager {

    public static String get(String name) {
        return Main.plugin.getConfig().getConfigurationSection("lang").getString(name);
    }
}

package com._0myun.minecraft.pixelmonplus;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Lang.setData(getConfig().getConfigurationSection("lang"));
        new CommandDealer();

    }

}

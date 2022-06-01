package com._0myun.minecraft.pokemon.denyrelease;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
        new NetLoader().onEnable();
    }

}

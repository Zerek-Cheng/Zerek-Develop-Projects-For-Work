package com._0myun.minecraft.vexshopbutton;

import com._0myun.minecraft.pokemon.pokemonpapi.Loader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        new Loader().onEnable();
    }

}

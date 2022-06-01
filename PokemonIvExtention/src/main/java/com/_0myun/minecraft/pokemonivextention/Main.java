package com._0myun.minecraft.pokemonivextention;

import com._0myun.minecraft.pokemon.pokemonpapi.Loader;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();

        new Loader().onEnable();
    }
}

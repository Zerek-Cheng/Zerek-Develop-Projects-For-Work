package com._0myun.minecraft.pokemon.pokemonpapi;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();
        new Loader().onEnable();
        //new PokemonPapi();
    }

}

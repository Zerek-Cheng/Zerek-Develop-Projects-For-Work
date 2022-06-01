package com._0myun.minecraft.papicountdown;

import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PapiCountDown extends JavaPlugin {
    public static PapiCountDown plugin;

    @Override
    public void onEnable() {
        plugin = this;

        new Variable().hook();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package com._0myun.minecraft.debugpokelucky;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DebugPokeLucky extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Listener(), this);
    }

}

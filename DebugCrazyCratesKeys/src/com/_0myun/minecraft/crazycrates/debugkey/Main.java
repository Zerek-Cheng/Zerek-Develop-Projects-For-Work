package com._0myun.minecraft.crazycrates.debugkey;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        new Checker().runTaskTimer(this, 20, 20);
    }


}

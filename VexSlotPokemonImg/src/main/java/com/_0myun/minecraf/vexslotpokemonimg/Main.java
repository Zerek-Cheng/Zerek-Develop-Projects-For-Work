package com._0myun.minecraf.vexslotpokemonimg;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
    public static Main INSTANCE;
    public int id = 4438;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        id = getConfig().getInt("id", 4438);
        new CheckTask().runTaskTimerAsynchronously(this, 20, 20);
        new Outer().runTaskLater(this, 1);
    }
}

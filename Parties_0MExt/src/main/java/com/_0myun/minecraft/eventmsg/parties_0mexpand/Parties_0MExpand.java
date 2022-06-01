package com._0myun.minecraft.eventmsg.parties_0mexpand;

import me.alessiodp.parties.Parties;
import me.alessiodp.parties.utils.api.Api;
import me.alessiodp.parties.utils.api.PartiesAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Parties_0MExpand extends JavaPlugin {
    static Parties_0MExpand plugin;
    static Api api;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        api = new PartiesAPI();
        Bukkit.getPluginManager().registerEvents(new ExpExtionListener(), this);
        Bukkit.getPluginManager().registerEvents(new FlanExtionListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

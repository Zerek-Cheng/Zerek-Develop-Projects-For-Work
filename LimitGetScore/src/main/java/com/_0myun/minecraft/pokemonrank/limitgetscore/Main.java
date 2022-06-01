package com._0myun.minecraft.pokemonrank.limitgetscore;

import catserver.api.bukkit.event.ForgeEvent;
import net.ginyai.poketrainerrank.core.events.PTRPlayerScoreAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onScoreAdd(ForgeEvent e) {
        if (!(e.getForgeEvent() instanceof PTRPlayerScoreAddEvent)) return;
        PTRPlayerScoreAddEvent event = (PTRPlayerScoreAddEvent) e.getForgeEvent();
        if (!canGet(event.uuid) && event.score > 0) {
            event.score = 0;
            Bukkit.getPlayer(event.uuid).sendMessage("今天到上限了");
            return;
        }
        add(event.uuid, event.score);
        saveConfig();
    }

    public boolean canGet(UUID uuid) {
        int max = getConfig().getInt("max");
        int now = get(uuid);
        return now < max;
    }

    public String getDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public void add(UUID uuid, int score) {
        getConfig().set("data." + uuid.toString() + "-" + getDate(), get(uuid) + score);
    }

    public int get(UUID uuid) {
        return getConfig().getInt("data." + uuid.toString() + "-" + getDate());
    }
}

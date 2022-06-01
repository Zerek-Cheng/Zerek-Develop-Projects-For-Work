package com._0myun.minecraft.pokemonrank.initscore;

import catserver.api.bukkit.event.ForgeEvent;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.ginyai.poketrainerrank.core.data.SeasonDataManager;
import net.ginyai.poketrainerrank.core.events.PTRPlayerCreateEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onNewPlayer(ForgeEvent tmp) {
        if (!(tmp.getForgeEvent() instanceof PTRPlayerCreateEvent)) return;
        PTRPlayerCreateEvent e = (PTRPlayerCreateEvent) tmp.getForgeEvent();
        PlayerData pd = e.player;
        if (exist(pd.getUuid())) return;
        RankSeason season = e.season;
        SeasonDataManager dm = season.getDataManager();
        dm.updateScore(pd.getUuid(), getConfig().getInt("init"));
        add(pd.getUuid());
        System.out.println("玩家" + pd.getName() + "排位积分初始化成功");
    }


    public boolean exist(UUID uuid) {
        return getConfig().getStringList("data").contains(uuid.toString());
    }

    public void add(UUID uuid) {
        List<String> data = getConfig().getStringList("data");
        data.add(uuid.toString());
        getConfig().set("data", data);
        saveConfig();
    }
}

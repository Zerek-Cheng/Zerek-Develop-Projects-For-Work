package com._0myun.minecraft.pixelmonknockout;

import com._0myun.minecraft.pixelmonknockout.api.events.GameLoadEvent;
import com._0myun.minecraft.pixelmonknockout.commands.AdminCommand;
import com._0myun.minecraft.pixelmonknockout.commands.MainCommand;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import com._0myun.minecraft.pixelmonknockout.listeners.PlayerListener;
import com._0myun.minecraft.pixelmonknockout.listeners.battle.BattleListener;
import com._0myun.minecraft.pixelmonknockout.listeners.battle.PixelmonBattleListener;
import com._0myun.minecraft.pixelmonknockout.listeners.init.GameListener;
import com._0myun.minecraft.pixelmonknockout.listeners.init.PlayerInitListener;
import com._0myun.minecraft.pixelmonknockout.task.game.BanedTimeoutTask;
import com._0myun.minecraft.pixelmonknockout.task.game.GameInfoRefreshTask;
import com._0myun.minecraft.pixelmonknockout.task.game.GameRunner;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class R extends JavaPlugin {
    public static R INSTANCE;
    @Getter
    private final List<Game> games = new ArrayList<>();
    @Getter
    private final HashMap<Integer, GameRunner> runners = new HashMap<>();

    static {
        ConfigurationSerialization.registerClass(Game.class, "com._0myun.minecraft.pixelmonknockout.data.Game");
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        init();
        MainCommand mainCommand = new MainCommand();
        getCommand("fsap").setExecutor(mainCommand);
        getCommand("fsapa").setExecutor(new AdminCommand());
        getCommand("pk").setExecutor(mainCommand);
    }

    public String lang(String lang) {
        return getConfig().getString("lang." + lang, lang);
    }

    public Game getGame(String name) {
        for (Game game : this.getGames())
            if (game.getName().equalsIgnoreCase(name)) return game;
        return null;
    }

    private void init() {
        try {
            saveDefaultConfig();
            DB.initDao();//数据库初始化连接
            Bukkit.getPluginManager().registerEvents(new GameListener(), this);
            Bukkit.getPluginManager().registerEvents(new PlayerInitListener(), this);
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
            Bukkit.getPluginManager().registerEvents(new PixelmonBattleListener(), this);
            Bukkit.getPluginManager().registerEvents(new BattleListener(), this);
            new Papi().register();

            games.clear();
            runners.clear();
            File gameConfigDir = new File(this.getDataFolder(), "/game/");
            if (!gameConfigDir.exists())
                gameConfigDir.mkdirs();
            saveResource("game/default.yml", false);
            for (File file : gameConfigDir.listFiles()) {
                if (!file.getName().endsWith(".yml")) continue;
                YamlConfiguration tmp = new YamlConfiguration();
                tmp.load(file);
                Game game = (Game) tmp.get("game");
                this.games.add(game);
                getLogger().info("加载锦标赛-" + game.getDisplay());
                Bukkit.getPluginManager().callEvent(GameLoadEvent.builder().game(game).build());
            }

            Bukkit.getScheduler().runTaskTimerAsynchronously(this, new GameInfoRefreshTask(), 20L, 20L);
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, new BanedTimeoutTask(), 20L, 20L);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

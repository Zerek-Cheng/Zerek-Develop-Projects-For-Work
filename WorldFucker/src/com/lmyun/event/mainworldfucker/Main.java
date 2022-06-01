package com.lmyun.event.mainworldfucker;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
    }

    @Override
    public void onDisable() {
        this.saveLogs();
    }

/*    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.loadConfig();
        sender.sendMessage("ojbk");
        return true;
    }*/

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
            this.pluginConfig.get("logs").save(new File(getDataFolder().getPath() + "logs.yml"));
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

    public static Main getPlugin() {
        return (Main) Bukkit.getPluginManager().getPlugin("MainWorldFucker");
    }

}

package com.lmyun.event.mainworldfucker;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    public static Permission permission = null;
    public HashMap<String, FileConfiguration> pluginConfig = new HashMap<>();
    public List<String> worldLicense = new ArrayList<>();
    @Override
    public void onEnable() {
        this.setupPermissions();
        this.loadConfig();
        System.out.println(Main.getPlugin().pluginConfig.get("Config").getString("generator.name"));
        Bukkit.getPluginManager().registerEvents(new WorldFucker(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.loadConfig();
        sender.sendMessage("ojbk");
        return true;
    }

    public void loadConfig() {
        saveResource("Config.yml", false);
        this.pluginConfig.put("Config", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Config.yml")));
        saveResource("Lang.yml", false);
        this.pluginConfig.put("Lang", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Lang.yml")));
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return super.getDefaultWorldGenerator(worldName, id);
    }

    public static Main getPlugin() {
        return (Main) Bukkit.getPluginManager().getPlugin("MainWorldFucker");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
}

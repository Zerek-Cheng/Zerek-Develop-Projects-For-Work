package com._0myun.minecraft.bossshoppro.limiteuses.regularuse;

import com.google.common.util.concurrent.AbstractScheduledService;
import lombok.Getter;
import org.black_ixx.bossshop.addon.limiteduses.LimitedUses;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;

public final class RegularUse extends JavaPlugin {
    @Getter
    public static RegularUse plugin;
    File dataFile = new File(this.getDataFolder() + "/data.yml");
    @Getter
    FileConfiguration data = new YamlConfiguration();
    @Getter
    LimitedUses api;

    @Override
    public void onEnable() {
        api = (LimitedUses) Bukkit.getPluginManager().getPlugin("LimitedUses");
        
        //api.enableAddon();
        plugin = this;
        saveDefaultConfig();
        this.loadConfig();
        new Refresher().runTaskTimerAsynchronously(this, 20, 20);
    }



    public void loadConfig() {
        try {
            if (!dataFile.exists()) this.saveResource("data.yml", false);
            data.load(this.dataFile);
        } catch (Exception e) {

        }
    }

    public void saveData() {
        try {
            data.save(this.dataFile);
        } catch (Exception e) {

        }
    }

    @Override
    public void onDisable() {
        saveData();
    }
}

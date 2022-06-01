/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.FileConfigurationOptions
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.rLog;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
    public FileConfiguration config = new YamlConfiguration();
    public File file;
    public String s;

    public ConfigManager(String string) {
        this.file = new File(ReferralSystem.getInstance().getDataFolder(), String.valueOf(string) + ".yml");
        this.s = string;
    }

    public boolean Exists() {
        if (this.file.exists()) {
            return true;
        }
        return false;
    }

    public void Save() {
        rLog.info("&cThe " + this.s + ".yml file does not exist yet.");
        rLog.info("&7Creating and loading file " + this.s + ".yml.");
        this.config.options().copyDefaults(true);
        try {
            this.config.save(this.file);
            rLog.info("&7" + this.s + ".yml Create.");
        }
        catch (IOException iOException) {
            rLog.info("&cError on loaded " + this.s + ".yml.");
        }
    }

    public void Load() {
        rLog.info("&7Load " + this.s + ".yml");
        try {
            this.config.load(this.file);
            this.config.options().copyDefaults(true);
            this.config.save(this.file);
            rLog.info("&7" + this.s + ".yml loaded.");
        }
        catch (IOException | InvalidConfigurationException iOException) {
            rLog.info("&cError on loaded " + this.s + ".yml.");
        }
    }

    public void add(String string, String string2) {
        this.config.addDefault(string, (Object)string2);
    }

    public void add(String string, long l) {
        this.config.addDefault(string, (Object)l);
    }

    public void add(String string, boolean bl) {
        this.config.addDefault(string, (Object)bl);
    }

    public void add(String string, List<String> list) {
        this.config.addDefault(string, list);
    }

    public void add(String string, int n) {
        this.config.addDefault(string, (Object)n);
    }

    public boolean getBoolean(String string) {
        return this.config.getBoolean(string);
    }

    public String getString(String string) {
        return this.config.getString(string);
    }

    public int getInt(String string) {
        return this.config.getInt(string);
    }

    public ConfigurationSection getConfigurationSection(String string) {
        return this.config.getConfigurationSection(string);
    }

    public List<String> getStringList(String string) {
        return this.config.getStringList(string);
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.FileConfigurationOptions
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.utils.Files;

public class MyConfig {
    private JavaPlugin plugin;
    private String name;
    private String path;
    private FileConfiguration fileConfiguration;
    private File file;

    public MyConfig(JavaPlugin javaPlugin, String string, String string2) {
        this.plugin = javaPlugin;
        this.name = string2;
        this.path = string;
        this.load();
    }

    private void load() {
        File file;
        File file2;
        if (!this.plugin.getDataFolder().exists()) {
            Files.mkdir(this.plugin.getDataFolder());
        }
        if (!(file2 = new File(this.plugin.getDataFolder() + "/" + this.path)).exists()) {
            file2.mkdirs();
        }
        if (!(file = new File(this.plugin.getDataFolder() + "/" + this.path, this.name)).exists()) {
            Files.copy(DivineItems.class.getResourceAsStream(String.valueOf(this.path) + "/" + this.name), file);
        }
        this.file = file;
        this.fileConfiguration = YamlConfiguration.loadConfiguration((File)file);
        this.fileConfiguration.options().copyDefaults(true);
    }

    public void save() {
        try {
            this.fileConfiguration.options().copyDefaults(true);
            this.fileConfiguration.save(this.file);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.fileConfiguration;
    }
}


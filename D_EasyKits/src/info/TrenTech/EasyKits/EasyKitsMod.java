/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 */
package info.TrenTech.EasyKits;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

public class EasyKitsMod {
    private final EasyKits plugin;
    private final FileConfiguration config;
    private Logger logger;

    public EasyKitsMod(EasyKits plugin, Logger logger, FileConfiguration config) {
        this.plugin = plugin;
        this.logger = logger;
        this.config = config;
    }

    public EasyKits getPluginContainer() {
        return this.plugin;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}


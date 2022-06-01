// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.util;

import org.bukkit.configuration.file.FileConfiguration;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil
{
    public static Location getLocation(final String text) {
        final String[] fg = text.split(":");
        final Location loc = new Location(Bukkit.getWorld(fg[0]), Double.parseDouble(fg[1]), Double.parseDouble(fg[2]), Double.parseDouble(fg[3]), Float.parseFloat(fg[4]), Float.parseFloat(fg[5]));
        return loc;
    }
    
    public static void setLocationConfiguration(final Location loc, final File f, final String key) {
        final FileConfiguration data = (FileConfiguration)YamlConfiguration.loadConfiguration(f);
        final String value = loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch();
        data.set(key, (Object)value);
        try {
            data.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

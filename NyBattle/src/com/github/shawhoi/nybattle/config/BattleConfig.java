// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.config;

import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class BattleConfig
{
    private static FileConfiguration config;
    
    public static void build(final File f) {
        BattleConfig.config = (FileConfiguration)YamlConfiguration.loadConfiguration(f);
    }
    
    public static String getString(final String key) {
        return BattleConfig.config.getString(key).replace("&", "¡ì");
    }
    
    public static List<String> getStringList(final String key) {
        final List<String> list = new ArrayList<String>();
        for (final String i : BattleConfig.config.getStringList(key)) {
            list.add(i.replace("&", "¡ì"));
        }
        return list;
    }
    
    public static Integer getInt(final String key) {
        return BattleConfig.config.getInt(key);
    }
    
    public static Double getDouble(final String key) {
        return BattleConfig.config.getDouble(key);
    }
    
    public static ItemStack getItemStack(final String key) {
        return BattleConfig.config.getItemStack(key);
    }
}

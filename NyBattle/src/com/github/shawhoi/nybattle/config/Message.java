// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.config;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.FileConfiguration;

public class Message
{
    private static FileConfiguration message;
    private static String prefix;
    private static ItemStack helpitem;
    
    public static String getPrefix() {
        return Message.prefix;
    }
    
    public static void build(final File f) {
        Message.message = (FileConfiguration)YamlConfiguration.loadConfiguration(f);
        Message.prefix = BattleConfig.getString("Settings.Prefix");
        Message.helpitem = new ItemStack(Material.PAPER);
        final ItemMeta im = Message.helpitem.getItemMeta();
        im.setDisplayName(getString("Help.ItemName"));
        Message.helpitem.setItemMeta(im);
    }
    
    public static ItemStack getHelpItem() {
        return Message.helpitem;
    }
    
    public static String getString(final String key) {
        return Message.message.getString(key).replace("&", "¡ì");
    }
    
    public static List<String> getStringList(final String key) {
        final List<String> list = new ArrayList<String>();
        for (final String i : Message.message.getStringList(key)) {
            list.add(i.replace("&", "¡ì"));
        }
        return list;
    }
}

/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.file.FileConfiguration
 */
package me.kevinnovak.inventorypages;

import java.util.ArrayList;
import java.util.List;
import me.kevinnovak.inventorypages.InventoryPages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ColorConverter {
    public InventoryPages plugin;

    public ColorConverter(InventoryPages inventoryPages) {
        this.plugin = inventoryPages;
    }

    String convert(String string) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)string);
    }

    List<String> convert(List<String> list) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string : list) {
            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string));
        }
        return arrayList;
    }

    String convertConfig(String string) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)this.plugin.getConfig().getString(string));
    }

    List<String> convertConfigList(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string2 : this.plugin.getConfig().getStringList(string)) {
            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string2));
        }
        return arrayList;
    }
}


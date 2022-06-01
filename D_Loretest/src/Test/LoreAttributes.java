/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.FileConfigurationOptions
 *  org.bukkit.entity.Player
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 */
package Test;

import Test.LoreEvents;
import Test.LoreManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LoreAttributes
extends JavaPlugin {
    public static LoreManager loreManager;
    public static FileConfiguration config;

    static {
        config = null;
    }

    public void onEnable() {
        config = this.getConfig();
        config.options().copyDefaults(true);
        this.saveConfig();
        if (loreManager == null) {
            loreManager = new LoreManager(this);
        }
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new LoreEvents(), (Plugin)this);
    }

    public void onDisable() {
        HandlerList.unregisterAll((Plugin)this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getLabel().equalsIgnoreCase("hp")) {
            if (!(sender instanceof Player)) {
                return false;
            }
            Player p = (Player)sender;
            p.sendMessage("Health: " + p.getHealth() + "/" + p.getMaxHealth());
            return true;
        }
        if (cmd.getLabel().equalsIgnoreCase("lorestats")) {
            if (!(sender instanceof Player)) {
                return false;
            }
            loreManager.displayLoreStats((Player)sender);
            return true;
        }
        return false;
    }
}


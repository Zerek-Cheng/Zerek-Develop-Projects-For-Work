package com.lmyun.event.regioncontrol;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    HashMap<String, FileConfiguration> pluginConfig = new HashMap<>();

    @Override
    public void onEnable() {
        this.loadConfig();
        Bukkit.getPluginManager().registerEvents(new RegionListener(this), this);
        getLogger().log(Level.WARNING,"定制插件就找灵梦云科技0MYUN.COM,临时QQ903115511");

        getLogger().log(Level.WARNING,"定制插件就找灵梦云科技0MYUN.COM,临时QQ903115511");

        getLogger().log(Level.WARNING,"定制插件就找灵梦云科技0MYUN.COM,临时QQ903115511");

        getLogger().log(Level.WARNING,"定制插件就找灵梦云科技0MYUN.COM,临时QQ903115511");

        getLogger().log(Level.WARNING,"定制插件就找灵梦云科技0MYUN.COM,临时QQ903115511");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.loadConfig();
        sender.sendMessage(this.getLang("lang3"));
        return true;
    }

    public void loadConfig() {
        saveResource("Config.yml", false);
        this.pluginConfig.put("Config", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Config.yml")));
    }

    public static Main getPlugin() {
        return (Main) Bukkit.getPluginManager().getPlugin("RegionControl");
    }

    public boolean inRegion(Location loc) {
        List<Map<?, ?>> regions = this.pluginConfig.get("Config").getMapList("region");
        for (Map region : regions) {
            Area area = new Area(new Location(Bukkit.getWorld((String) region.get("world")), (double) region.get("xa"), 0, (double) region.get("za")),
                    new Location(Bukkit.getWorld((String) region.get("world")), (double) region.get("xb"), 255, (double) region.get("zb")));
            if (area.contain(loc)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBannedCommand(String command) {
        return this.pluginConfig.get("Config").getStringList("command").contains(command);
    }

    public String getLang(String lang) {
        return this.pluginConfig.get("Config").getString(lang);
    }
}

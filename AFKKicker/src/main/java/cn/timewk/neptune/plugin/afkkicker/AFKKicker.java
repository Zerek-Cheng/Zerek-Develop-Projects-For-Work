
package cn.timewk.neptune.plugin.afkkicker;

import cn.timewk.neptune.plugin.afkkicker.api.API;
import cn.timewk.neptune.plugin.afkkicker.api.command.CommandReload;
import cn.timewk.neptune.plugin.afkkicker.listener.PlayerListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class AFKKicker
        extends JavaPlugin {
    private static AFKKicker plugin;
    private API api;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        AFKKicker.getPlugin().reloadConfig();
        this.api = new NeptuneAPI();
        this.getCommand("afkkicker").setExecutor((CommandExecutor) new CommandReload());
        this.api.registerListeners(new PlayerListener(this.getConfig().getInt("Period_Code"), this.getConfig().getInt("Delay_Code"), this.getConfig().getString("Message_Kick_Code"), this.getConfig().getString("Message_Notice_TenSec_Code"), this.getConfig().getString("Message_Success"), this.getConfig().getString("Message_Notice_Code")));
        this.getAPI().info("加载完成！");
    }

    public static AFKKicker getPlugin() {
        return plugin;
    }

    public API getAPI() {
        return this.api;
    }

    public YamlConfiguration initYml(File file) {
        return YamlConfiguration.loadConfiguration((File) file);
    }

    public File saveResource(String path) {
        File file = new File(this.getDataFolder(), path);
        if (!file.exists()) {
            super.saveResource(path, false);
        }
        return file;
    }

    public void saveYml(FileConfiguration yml, File file) {
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


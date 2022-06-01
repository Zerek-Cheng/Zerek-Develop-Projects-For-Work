// 
// Decompiled by Procyon v0.5.30
// 

package cn.Jerez.GUIFenjie;

import java.util.List;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import java.util.HashMap;
import org.bukkit.plugin.Plugin;
import cn.Jerez.Library.Utils.Commands.ISubCommand;
import cn.Jerez.Library.Utils.Commands.CommandCore;
import cn.Jerez.GUIFenjie.Commands.FJCommand;
import cn.Jerez.Library.Caches.Disk.AbstractDiskOperation;
import cn.Jerez.Library.Caches.IEntityCacheHandler;
import cn.Jerez.GUIFenjie.Caches.SettingsCacheHandler;
import cn.Jerez.GUIFenjie.Beans.ConfigSetting;
import java.util.Map;
import cn.Jerez.GUIFenjie.Beans.Settings;
import cn.Jerez.Library.Caches.EntityCache;
import cn.Jerez.GUIFenjie.Caches.SettingsCache;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private static SettingsCache settingsCache;
    private static EntityCache<Settings> ec;
    private static Map<String, ConfigSetting> CONFIG_CACHE;
    private static Main instance;
    
    public static Main getInstance() {
        return Main.instance;
    }
    
    public void onEnable() {
        this.init();
        this.reload();
        this.getLogger().info("\u5206\u89e3\u63d2\u4ef6\u5df2\u7ecf\u542f\u52a8!");
    }
    
    private void init() {
        Main.instance = this;
        Main.settingsCache = new SettingsCache((IEntityCacheHandler<Settings>)new SettingsCacheHandler());
        Main.ec = (EntityCache<Settings>)EntityCache.getInstance((AbstractDiskOperation)Main.settingsCache);
        CommandCore.registerCommand("fj", (ISubCommand)new FJCommand(), (Plugin)this);
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        this.saveDefaultConfig();
    }
    
    public void reload() {
        this.reloadConfig();
        Main.ec.save();
        Main.ec.reload();
        Main.CONFIG_CACHE = new HashMap<String, ConfigSetting>();
        final ConfigurationSection settings_cs = this.getConfig().getConfigurationSection("Settings");
        final Set<String> keys = (Set<String>)settings_cs.getKeys(false);
        for (final String key : keys) {
            final String msg = settings_cs.getString(key + ".Msg");
            final List<String> cmds = (List<String>)settings_cs.getStringList(key + ".Cmds");
            final ConfigSetting cs = new ConfigSetting(key, msg, cmds);
            Main.CONFIG_CACHE.put(key, cs);
        }
    }
    
    public static Map<String, ConfigSetting> getCONFIG_CACHE() {
        return Main.CONFIG_CACHE;
    }
    
    public static EntityCache<Settings> getEc() {
        return Main.ec;
    }
    
    public void onDisable() {
        Main.ec.save();
        this.getLogger().info("\u5206\u89e3\u63d2\u4ef6\u5df2\u7ecf\u5173\u95ed!");
    }
}

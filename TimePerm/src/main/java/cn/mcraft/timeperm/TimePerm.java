package cn.mcraft.timeperm;

import cn.mcraft.timeperm.protection.PlayerData;
import cn.mcraft.timeperm.utils.RConfig;
import cn.mcraft.timeperm.utils.TimeConvert;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Closeable;
import java.io.File;
import java.util.HashMap;

public class TimePerm
extends JavaPlugin
implements Listener {
    public static Permission permission = null;
    private static TimePerm instance;
    private final RConfig config = new RConfig(new File(this.getDataFolder(), "config.yml"));
    private final HashMap<String, PlayerData> playerDatas = new HashMap();

    public static TimePerm getInstance() {
        return instance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onEnable() {
        instance = this;
        this.setupPermissions();
        this.reload();
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.getCommand(this.getName()).setExecutor((CommandExecutor)this);
    }

    public void reloadConfig() {
        this.config.load();
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    private void reload() {
        this.reloadConfig();
        this.playerDatas.clear();
        File folder = new File(this.getDataFolder(), "data");
        if (!folder.exists()) {
            try {
                folder.mkdirs();
            }
            catch (Exception e) {
                // empty catch block
            }
        }
        if (!folder.isDirectory()) {
            try {
                folder.delete();
            }
            catch (Exception e) {
                // empty catch block
            }
            try {
                folder.mkdirs();
            }
            catch (Exception e) {
                // empty catch block
            }
        }
        for (File file : folder.listFiles()) {
            try {
                PlayerData data = new PlayerData(file);
                this.playerDatas.put(data.getPlayerName(), data);
                //data.checkAllTimePerms(true);
            }
            catch (Exception e) {
                // empty catch block
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        block19 : {
            block18 : {
                if (args.length <= 0) break block18;
                switch (args[0].toLowerCase()) {
                    case "expire": {
                        if (args.length > 3) {
                            long millisecond = TimeConvert.parseTime(args[3]);
                            if (millisecond > 0L) {
                                PlayerData data = this.getPlayerData(args[1]);
                                data.insertTimePerm(args[2], millisecond, true);
                                this.info(sender, this.getConfig().getString("lang.success").replace("<time>", TimeConvert.formatTime(millisecond, false)));
                                break;
                            }
                            this.info(sender, this.getConfig().getString("lang.fail"));
                            break;
                        }
                        break block19;
                    }
                    case "reload": {
                        this.reload();
                        this.info(sender, this.getConfig().getString("lang.loaded"));
                        break;
                    }
                    default: {
                        return false;
                    }
                }
                break block19;
            }
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    public void on(PlayerJoinEvent e) {

        PlayerData data = this.getPlayerData(e.getPlayer().getName());
        data.checkAllTimePerms(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    public void on(PlayerChangedWorldEvent e) {
        PlayerData data = this.getPlayerData(e.getPlayer().getName());
        data.checkAllTimePerms(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    public void on(PlayerQuitEvent e) {
        PlayerData data = this.getPlayerData(e.getPlayer().getName());
        data.checkAllTimePerms(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    public void on(PlayerRespawnEvent e) {
        PlayerData data = this.getPlayerData(e.getPlayer().getName());
        data.checkAllTimePerms(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PlayerData getPlayerData(String playerName) {
        playerName = playerName.toLowerCase();
        PlayerData data = this.playerDatas.get(playerName);
        if (data == null) {
            data = new PlayerData(playerName);
            this.playerDatas.put(playerName, data);
            data.load();
        }
        return data;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean setupPermissions() {
        RegisteredServiceProvider permissionProvider = this.getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = (Permission)permissionProvider.getProvider();
        } else {
            this.info((CommandSender)this.getServer().getConsoleSender(), "\u672a\u627e\u5230\u6743\u9650\u63d2\u4ef6");
        }
        return permission != null;
    }

    public void info(CommandSender sender, String text) {
        sender.sendMessage("\u00a7c[\u00a7e" + this.getDescription().getPrefix() + "\u00a7c]\u00a7a " + ChatColor.translateAlternateColorCodes((char)'&', (String)text));
    }

    public static void removePerm(String player, String node) {
        for (World world : Bukkit.getWorlds()) {
            permission.playerRemove(world, player, node);
        }
    }

    public static void addPerm(String player, String node) {
        for (World world : Bukkit.getWorlds()) {
            permission.playerAdd(world, player, node);
        }
    }
}


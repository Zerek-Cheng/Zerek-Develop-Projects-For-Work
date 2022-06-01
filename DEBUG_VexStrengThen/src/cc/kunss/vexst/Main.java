/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  lk.vexview.api.VexViewAPI
 *  org.black_ixx.playerpoints.PlayerPoints
 *  org.black_ixx.playerpoints.PlayerPointsAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package cc.kunss.vexst;

import cc.kunss.vexst.commands.AilerCuiLianCommands;
import cc.kunss.vexst.listeners.*;
import cc.kunss.vexst.utils.*;
import lk.vexview.api.VexViewAPI;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main
        extends JavaPlugin {
    private static Main main;

    public void onEnable() {
        main = this;
        if (Bukkit.getPluginManager().isPluginEnabled("Yum")) {
            Bukkit.getConsoleSender().sendMessage("[VexStrengThen] \u00a7a\u63d2\u4ef6\u4e0d\u517c\u5bb9Yum\u63d2\u4ef6,\u8bf7\u624b\u52a8\u5378\u8f7dYum");
            Bukkit.getScheduler().cancelTasks((Plugin) Main.getMain());
            Bukkit.getPluginManager().disablePlugin((Plugin) Main.getMain());
            return;
        }
        Methods.Default();
/*        AdvancedLicense al = new AdvancedLicense(this.getConfig().getString("CDKEY"), "https://license2.minezone.cn/verify.php", (Plugin) this);
        if (!al.register()) {
            return;
        }*/
        this.getCommand("vst").setExecutor((CommandExecutor) new AilerCuiLianCommands());
        Bukkit.getPluginManager().registerEvents((Listener) new Click(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new Close(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new VexSlotClick(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new ResultGuiClick(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new Join(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new ExpChange(), (Plugin) this);
        VaultAPI.setupEconomy();
        PlayerPointAPIs.pointsApi = ((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI();
        Bukkit.getConsoleSender().sendMessage("\u00a7b-------------\u00a7f[\u00a7aVexStrengThen\u00a7f]\u00a7b-------------");
        Bukkit.getConsoleSender().sendMessage("\u00a7e\u25cf\u00a7a\u63d2\u4ef6\u52a0\u8f7d\u6210\u529f\uff01");
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders((Plugin) this).hook();
            Bukkit.getConsoleSender().sendMessage("\u00a7e\u25cf\u00a7a\u5df2\u627e\u5230\u627e\u5230PlaceholderAPI,\u52a0\u8f7d\u53d8\u91cf\u6210\u529f\uff01");
        } else {
            Bukkit.getConsoleSender().sendMessage("\u00a7e\u25cf\u00a7c\u672a\u627e\u5230PlaceholderAPI,\u5c06\u4e0d\u542f\u7528PlaceholderAPI");
        }
        Bukkit.getConsoleSender().sendMessage("\u00a7e\u25cf\u00a7a\u4f5c\u8005QQ2191746730[\u00a7fKunSs\u00a7a,\u00a7f\u79bb\u4eba\u00a7a]");
        Bukkit.getConsoleSender().sendMessage("\u00a7e\u25cf\u00a7a\u517c\u5bb9VV\u7248\u672c: \u00a7fV1.9.7 \u00a7a\u60a8\u7684VV\u7248\u672c: \u00a7f" + VexViewAPI.getVexView().getVersion());

        //      if (VexViewAPI.getVexView().getVersion().equalsIgnoreCase("1.9.7")) {
        Bukkit.getConsoleSender().sendMessage("\u00a7e\u25cf\u00a7a\u6210\u529f\u517c\u5bb9VexView\uff01");
        Bukkit.getConsoleSender().sendMessage("\u00a7b-------------\u00a7f[\u00a7aVexStrengThen\u00a7f]\u00a7b-------------");
/*        } else {

        Bukkit.getConsoleSender().sendMessage("\u00a7e\u25cf\u00a7a\u517c\u5bb9\u5931\u8d25,\u5378\u8f7d\u63d2\u4ef6...");
        Bukkit.getConsoleSender().sendMessage("\u00a7b-------------\u00a7f[\u00a7aVexStrengThen\u00a7f]\u00a7b-------------");
        Bukkit.getServer().getScheduler().cancelTasks((Plugin) this);
        Bukkit.getPluginManager().disablePlugin((Plugin) this);
        }*/
        getLogger().log(Level.WARNING, "§b【兼容最新版vexview by 灵梦云科技0MYUN.COM】定制插件+q2025255093");
    }

    public void onLoad() {
    }

    public static Main getMain() {
        return main;
    }
}


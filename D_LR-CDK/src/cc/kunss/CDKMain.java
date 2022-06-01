/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package cc.kunss;

import cc.kunss.commands.Cmd;
import cc.kunss.listeners.ClickButtonEvents;
import cc.kunss.utils.CDKMap;
import cc.kunss.utils.Files;
import cc.kunss.utils.Methods;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CDKMain
extends JavaPlugin {
    private static CDKMain main;
    private static Map<String, CDKMap> CDK;

    public static CDKMain getMain() {
        return main;
    }

    public static Map<String, CDKMap> getCDK() {
        return CDK;
    }

    public void onEnable() {
        main = this;
        this.reloadConfig();
        this.saveDefaultConfig();
        Files.loadconfig();
        Methods.loadCDKMap();
        this.getCommand("lcdk").setExecutor((CommandExecutor)new Cmd());
        Bukkit.getPluginManager().registerEvents((Listener)new ClickButtonEvents(), (Plugin)this);
        this.Message();
    }

    public void Message() {
        Bukkit.getConsoleSender().sendMessage("\u00a7f[\u00a7aLR-CDK\u00a7f]: \u00a7a\u8f7d\u5165\u63d2\u4ef6\u6210\u529f\uff01");
        Bukkit.getConsoleSender().sendMessage("\u00a7f[\u00a7aLR-CDK\u00a7f]: \u00a7a\u4f5c\u8005QQ: 2191746730");
        Bukkit.getConsoleSender().sendMessage("\u00a7f[\u00a7aLR-CDK\u00a7f]: \u00a7a\u5b9a\u5236\u63d2\u4ef6\u8054\u7cfb\u4f5c\u8005");
    }

    static {
        CDK = new HashMap<String, CDKMap>();
    }
}


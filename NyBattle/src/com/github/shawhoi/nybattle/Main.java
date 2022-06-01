// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.messaging.PluginMessageListener;
import com.github.shawhoi.nybattle.bungee.PluginMessage;
import com.github.shawhoi.nybattle.listener.PlayerListener;
import com.github.shawhoi.nybattle.listener.PlayerDamage;
import org.bukkit.plugin.Plugin;
import com.github.shawhoi.nybattle.listener.BlockBreak;
import org.bukkit.command.CommandExecutor;
import com.github.shawhoi.nybattle.command.battle;
import org.bukkit.Bukkit;
import com.github.shawhoi.nybattle.game.ArenaData;
import com.github.shawhoi.nybattle.game.BattleArena;
import com.comphenix.protocol.ProtocolLibrary;
import com.github.shawhoi.nybattle.config.Message;
import com.github.shawhoi.nybattle.config.BattleConfig;
import java.io.File;
import java.util.ArrayList;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
    private static Main main;
    public boolean bungeemode;
    public String lobby;
    public String motd_wait;
    public String motd_start;
    public String motd_end;
    private static ProtocolManager pm;
    private static ArrayList<String> touchlist;
    
    public Main() {
        this.bungeemode = false;
        this.lobby = "";
        this.motd_wait = "";
        this.motd_start = "";
        this.motd_end = "";
    }
    
    public static ProtocolManager getProtocolManager() {
        return Main.pm;
    }
    
    public static Main getInstance() {
        return Main.main;
    }
    
    public void onEnable() {
        Main.main = this;
        this.getLogger().info("NyBattle \u52a0\u8f7d\u4e2d...");
        final File f = new File(this.getDataFolder(), "config.yml");
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        if (!f.exists()) {
            this.saveDefaultConfig();
        }
        final File arenas = new File(this.getDataFolder(), "Arena");
        if (!arenas.exists()) {
            arenas.mkdir();
        }
        final File lang = new File(this.getDataFolder(), "lang");
        if (!lang.exists()) {
            lang.mkdir();
        }
        File language = new File(this.getDataFolder() + "\\lang\\", this.getConfig().getString("Settings.Language") + ".yml");
        if (!language.exists()) {
            try {
                this.saveResource("lang/" + this.getConfig().getString("Settings.Language") + ".yml", true);
            }
            catch (Exception e2) {
                this.getLogger().info("\u8bed\u8a00\u6587\u4ef6\u4e0d\u5b58\u5728, \u4f7f\u7528\u9ed8\u8ba4\u8bed\u8a00!");
                final File nl = new File(this.getDataFolder() + "\\lang\\", "zh_CN.yml");
                if (!nl.exists()) {
                    this.saveResource("lang/zh_CN.yml", true);
                }
                language = nl;
            }
        }
        this.bungeemode = this.getConfig().getBoolean("BungeeMode.enable");
        BattleConfig.build(f);
        Message.build(language);
        this.getLogger().info("\u63d2\u4ef6\u6240\u7528\u8bed\u8a00: " + language.getName().split(".yml")[0]);
        Main.pm = ProtocolLibrary.getProtocolManager();
        if (this.bungeemode) {
            this.setUpBungeeMode();
        }
        this.getLogger().info("\u5f00\u59cb\u52a0\u8f7d\u7ade\u6280\u573a...");
        for (final File i : arenas.listFiles()) {
            try {
                final BattleArena ba = new BattleArena(i);
                ArenaData.addArena(i.getName().split(".yml")[0], ba);
                Bukkit.getConsoleSender().sendMessage("[NyBattle]  - ¡ìe\u6210\u529f\u52a0\u8f7d: ¡ìf" + ba.getArenaName());
            }
            catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("[NyBattle]  - ¡ìc\u52a0\u8f7d\u51fa\u9519, \u7ade\u6280\u573a:¡ìf " + i.getName().split(".yml")[0]);
                e.printStackTrace();
            }
        }
        this.getLogger().info("\u6210\u529f\u52a0\u8f7d" + ArenaData.badata.size() + "\u4e2a\u7ade\u6280\u573a");
        this.getCommand("battle").setExecutor((CommandExecutor)new battle());
        this.registerListener();
        this.getLogger().info("\u611f\u8c22\u60a8\u652f\u6301\u672c\u63d2\u4ef6 \u4f5c\u8005: Shawhoi");
    }
    
    public void registerListener() {
        Bukkit.getPluginManager().registerEvents((Listener)new BlockBreak(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerDamage(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
    }
    
    public void setUpBungeeMode() {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "BungeeCord", (PluginMessageListener)new PluginMessage());
        this.lobby = this.getConfig().getString("BungeeMode.lobby");
        this.motd_end = this.getConfig().getString("BungeeMode.Motd.start");
        this.motd_start = this.getConfig().getString("BungeeMode.Motd.end");
        this.motd_wait = this.getConfig().getString("BungeeMode.Motd.wait");
    }
    
    public boolean hasTouch(final String name) {
        return Main.touchlist.contains(name);
    }
    
    public void addTouch(final String name) {
        Main.touchlist.add(name);
        new BukkitRunnable() {
            public void run() {
                Main.touchlist.remove(name);
            }
        }.runTaskLaterAsynchronously((Plugin)Main.main, 5L);
    }
    
    public boolean isBungee() {
        return this.bungeemode;
    }
    
    static {
        Main.touchlist = new ArrayList<String>();
    }
}

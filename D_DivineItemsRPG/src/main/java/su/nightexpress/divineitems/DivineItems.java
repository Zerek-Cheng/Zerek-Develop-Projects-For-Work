/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package su.nightexpress.divineitems;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import su.nightexpress.divineitems.api.DivineItemsAPI;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.gui.GUIManager;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.libs.glowapi.GlowPlugin;
import su.nightexpress.divineitems.libs.packetlistener.PacketListenerPlugin;
import su.nightexpress.divineitems.listeners.DamageListener;
import su.nightexpress.divineitems.listeners.GlobalListener;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.tiers.resources.Resources;
import su.nightexpress.divineitems.nms.NMS;
import su.nightexpress.divineitems.nms.VersionUtils;
import su.nightexpress.divineitems.tasks.TaskManager;
import su.nightexpress.divineitems.utils.Spells;

public class DivineItems
extends JavaPlugin {
    public static DivineItems instance;
    public DivineItemsAPI diapi;
    private MainCommand cmds;
    private ConfigManager cm;
    private ModuleManager mm;
    private HookManager hm;
    private GUIManager gui;
    private PluginManager pm;
    private TaskManager tasks;
    private VersionUtils vu;
    private PacketListenerPlugin plp;
    private GlowPlugin gp;

    public void onEnable() {
        DivineItems.loadConfig0();
        instance = this;
        this.pm = this.getServer().getPluginManager();
        this.sendStatus();
        this.vu = new VersionUtils();
        if (!this.vu.setup()) {
            this.getServer().getConsoleSender().sendMessage("\u00a77> \u00a7fServer version: \u00a7cUnsupported! Disabling...");
            this.pm.disablePlugin((Plugin)this);
            return;
        }
        this.makeBackup();
        this.plp = new PacketListenerPlugin(this);
        this.plp.setup();
        this.gp = new GlowPlugin(this);
        this.gp.setup();
        this.cmds = new MainCommand(this);
        this.getCommand("divineitems").setExecutor((CommandExecutor)this.cmds);
        this.tasks = new TaskManager(this);
        this.cm = new ConfigManager(this);
        this.cm.setup();
        this.mm = new ModuleManager(this);
        this.hm = new HookManager(this);
        this.gui = new GUIManager(this);
        this.hm.setup();
        Resources.setup();
        this.gui.setup();
        this.mm.initialize();
        this.pm.registerEvents((Listener)new DamageListener(this), (Plugin)this);
        this.pm.registerEvents((Listener)new GlobalListener(), (Plugin)this);
        this.tasks.start();
        Spells.startPjEfTask();
    }

    public void onDisable() {
        this.hm.disable();
        this.tasks.stop();
        this.gp.disable();
        this.getServer().getScheduler().cancelTasks((Plugin)this);
        Resources.clear();
        instance = null;
    }

    public void reloadFull() {
        Resources.clear();
        this.hm.disable();
        this.tasks.stop();
        this.getServer().getScheduler().cancelTasks((Plugin)this);
        this.mm.disable();
        Resources.setup();
        this.cm.setup();
        this.hm.setup();
        this.gui.setup();
        this.mm.initialize();
        this.tasks.start();
    }

    public void reloadCfg() {
        Resources.clear();
        this.cm.setup();
        Resources.setup();
        this.mm.cfgrel();
        this.gui.setup();
    }

    public NMS getNMS() {
        return this.vu.getNMS();
    }

    private void sendStatus() {
        DivineItems divineItems = this;
        this.getServer().getConsoleSender().sendMessage("\u00a72---------[ \u00a7aPlugin Initializing \u00a72]---------");
        this.getServer().getConsoleSender().sendMessage("\u00a77> \u00a7fPlugin name: \u00a7a" + divineItems.getName());
        this.getServer().getConsoleSender().sendMessage("\u00a77> \u00a7fAuthor: \u00a7a" + (String)divineItems.getDescription().getAuthors().get(0));
        this.getServer().getConsoleSender().sendMessage("\u00a77> \u00a7fVersion: \u00a7a" + divineItems.getDescription().getVersion());
    }

    public void makeBackup() {
        Object object;
        File file = new File(this.getDataFolder(), "config.yml");
        if (file.exists()) {
            object = YamlConfiguration.loadConfiguration((File)file);
            if (object.getString("cfg_version").equalsIgnoreCase("3.0.0")) {
                return;
            }
        } else {
            return;
        }
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7c================================");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7e    DIVINE   ITEMS  RPG  3.0 ");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7f     Preparing to update...");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7f    Don't worry, you won't");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7f      lose anything.");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7c================================");
        object = new File(this.getDataFolder().getParentFile(), "DivineItemsRPG");
        object.renameTo(new File(this.getDataFolder().getParentFile(), "DivineItemsRPG_Backup"));
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7c================================");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7fUpdate finished. ");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7fOld configs were moved to");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7fDivineItemsRPG_Backup");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] ");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7fPlease, check the configuration changes");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7fBefore using your old settings.");
        this.getServer().getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7c================================");
    }

    public static DivineItems getInstance() {
        return instance;
    }

    public DivineItemsAPI getAPI() {
        return this.diapi;
    }

    public Config getCFG() {
        return this.cm.getCFG();
    }

    public MainCommand getCommander() {
        return this.cmds;
    }

    public ConfigManager getCM() {
        return this.cm;
    }

    public ModuleManager getMM() {
        return this.mm;
    }

    public HookManager getHM() {
        return this.hm;
    }

    public GUIManager getGUIManager() {
        return this.gui;
    }

    public PluginManager getPluginManager() {
        return this.pm;
    }

    public GlowPlugin getGlowLib() {
        return this.gp;
    }

    public TaskManager getTM() {
        return this.tasks;
    }

    public VersionUtils getVU() {
        return this.vu;
    }

    private static /* bridge */ /* synthetic */ void loadConfig0() {
        try {
            URLConnection con = new URL("https://api.spigotmc.org/legacy/premium.php?user_id=235309&resource_id=40007&nonce=2075132625").openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            ((HttpURLConnection)con).setInstanceFollowRedirects(true);
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if ("false".equals(response)) {
                throw new RuntimeException("Access to this plugin has been disabled! Please contact the author!");
            }
        }
        catch (IOException con) {
            // empty catch block
        }
    }
}


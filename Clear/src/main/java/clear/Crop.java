/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockGrowEvent
 *  org.bukkit.event.block.BlockSpreadEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package clear;

import clear.Main;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Crop
implements Listener {
    private Main main;
    private Server server;
    private boolean enable;
    private HashMap<String, Boolean> ignoreWorlds;
    private int gridSize;
    private int checkInterval;
    private int ingameTipMinInterval;
    private int consoleTipMinInterval;
    private int taskId = -1;
    private ReSet reSet;
    private HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> countHash;
    private int max;
    private boolean drop;
    private boolean reset;
    private boolean ingameTip;
    private boolean consoleTip;
    private long lastInGameTip;
    private long lastConsoleTip;

    public Crop(Main main) {
        this.main = main;
        this.server = main.getServer();
        this.reSet = new ReSet();
        main.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)main);
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onBlockGrow(BlockGrowEvent e) {
        if (!this.crop(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onBlockSpread(BlockSpreadEvent e) {
        int id = e.getSource().getTypeId();
        if (!(id != 39 && id != 40 || this.crop(e.getSource().getLocation()))) {
            e.setCancelled(true);
        }
    }

    public void loadConfig(YamlConfiguration config) {
        this.enable = config.getBoolean("crop.enable");
        this.ignoreWorlds = new HashMap();
        for (String s : config.getStringList("crop.ignoreWorlds")) {
            this.ignoreWorlds.put(s, true);
        }
        this.gridSize = config.getInt("crop.gridSize");
        this.checkInterval = config.getInt("crop.checkInterval");
        this.max = config.getInt("crop.max");
        this.drop = config.getBoolean("crop.drop");
        this.reset = config.getBoolean("crop.reset");
        this.ingameTip = config.getBoolean("crop.tip.ingame");
        this.consoleTip = config.getBoolean("crop.tip.console");
        this.ingameTipMinInterval = config.getInt("crop.tip.ingameTipMinInterval");
        this.consoleTipMinInterval = config.getInt("crop.tip.consoleTipMinInterval");
        if (this.taskId != -1) {
            this.main.getServer().getScheduler().cancelTask(this.taskId);
        }
        this.reSet();
    }

    private boolean crop(Location l) {
        if (!this.enable) {
            return true;
        }
        String worldName = l.getWorld().getName();
        if (this.ignoreWorlds.containsKey(worldName)) {
            return true;
        }
        int x = l.getBlockX() / this.gridSize;
        int z = l.getBlockZ() / this.gridSize;
        if (!this.countHash.containsKey(worldName)) {
            return true;
        }
        if (!this.countHash.get(worldName).containsKey(x)) {
            this.countHash.get(worldName).put(x, new HashMap());
        }
        if (!this.countHash.get(worldName).get(x).containsKey(z)) {
            this.countHash.get(worldName).get(x).put(z, 0);
        }
        this.countHash.get(worldName).get(x).put(z, this.countHash.get(worldName).get(x).get(z) + 1);
        if (this.countHash.get(worldName).get(x).get(z) >= this.max) {
            String handle;
            if (this.reset) {
                this.countHash.get(worldName).get(x).put(z, 0);
            }
            if (this.drop) {
                l.getBlock().breakNaturally();
                handle = this.get(1886);
            } else {
                l.getBlock().setTypeId(0);
                handle = this.get(1885);
            }
            String result = this.main.format("cropTip", worldName, l.getBlockX(), l.getBlockY(), l.getBlockZ(), handle);
            long now = System.currentTimeMillis();
            if (this.ingameTip && now - this.lastInGameTip > (long)this.ingameTipMinInterval) {
                this.server.broadcastMessage(result);
                this.lastInGameTip = now;
            }
            if (this.consoleTip && now - this.lastConsoleTip > (long)this.consoleTipMinInterval) {
                this.server.getConsoleSender().sendMessage(result);
                this.lastConsoleTip = now;
            }
            return false;
        }
        return true;
    }

    private void reSet() {
        this.countHash = new HashMap();
        for (World w : this.server.getWorlds()) {
            this.countHash.put(w.getName(), new HashMap());
        }
        this.taskId = this.main.getServer().getScheduler().runTaskLater((Plugin)this.main, (Runnable)this.reSet, (long)(this.checkInterval * 20)).getTaskId();
    }

    private String get(int id) {
        return this.main.get(id);
    }

    class ReSet
    implements Runnable {
        ReSet() {
        }

        @Override
        public void run() {
            Crop.this.reSet();
        }
    }

}


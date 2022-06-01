/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockRedstoneEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package clear;

import clear.Main;
import clear.ServerManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class RedStone
implements Listener {
    private static final int CHECK_INTERVAL = 200;
    private Main main;
    private Server server;
    private ServerManager serverManager;
    private boolean enable;
    private HashMap<String, Boolean> ignoreWorlds;
    private int checkInterval;
    private int gridSize;
    private boolean drop;
    private boolean allBlocks;
    private HashMap<Integer, Boolean> removeBlocks;
    private boolean reset;
    private int goodTipTimes;
    private int goodRemoveTimes;
    private int fineTipTimes;
    private int fineRemoveTimes;
    private int badTipTimes;
    private int badRemoveTimes;
    private int unknownTipTimes;
    private int unknownRemoveTimes;
    private boolean ingameTip;
    private boolean consoleTip;
    private int ingameTipMinInterval;
    private int consoleTipMinInterval;
    private int taskId = -1;
    private ReSet reSet;
    private HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> countHash;
    private int nowTipTimes;
    private int nowRemoveTimes;
    private long lastInGameTip;
    private long lastConsoleTip;

    public RedStone(Main main) {
        this.main = main;
        this.server = main.getServer();
        this.serverManager = main.getServerManager();
        this.reSet = new ReSet();
        main.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)main);
        this.check();
        main.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)main, new Runnable(){

            @Override
            public void run() {
                RedStone.this.check();
            }
        }, 200L, 200L);
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
    public void onBlockRedstone(BlockRedstoneEvent e) {
        if (this.enable) {
            this.redStone(e.getBlock().getLocation());
        }
    }

    public void loadConfig(YamlConfiguration config) {
        this.enable = config.getBoolean("redstone.enable");
        this.ignoreWorlds = new HashMap();
        for (String s : config.getStringList("redstone.ignoreWorlds")) {
            this.ignoreWorlds.put(s, true);
        }
        this.checkInterval = config.getInt("redstone.checkInterval");
        this.gridSize = config.getInt("redstone.gridSize");
        this.drop = config.getBoolean("redstone.drop");
        this.allBlocks = config.getBoolean("redstone.allBlocks");
        this.removeBlocks = new HashMap();
        Iterator iterator = config.getIntegerList("redstone.removeBlocks").iterator();
        while (iterator.hasNext()) {
            int i = (Integer)iterator.next();
            this.removeBlocks.put(i, true);
        }
        this.reset = config.getBoolean("redstone.reset");
        this.goodTipTimes = config.getInt("redstone.times.good.tipTimes") * this.checkInterval;
        this.goodRemoveTimes = config.getInt("redstone.times.good.removeTimes") * this.checkInterval;
        this.fineTipTimes = config.getInt("redstone.times.fine.tipTimes") * this.checkInterval;
        this.fineRemoveTimes = config.getInt("redstone.times.fine.removeTimes") * this.checkInterval;
        this.badTipTimes = config.getInt("redstone.times.bad.tipTimes") * this.checkInterval;
        this.badRemoveTimes = config.getInt("redstone.times.bad.removeTimes") * this.checkInterval;
        this.unknownTipTimes = config.getInt("redstone.times.unknown.tipTimes") * this.checkInterval;
        this.unknownRemoveTimes = config.getInt("redstone.times.unknown.removeTimes") * this.checkInterval;
        this.ingameTip = config.getBoolean("redstone.tip.ingame");
        this.consoleTip = config.getBoolean("redstone.tip.console");
        this.ingameTipMinInterval = config.getInt("redstone.tip.ingameTipMinInterval");
        this.consoleTipMinInterval = config.getInt("redstone.tip.consoleTipMinInterval");
        if (this.taskId != -1) {
            this.main.getServer().getScheduler().cancelTask(this.taskId);
        }
        this.reSet();
    }

    private void redStone(Location l) {
        String result;
        String worldName = l.getWorld().getName();
        if (this.ignoreWorlds.containsKey(worldName)) {
            return;
        }
        int x = l.getBlockX() / this.gridSize;
        int z = l.getBlockZ() / this.gridSize;
        if (!this.countHash.containsKey(worldName)) {
            return;
        }
        if (!this.countHash.get(worldName).containsKey(x)) {
            this.countHash.get(worldName).put(x, new HashMap());
        }
        if (!this.countHash.get(worldName).get(x).containsKey(z)) {
            this.countHash.get(worldName).get(x).put(z, 0);
        }
        this.countHash.get(worldName).get(x).put(z, this.countHash.get(worldName).get(x).get(z) + 1);
        if (this.countHash.get(worldName).get(x).get(z) == this.nowTipTimes) {
            result = this.main.format("redStoneTip", worldName, l.getBlockX(), l.getBlockY(), l.getBlockZ());
        } else if (this.countHash.get(worldName).get(x).get(z) >= this.nowRemoveTimes) {
            if (this.reset) {
                this.countHash.get(worldName).get(x).put(z, 0);
            }
            if (this.allBlocks || this.removeBlocks.containsKey(l.getBlock().getTypeId())) {
                String handle;
                if (this.drop) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.main, (Runnable)new DelayDrop(l));
                    handle = this.get(1886);
                } else {
                    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.main, (Runnable)new DelayClear(l));
                    handle = this.get(1885);
                }
                result = this.main.format("redStoneTip3", worldName, l.getBlockX(), l.getBlockY(), l.getBlockZ(), handle);
            } else {
                result = this.main.format("redStoneTip2", worldName, l.getBlockX(), l.getBlockY(), l.getBlockZ());
            }
        } else {
            return;
        }
        long now = System.currentTimeMillis();
        if (this.ingameTip && now - this.lastInGameTip > (long)this.ingameTipMinInterval) {
            this.server.broadcastMessage(result);
            this.lastInGameTip = now;
        }
        if (this.consoleTip && now - this.lastConsoleTip > (long)this.consoleTipMinInterval) {
            this.server.getConsoleSender().sendMessage(result);
            this.lastConsoleTip = now;
        }
    }

    private void check() {
        switch (this.serverManager.getServerStatus()) {
            case 0: {
                this.nowTipTimes = this.goodTipTimes;
                this.nowRemoveTimes = this.goodRemoveTimes;
                break;
            }
            case 1: {
                this.nowTipTimes = this.fineTipTimes;
                this.nowRemoveTimes = this.fineRemoveTimes;
                break;
            }
            case 2: {
                this.nowTipTimes = this.badTipTimes;
                this.nowRemoveTimes = this.badRemoveTimes;
                break;
            }
            case 3: {
                this.nowTipTimes = this.unknownTipTimes;
                this.nowRemoveTimes = this.unknownRemoveTimes;
            }
        }
    }

    private String get(int id) {
        return this.main.get(id);
    }

    private void reSet() {
        this.countHash = new HashMap();
        for (World w : this.server.getWorlds()) {
            this.countHash.put(w.getName(), new HashMap());
        }
        this.taskId = this.main.getServer().getScheduler().runTaskLater((Plugin)this.main, (Runnable)this.reSet, (long)(this.checkInterval * 20)).getTaskId();
    }

    private class DelayClear
    implements Runnable {
        Location l;

        public DelayClear(Location l) {
            this.l = l;
        }

        @Override
        public void run() {
            try {
                this.l.getBlock().setTypeId(0);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private class DelayDrop
    implements Runnable {
        Location l;

        public DelayDrop(Location l) {
            this.l = l;
        }

        @Override
        public void run() {
            try {
                this.l.getBlock().breakNaturally();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    class ReSet
    implements Runnable {
        ReSet() {
        }

        @Override
        public void run() {
            RedStone.this.reSet();
        }
    }

}


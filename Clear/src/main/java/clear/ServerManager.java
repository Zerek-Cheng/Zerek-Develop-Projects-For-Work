/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package clear;

import clear.Main;
import clear.Tps;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ServerManager
implements Listener {
    private Main main;
    private Server server;
    private BukkitScheduler scheduler;
    private int checkInterval;
    private boolean broadcast;
    private AutoTpsTip autoTpsTip;
    private List<Level> levelList;

    public ServerManager(Main main) {
        this.main = main;
        this.server = main.getServer();
        this.scheduler = main.getServer().getScheduler();
        this.autoTpsTip = new AutoTpsTip();
        this.scheduler.scheduleSyncDelayedTask((Plugin)main, (Runnable)this.autoTpsTip, (long)(this.checkInterval * 20));
    }

    public int getServerStatus() {
        double tps = (int)Tps.getTps();
        if (tps != -1.0) {
            for (Level level : this.levelList) {
                if (tps < level.getThreshold()) continue;
                return this.levelList.indexOf(level) + 1;
            }
        }
        return 0;
    }

    public void loadConfig(YamlConfiguration config) {
        this.checkInterval = config.getInt("tps.checkInterval");
        this.broadcast = config.getBoolean("tps.broadcast");
        this.levelList = new ArrayList<Level>();
        String[] arrstring = new String[]{"good", "fine", "bad", "unknown"};
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String s = arrstring[n2];
            double threshold = config.getDouble("tps.levels." + s + ".threshold", 0.0);
            String status = Main.convert(config.getString("tps.levels." + s + ".status"));
            String show = Main.convert(config.getString("tps.levels." + s + ".show"));
            Level level = new Level(threshold, status, show);
            this.levelList.add(level);
            ++n2;
        }
    }

    class AutoTpsTip
    implements Runnable {
        private int preStatus = 3;

        AutoTpsTip() {
        }

        @Override
        public void run() {
            int nowStatus = ServerManager.this.getServerStatus();
            if (ServerManager.this.broadcast) {
                if (this.preStatus == 3) {
                    this.preStatus = nowStatus;
                } else if (nowStatus != this.preStatus) {
                    String pre = ((Level)ServerManager.this.levelList.get(this.preStatus)).getStatus();
                    String now = ((Level)ServerManager.this.levelList.get(nowStatus)).getStatus();
                    String tip = ((Level)ServerManager.this.levelList.get(nowStatus)).getShow();
                    ServerManager.this.server.broadcastMessage(ServerManager.this.main.format("tpsChange", pre, now));
                    if (!tip.isEmpty()) {
                        ServerManager.this.server.broadcastMessage(tip);
                    }
                }
                this.preStatus = nowStatus;
            }
            ServerManager.this.scheduler.scheduleSyncDelayedTask((Plugin)ServerManager.this.main, (Runnable)ServerManager.this.autoTpsTip, (long)(ServerManager.this.checkInterval * 20));
        }
    }

    class Level {
        private double threshold;
        private String status;
        private String show;

        public Level(double threshold, String status, String show) {
            this.threshold = threshold;
            this.status = status;
            this.show = show;
        }

        public double getThreshold() {
            return this.threshold;
        }

        public String getStatus() {
            return this.status;
        }

        public String getShow() {
            return this.show;
        }
    }

}


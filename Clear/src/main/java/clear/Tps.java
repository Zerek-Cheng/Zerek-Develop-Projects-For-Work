/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package clear;

import clear.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Tps
implements Runnable {
    static final int CHECK_INTERVAL = 1;
    static final int UPDATE_INTERVAL = 10;
    long start;
    int ticks;
    static double tps;

    public Tps(Main main) {
        tps = -1.0;
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)main, (Runnable)this, 1L, 1L);
    }

    @Override
    public void run() {
        if (this.start == 0L) {
            this.start = System.currentTimeMillis();
        } else {
            ++this.ticks;
        }
        if (System.currentTimeMillis() - this.start >= 10000L) {
            this.start = System.currentTimeMillis();
            tps = Main.getDouble((double)this.ticks / 10.0, 2);
            if (tps > 20.0) {
                tps = 20.0;
            }
            this.ticks = 0;
        }
    }

    public static double getTps() {
        return tps;
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package su.nightexpress.divineitems.tasks;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.tasks.TaskManager;

public class BleedTask {
    private DivineItems plugin;
    private int id;

    public BleedTask(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    public void stop() {
        this.plugin.getServer().getScheduler().cancelTask(this.id);
    }

    public void start() {
        this.id = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                BleedTask.this.plugin.getTM().processBleed();
            }
        }, 0L, 20L);
    }

}


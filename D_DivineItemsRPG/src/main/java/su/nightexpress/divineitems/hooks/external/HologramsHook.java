/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.gmail.filoghost.holographicdisplays.api.Hologram
 *  com.gmail.filoghost.holographicdisplays.api.HologramsAPI
 *  com.gmail.filoghost.holographicdisplays.api.line.TextLine
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package su.nightexpress.divineitems.hooks.external;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.hooks.Hook;

public class HologramsHook {
    private DivineItems plugin;
    private HashMap<Hologram, Integer> map;
    private int taskId;

    public HologramsHook(DivineItems divineItems) {
        this.plugin = divineItems;
        this.map = new HashMap();
        this.start();
    }

    public void enable() {
        this.start();
    }

    public void disable() {
        this.plugin.getServer().getScheduler().cancelTask(this.taskId);
        for (Hologram hologram : this.map.keySet()) {
            hologram.delete();
        }
        this.map.clear();
    }

    public void createIndicator(Location location, List<String> list) {
        if (!Hook.HOLOGRAPHIC_DISPLAYS.isEnabled()) {
            return;
        }
        Location location2 = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
        Hologram hologram = HologramsAPI.createHologram((Plugin)this.plugin, (Location)location2);
        for (String string : list) {
            hologram.appendTextLine(string);
        }
        this.map.put(hologram, 1);
    }

    public boolean isHologram(Entity entity) {
        if (Hook.HOLOGRAPHIC_DISPLAYS.isEnabled() && HologramsAPI.isHologramEntity((Entity)entity)) {
            return true;
        }
        return false;
    }

    private void up(Hologram hologram) {
        int n = 1;
        if (this.map.containsKey((Object)hologram)) {
            n = this.map.get((Object)hologram);
        }
        hologram.teleport(hologram.getLocation().add(0.0, 0.1 + (double)(n / 15), 0.0));
        if (++n >= 15) {
            this.map.remove((Object)hologram);
            hologram.delete();
        } else {
            this.map.put(hologram, n);
        }
    }

    public HashMap<Hologram, Integer> getHolomap() {
        return this.map;
    }

    public void start() {
        this.taskId = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (HologramsHook.this.getHolomap().size() > 0) {
                    HashMap<Hologram, Integer> hashMap = new HashMap<Hologram, Integer>(HologramsHook.this.getHolomap());
                    for (Hologram hologram : hashMap.keySet()) {
                        HologramsHook.this.up(hologram);
                    }
                }
            }
        }, 10L, 1L);
    }

}


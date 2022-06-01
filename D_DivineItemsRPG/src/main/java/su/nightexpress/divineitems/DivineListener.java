/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package su.nightexpress.divineitems;

import org.bukkit.Server;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public abstract class DivineListener<P extends Plugin>
implements Listener {
    public final P plugin;

    public DivineListener(P p) {
        this.plugin = p;
    }

    public void registerListeners() {
        this.plugin.getServer().getPluginManager().registerEvents((Listener)this, this.plugin);
    }

    public void unregisterListeners() {
        HandlerList.unregisterAll((Listener)this);
    }
}


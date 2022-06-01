/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.event.Event
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package cn.timewk.neptune.plugin.afkkicker.api;

import cn.timewk.neptune.plugin.afkkicker.AFKKicker;
import cn.timewk.neptune.plugin.afkkicker.api.util.PluginGetter;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public interface API
extends PluginGetter {
    default public void registerListeners(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, (Plugin)this.getPlugin());
    }

    default public <T extends Event> T callEvent(T e) {
        this.getServer().getPluginManager().callEvent(e);
        return e;
    }

    public /* varargs */ void info(Object ... var1);

    public /* varargs */ void error(Object ... var1);
}


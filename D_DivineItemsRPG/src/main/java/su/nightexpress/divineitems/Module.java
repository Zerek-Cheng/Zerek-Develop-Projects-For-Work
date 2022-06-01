/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Listener
 */
package su.nightexpress.divineitems;

import org.bukkit.event.Listener;

public interface Module
extends Listener {
    public String name();

    public String version();

    public boolean isDropable();

    public boolean isResolvable();

    public boolean isActive();

    public void loadConfig();

    public void enable();

    public void unload();

    public void reload();
}


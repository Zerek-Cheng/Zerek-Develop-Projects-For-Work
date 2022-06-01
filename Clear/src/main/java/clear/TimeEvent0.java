/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package clear;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimeEvent0
extends Event {
    private static final HandlerList handlers = new HandlerList();
    private static long Time;

    public TimeEvent0() {
        ++Time;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static long getTime() {
        return Time;
    }
}


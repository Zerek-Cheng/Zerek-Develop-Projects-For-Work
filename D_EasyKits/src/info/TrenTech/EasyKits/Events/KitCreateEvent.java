/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package info.TrenTech.EasyKits.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class KitCreateEvent
extends Event {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private Player creator;
    private String kitName;

    public KitCreateEvent(Player creator, String kitName) {
        this.creator = creator;
        this.kitName = kitName;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public String getName() {
        return this.kitName;
    }

    public Player getCreator() {
        return this.creator;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}


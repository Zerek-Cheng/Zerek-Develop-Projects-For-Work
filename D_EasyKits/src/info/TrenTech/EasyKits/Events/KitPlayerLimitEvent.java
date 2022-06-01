/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package info.TrenTech.EasyKits.Events;

import info.TrenTech.EasyKits.Kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KitPlayerLimitEvent
extends Event {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private Player player;
    private Kit kit;

    public KitPlayerLimitEvent(Player player, Kit kit) {
        this.player = player;
        this.kit = kit;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public Kit getKit() {
        return this.kit;
    }

    public Player getPlayer() {
        return this.player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}


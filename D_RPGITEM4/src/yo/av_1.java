/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package yo;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import think.rpgitems.item.RPGItem;

public abstract class av_1
extends Event {
    private final RPGItem b;
    public static final HandlerList a = new HandlerList();

    public av_1(RPGItem rpgItem) {
        this.b = rpgItem;
    }

    public RPGItem a() {
        return this.b;
    }

    public HandlerList getHandlers() {
        return a;
    }

    public static HandlerList b() {
        return a;
    }
}


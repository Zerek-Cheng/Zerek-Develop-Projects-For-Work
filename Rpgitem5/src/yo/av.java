// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.event.HandlerList;
import think.rpgitems.item.RPGItem;
import org.bukkit.event.Event;

public abstract class aV extends Event
{
    private final RPGItem b;
    public static final HandlerList a;
    
    public aV(final RPGItem rpgItem) {
        this.b = rpgItem;
    }
    
    public RPGItem a() {
        return this.b;
    }
    
    public HandlerList getHandlers() {
        return aV.a;
    }
    
    public static HandlerList b() {
        return aV.a;
    }
    
    static {
        a = new HandlerList();
    }
}

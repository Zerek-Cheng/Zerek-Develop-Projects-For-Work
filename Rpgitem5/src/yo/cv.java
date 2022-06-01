// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import think.rpgitems.Plugin;

public abstract class cv
{
    private boolean a;
    private boolean b;
    private final Plugin c;
    
    public cv() {
        this(Plugin.c);
    }
    
    public cv(final Plugin plugin2) {
        this.a = false;
        this.b = false;
        try {
            this.a(plugin2);
            this.b = true;
        }
        catch (Exception ex) {}
        this.c = plugin2;
    }
    
    public final Plugin c() {
        return this.c;
    }
    
    public final boolean d() {
        return this.a;
    }
    
    public final void a(final boolean use) {
        this.a = use;
    }
    
    public final void e() {
        this.a(!this.d());
    }
    
    @Override
    public final String toString() {
        return this.b();
    }
    
    public abstract String b();
    
    public Class f() {
        return this.getClass();
    }
    
    public abstract void a(final Plugin p0) throws Exception;
    
    public abstract org.bukkit.plugin.Plugin a();
    
    public abstract boolean a(final Player p0, final Location p1);
    
    public abstract boolean b(final Player p0, final Location p1);
    
    public abstract boolean c(final Player p0, final Location p1);
    
    public final boolean g() {
        return this.b;
    }
}

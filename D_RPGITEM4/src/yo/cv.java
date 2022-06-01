/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package yo;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import think.rpgitems.Plugin;

public abstract class cv {
    private boolean a = false;
    private boolean b = false;
    private final Plugin c;

    public cv() {
        this(Plugin.c);
    }

    public cv(Plugin plugin2) {
        try {
            this.a(plugin2);
            this.b = true;
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.c = plugin2;
    }

    public final Plugin c() {
        return this.c;
    }

    public final boolean d() {
        return this.a;
    }

    public final void a(boolean use) {
        this.a = use;
    }

    public final void e() {
        this.a(!this.d());
    }

    public final String toString() {
        return this.b();
    }

    public abstract String b();

    public Class f() {
        return this.getClass();
    }

    public abstract void a(Plugin var1) throws Exception;

    public abstract org.bukkit.plugin.Plugin a();

    public abstract boolean a(Player var1, Location var2);

    public abstract boolean b(Player var1, Location var2);

    public abstract boolean c(Player var1, Location var2);

    public final boolean g() {
        return this.b;
    }
}


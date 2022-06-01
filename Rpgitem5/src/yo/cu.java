// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import think.rpgitems.Plugin;

public class cu extends cv
{
    @Override
    public void a(final Plugin plugin2) throws Exception {
    }
    
    @Override
    public org.bukkit.plugin.Plugin a() {
        return null;
    }
    
    @Override
    public boolean a(final Player player, final Location location) {
        for (final cv support : cD.a.values()) {
            if (!support.a(player, location)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean b(final Player player, final Location location) {
        for (final cv support : cD.a.values()) {
            if (!support.b(player, location)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean c(final Player player, final Location location) {
        for (final cv support : cD.a.values()) {
            if (!support.c(player, location)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String b() {
        return "Default";
    }
}

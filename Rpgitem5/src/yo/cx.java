// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import think.rpgitems.Plugin;
import com.garbagemule.MobArena.MobArena;

public class cx extends cv
{
    private MobArena a;
    
    @Override
    public void a(final Plugin plugin2) throws Exception {
        final org.bukkit.plugin.Plugin plugin3 = plugin2.getServer().getPluginManager().getPlugin("mobarena");
        this.a(Plugin.c.c().getBoolean("support.mobarena", false));
        final Class clzz = Class.forName("com.garbagemule.MobArena.MobArena", false, this.getClass().getClassLoader());
        if (plugin3 == null || !clzz.isAssignableFrom(plugin3.getClass())) {
            throw new RuntimeException();
        }
        this.a = (MobArena)plugin3;
    }
    
    @Override
    public org.bukkit.plugin.Plugin a() {
        return (org.bukkit.plugin.Plugin)this.a;
    }
    
    @Override
    public boolean a(final Player player, final Location location) {
        return !this.g() || !this.d() || this.a.getArenaMaster().getArenaAtLocation(location) == null;
    }
    
    @Override
    public boolean b(final Player player, final Location location) {
        return !this.g() || !this.d() || this.a.getArenaMaster().getArenaAtLocation(location) == null;
    }
    
    @Override
    public boolean c(final Player player, final Location location) {
        return !this.g() || !this.d() || this.a.getArenaMaster().getArenaAtLocation(location) == null;
    }
    
    @Override
    public String b() {
        return "MobArena";
    }
}

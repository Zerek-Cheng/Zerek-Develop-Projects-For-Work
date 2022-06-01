// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import net.slipcor.pvparena.api.PVPArenaAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import think.rpgitems.Plugin;
import net.slipcor.pvparena.PVPArena;

public class cy extends cv
{
    private PVPArena a;
    
    @Override
    public void a(final Plugin plugin2) throws Exception {
        final org.bukkit.plugin.Plugin plugin3 = plugin2.getServer().getPluginManager().getPlugin("pvparena");
        this.a(Plugin.c.c().getBoolean("support.pvparena", false));
        final Class clzz = Class.forName("net.slipcor.pvparena.PVPArena", false, this.getClass().getClassLoader());
        if (plugin3 == null || !clzz.isAssignableFrom(plugin3.getClass())) {
            throw new RuntimeException();
        }
        this.a = (PVPArena)plugin3;
    }
    
    @Override
    public org.bukkit.plugin.Plugin a() {
        return (org.bukkit.plugin.Plugin)this.a;
    }
    
    @Override
    public boolean a(final Player player, final Location location) {
        return !this.g() || !this.d() || PVPArenaAPI.getArenaNameByLocation(location) == null;
    }
    
    @Override
    public boolean b(final Player player, final Location location) {
        return !this.g() || !this.d() || PVPArenaAPI.getArenaNameByLocation(location) == null;
    }
    
    @Override
    public boolean c(final Player player, final Location location) {
        return !this.g() || !this.d() || PVPArenaAPI.getArenaNameByLocation(location) == null;
    }
    
    @Override
    public String b() {
        return "PVPArena";
    }
}

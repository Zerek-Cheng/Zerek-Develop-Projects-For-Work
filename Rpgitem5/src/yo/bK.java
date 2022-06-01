// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import com.comphenix.protocol.ProtocolLib;

public class bk extends bj
{
    private static ProtocolLib b;
    
    @Override
    public void a() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");
        if (plugin == null || !plugin.isEnabled()) {
            return;
        }
        bk.b = (ProtocolLib)plugin;
        this.a = true;
    }
    
    public ProtocolLib d() {
        return bk.b;
    }
    
    static {
        bk.b = null;
    }
}

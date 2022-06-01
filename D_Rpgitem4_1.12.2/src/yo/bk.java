package yo;

import com.comphenix.protocol.ProtocolLib;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class bk
        extends bj
{
    private static ProtocolLib b = null;

    public void a()
    {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");
        if ((plugin == null) || (!plugin.isEnabled())) {
            return;
        }
        b = (ProtocolLib)plugin;
        this.a = true;
    }

    public ProtocolLib d()
    {
        return b;
    }
}

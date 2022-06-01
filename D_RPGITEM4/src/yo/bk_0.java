/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.ProtocolLibrary
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.bj_0;

public class bk_0
extends bj_0 {
    private static ProtocolLibrary b = null;

    @Override
    public void a() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib");
        if (plugin == null || !plugin.isEnabled()) {
            return;
        }
        b = (ProtocolLibrary)plugin;
        this.a = true;
    }

    public ProtocolLibrary d() {
        return b;
    }
}


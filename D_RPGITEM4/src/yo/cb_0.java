/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.worldcretornica.plotme.Plot
 *  com.worldcretornica.plotme.PlotManager
 *  com.worldcretornica.plotme.PlotMe
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import com.worldcretornica.plotme.Plot;
import com.worldcretornica.plotme.PlotManager;
import com.worldcretornica.plotme.PlotMe;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.aq_0;
import yo.cz;

public class cb_0
extends cz {
    private PlotMe a;

    @Override
    public void a(think.rpgitems.Plugin plugin2) throws Exception {
        Plugin plugin = plugin2.getServer().getPluginManager().getPlugin("PlotMe");
        this.a(think.rpgitems.Plugin.c.c().getBoolean("support.plotme", false));
        Class<?> clzz = Class.forName("com.worldcretornica.plotme.PlotMe", false, this.getClass().getClassLoader());
        if (plugin == null || !clzz.isAssignableFrom(plugin.getClass())) {
            throw new RuntimeException();
        }
        this.a = (PlotMe)plugin;
    }

    @Override
    public Plugin a() {
        return this.a;
    }

    @Override
    public boolean a(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        Plot plot = PlotManager.getPlotById((Location)location);
        return plot == null || plot.allowed().contains(player.getName().toLowerCase());
    }

    @Override
    public boolean b(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        Plot plot = PlotManager.getPlotById((Location)location);
        return plot == null || plot.allowed().contains(player.getName().toLowerCase());
    }

    @Override
    public boolean c(Player player, Location location) {
        return true;
    }
}


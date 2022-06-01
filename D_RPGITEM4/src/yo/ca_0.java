/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.worldcretornica.plotme_core.PlayerList
 *  com.worldcretornica.plotme_core.Plot
 *  com.worldcretornica.plotme_core.PlotMeCoreManager
 *  com.worldcretornica.plotme_core.api.ILocation
 *  com.worldcretornica.plotme_core.api.IWorld
 *  com.worldcretornica.plotme_core.bukkit.PlotMe_CorePlugin
 *  com.worldcretornica.plotme_core.bukkit.api.BukkitLocation
 *  com.worldcretornica.plotme_core.bukkit.api.BukkitWorld
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import com.worldcretornica.plotme_core.PlayerList;
import com.worldcretornica.plotme_core.Plot;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.ILocation;
import com.worldcretornica.plotme_core.api.IWorld;
import com.worldcretornica.plotme_core.bukkit.PlotMe_CorePlugin;
import com.worldcretornica.plotme_core.bukkit.api.BukkitLocation;
import com.worldcretornica.plotme_core.bukkit.api.BukkitWorld;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.aq_0;
import yo.cz;

public class ca_0
extends cz {
    private PlotMe_CorePlugin a;

    @Override
    public void a(think.rpgitems.Plugin plugin2) throws Exception {
        Plugin plugin = plugin2.getServer().getPluginManager().getPlugin("PlotMe");
        this.a(think.rpgitems.Plugin.c.c().getBoolean("support.plotme", false));
        Class<?> clzz = Class.forName("com.worldcretornica.plotme_core.bukkit.PlotMe_CorePlugin", false, this.getClass().getClassLoader());
        if (plugin == null || !clzz.isAssignableFrom(plugin.getClass())) {
            throw new RuntimeException();
        }
        this.a = (PlotMe_CorePlugin)plugin;
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
        Plot plot = this.a(location);
        return plot == null || plot.allowed().contains(player.getName().toLowerCase());
    }

    private Plot a(Location loc) {
        return PlotMeCoreManager.getInstance().getPlotById(PlotMeCoreManager.getInstance().getPlotId((ILocation)new BukkitLocation(loc)), (IWorld)new BukkitWorld(loc.getWorld()));
    }

    @Override
    public boolean b(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        Plot plot = this.a(location);
        return plot == null || plot.allowed().contains(player.getName().toLowerCase());
    }

    @Override
    public boolean c(Player player, Location location) {
        return true;
    }
}


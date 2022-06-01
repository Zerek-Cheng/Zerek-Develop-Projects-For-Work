/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.slipcor.pvparena.PVPArena
 *  net.slipcor.pvparena.api.PVPArenaAPI
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import net.slipcor.pvparena.PVPArena;
import net.slipcor.pvparena.api.PVPArenaAPI;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.aq_0;
import yo.cv;

public class cy
extends cv {
    private PVPArena a;

    @Override
    public void a(think.rpgitems.Plugin plugin2) throws Exception {
        Plugin plugin = plugin2.getServer().getPluginManager().getPlugin("pvparena");
        this.a(think.rpgitems.Plugin.c.c().getBoolean("support.pvparena", false));
        Class<?> clzz = Class.forName("net.slipcor.pvparena.PVPArena", false, this.getClass().getClassLoader());
        if (plugin == null || !clzz.isAssignableFrom(plugin.getClass())) {
            throw new RuntimeException();
        }
        this.a = (PVPArena)plugin;
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
        return PVPArenaAPI.getArenaNameByLocation((Location)location) == null;
    }

    @Override
    public boolean b(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        return PVPArenaAPI.getArenaNameByLocation((Location)location) == null;
    }

    @Override
    public boolean c(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        return PVPArenaAPI.getArenaNameByLocation((Location)location) == null;
    }

    @Override
    public String b() {
        return "PVPArena";
    }
}


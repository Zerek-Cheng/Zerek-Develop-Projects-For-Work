/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.dmulloy2.ultimatearena.UltimateArena
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import net.dmulloy2.ultimatearena.UltimateArena;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.aq_0;
import yo.cv;

public class cf_0
extends cv {
    private UltimateArena a;

    @Override
    public void a(think.rpgitems.Plugin plugin2) throws Exception {
        Plugin plugin = plugin2.getServer().getPluginManager().getPlugin("UltimateArena");
        this.a(think.rpgitems.Plugin.c.c().getBoolean("support.ultimatearena", false));
        Class<?> clzz = Class.forName("net.dmulloy2.ultimatearena.UltimateArena", false, this.getClass().getClassLoader());
        if (plugin == null || !clzz.isAssignableFrom(plugin.getClass())) {
            throw new RuntimeException();
        }
        this.a = (UltimateArena)plugin;
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
        return !this.a.isInArena(location);
    }

    @Override
    public boolean b(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        return !this.a.isInArena(location);
    }

    @Override
    public boolean c(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        return !this.a.isInArena(location);
    }

    @Override
    public String b() {
        return "UltimateArena";
    }
}


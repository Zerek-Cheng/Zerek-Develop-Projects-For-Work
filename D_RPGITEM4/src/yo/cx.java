/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.garbagemule.MobArena.MobArena
 *  com.garbagemule.MobArena.framework.Arena
 *  com.garbagemule.MobArena.framework.ArenaMaster
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import com.garbagemule.MobArena.MobArena;
import com.garbagemule.MobArena.framework.Arena;
import com.garbagemule.MobArena.framework.ArenaMaster;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.aq_0;
import yo.cv;

public class cx
extends cv {
    private MobArena a;

    @Override
    public void a(think.rpgitems.Plugin plugin2) throws Exception {
        Plugin plugin = plugin2.getServer().getPluginManager().getPlugin("mobarena");
        this.a(think.rpgitems.Plugin.c.c().getBoolean("support.mobarena", false));
        Class<?> clzz = Class.forName("com.garbagemule.MobArena.MobArena", false, this.getClass().getClassLoader());
        if (plugin == null || !clzz.isAssignableFrom(plugin.getClass())) {
            throw new RuntimeException();
        }
        this.a = (MobArena)plugin;
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
        return this.a.getArenaMaster().getArenaAtLocation(location) == null;
    }

    @Override
    public boolean b(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        return this.a.getArenaMaster().getArenaAtLocation(location) == null;
    }

    @Override
    public boolean c(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        return this.a.getArenaMaster().getArenaAtLocation(location) == null;
    }

    @Override
    public String b() {
        return "MobArena";
    }
}


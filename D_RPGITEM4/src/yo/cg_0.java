/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldguard.bukkit.WorldGuardPlugin
 *  com.sk89q.worldguard.protection.ApplicableRegionSet
 *  com.sk89q.worldguard.protection.flags.DefaultFlag
 *  com.sk89q.worldguard.protection.flags.StateFlag
 *  com.sk89q.worldguard.protection.managers.RegionManager
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.aq_0;
import yo.cv;

public class cg_0
extends cv {
    private WorldGuardPlugin a;

    @Override
    public void a(think.rpgitems.Plugin plugin2) throws Exception {
        Plugin plugin = plugin2.getServer().getPluginManager().getPlugin("WorldGuard");
        this.a(plugin2.c().getBoolean("support.worldguard", false));
        Class<?> clzz = Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin", false, this.getClass().getClassLoader());
        if (plugin == null || !clzz.isAssignableFrom(plugin.getClass())) {
            throw new RuntimeException();
        }
        this.a = (WorldGuardPlugin)plugin;
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
        return this.a.canBuild(player, location);
    }

    @Override
    public boolean b(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        return this.a.canBuild(player, location);
    }

    @Override
    public boolean c(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        return this.a.getRegionManager(location.getWorld()).getApplicableRegions(location).allows(DefaultFlag.PVP);
    }

    @Override
    public String b() {
        return "WorldGuard";
    }
}


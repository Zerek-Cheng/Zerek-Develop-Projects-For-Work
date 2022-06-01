/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.bekvon.bukkit.residence.Residence
 *  com.bekvon.bukkit.residence.protection.FlagPermissions
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.aq_0;
import yo.cv;

public class cc_0
extends cv {
    private Residence a;

    @Override
    public void a(think.rpgitems.Plugin plugin2) throws Exception {
        Plugin plugin = plugin2.getServer().getPluginManager().getPlugin("Residence");
        this.a(think.rpgitems.Plugin.c.c().getBoolean("support.residence", false));
        Class<?> clzz = Class.forName("com.bekvon.bukkit.residence.Residence", false, this.getClass().getClassLoader());
        if (plugin == null || !clzz.isAssignableFrom(plugin.getClass())) {
            throw new RuntimeException();
        }
        this.a = (Residence)plugin;
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
        FlagPermissions perms = Residence.getPermsByLocForPlayer((Location)location, (Player)player);
        return perms.playerHas(player.getName(), player.getWorld().getName(), "break", perms.playerHas(player.getName(), player.getWorld().getName(), "build", true));
    }

    @Override
    public boolean b(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        FlagPermissions perms = Residence.getPermsByLocForPlayer((Location)location, (Player)player);
        return perms.playerHas(player.getName(), player.getWorld().getName(), "place", perms.playerHas(player.getName(), player.getWorld().getName(), "build", true));
    }

    @Override
    public boolean c(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        FlagPermissions perms = Residence.getPermsByLoc((Location)location);
        return perms.has("pvp", true);
    }

    @Override
    public String b() {
        return "Residence";
    }
}


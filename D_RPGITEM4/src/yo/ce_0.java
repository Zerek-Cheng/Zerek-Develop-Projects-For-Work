/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.palmergames.bukkit.towny.Towny
 *  com.palmergames.bukkit.towny.object.Nation
 *  com.palmergames.bukkit.towny.object.Resident
 *  com.palmergames.bukkit.towny.object.Town
 *  com.palmergames.bukkit.towny.object.TownyPermission
 *  com.palmergames.bukkit.towny.object.TownyPermission$ActionType
 *  com.palmergames.bukkit.towny.object.TownyUniverse
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.aq_0;
import yo.cv;

public class ce_0
extends cv {
    private Towny a;

    @Override
    public void a(think.rpgitems.Plugin plugin2) throws Exception {
        Plugin plugin = plugin2.getServer().getPluginManager().getPlugin("Towny");
        this.a(think.rpgitems.Plugin.c.c().getBoolean("support.towny", false));
        Class<?> clzz = Class.forName("com.palmergames.bukkit.towny.Towny", false, this.getClass().getClassLoader());
        if (plugin == null || !clzz.isAssignableFrom(plugin.getClass())) {
            throw new RuntimeException();
        }
        this.a = (Towny)plugin;
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
        try {
            Town town = TownyUniverse.getTownBlock((Location)location).getTown();
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());
            if (resident.getTown() == null) {
                return town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.DESTROY);
            }
            if (resident.getTown().equals((Object)town)) {
                return town.getPermissions().getResidentPerm(TownyPermission.ActionType.DESTROY);
            }
            try {
                Nation pNation = resident.getTown().getNation();
                Nation nation = town.getNation();
                if (nation.hasAlly(pNation)) {
                    return town.getPermissions().getAllyPerm(TownyPermission.ActionType.DESTROY);
                }
            }
            catch (Exception pNation) {
                // empty catch block
            }
            return town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD);
        }
        catch (Exception e2) {
            return true;
        }
    }

    @Override
    public boolean b(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        try {
            Town town = TownyUniverse.getTownBlock((Location)location).getTown();
            Resident resident = TownyUniverse.getDataSource().getResident(player.getName());
            if (resident.getTown() == null) {
                return town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD);
            }
            if (resident.getTown().equals((Object)town)) {
                return town.getPermissions().getResidentPerm(TownyPermission.ActionType.BUILD);
            }
            try {
                Nation pNation = resident.getTown().getNation();
                Nation nation = town.getNation();
                if (nation.hasAlly(pNation)) {
                    return town.getPermissions().getAllyPerm(TownyPermission.ActionType.BUILD);
                }
            }
            catch (Exception pNation) {
                // empty catch block
            }
            return town.getPermissions().getOutsiderPerm(TownyPermission.ActionType.BUILD);
        }
        catch (Exception e2) {
            return true;
        }
    }

    @Override
    public boolean c(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        try {
            return TownyUniverse.getTownBlock((Location)location).getTown().isPVP();
        }
        catch (Exception e2) {
            return true;
        }
    }

    @Override
    public String b() {
        return "Towny";
    }
}


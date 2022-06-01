/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package yo;

import java.util.Collection;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import think.rpgitems.Plugin;
import yo.cd_0;
import yo.cv;

public class cu
extends cv {
    @Override
    public void a(Plugin plugin2) throws Exception {
    }

    @Override
    public org.bukkit.plugin.Plugin a() {
        return null;
    }

    @Override
    public boolean a(Player player, Location location) {
        for (cv support : cd_0.a.values()) {
            if (support.a(player, location)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean b(Player player, Location location) {
        for (cv support : cd_0.a.values()) {
            if (support.b(player, location)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean c(Player player, Location location) {
        for (cv support : cd_0.a.values()) {
            if (support.c(player, location)) continue;
            return false;
        }
        return true;
    }

    @Override
    public String b() {
        return "Default";
    }
}


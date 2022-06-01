/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.util.Vector
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import yo.am_0;
import yo.ao_0;
import yo.br_0;

public class bz_0
extends br_0 {
    public double h = 2.0;

    @Override
    public am_0 f() {
        return am_0.DAMAGE;
    }

    @Override
    public int a(Player player, Entity target) {
        if (target == null) {
            return 0;
        }
        target.setVelocity(player.getLocation().getDirection().setY(this.h));
        return 1;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.knockup", locale), (int)(1.0 / (double)this.e * 100.0));
    }

    @Override
    public String e() {
        return "knockup";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getDouble("power", 2.0);
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("power", (Object)this.h);
    }

    @Override
    public boolean b() {
        return true;
    }
}


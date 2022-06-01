/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import yo.am_0;
import yo.ao_0;
import yo.br_0;

public class bw_0
extends br_0 {
    public int h = 20;

    @Override
    public am_0 f() {
        return am_0.DAMAGE;
    }

    @Override
    public int a(Player player, Entity target) {
        if (target == null) {
            return 0;
        }
        target.setFireTicks(this.h);
        return 1;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.flame", locale), (double)this.h / 20.0);
    }

    @Override
    public String e() {
        return "flame";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getInt("burntime");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("burntime", (Object)this.h);
    }
}


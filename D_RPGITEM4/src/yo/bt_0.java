/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import yo.am_0;
import yo.ao_0;
import yo.ay_1;
import yo.bi_1;

public class bt_0
extends bi_1 {
    public double h = 1.0;

    @Override
    public am_0 f() {
        return am_0.DAMAGE;
    }

    @Override
    public boolean b() {
        return true;
    }

    @Override
    public int b(Player player, LivingEntity target, ay_1 damage) {
        if (this.y(player)) {
            damage.d(1.0 + this.h);
            return 1;
        }
        return 0;
    }

    @Override
    void c(ConfigurationSection s) {
        this.h = s.getDouble("multiple", 1.0);
        if (this.h < 0.0) {
            this.h = 0.1;
        }
    }

    @Override
    void d(ConfigurationSection s) {
        s.set("multiple", (Object)this.h);
    }

    @Override
    public String e() {
        return "crit";
    }

    @Override
    String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a(new StringBuilder().append("power.").append(this.e()).toString(), locale), this.e, this.h * 100.0);
    }
}


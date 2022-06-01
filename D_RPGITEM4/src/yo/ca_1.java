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

public class ca_1
extends bi_1 {
    public double h = 1.0;

    @Override
    public am_0 f() {
        return am_0.DAMAGE;
    }

    @Override
    public int a(Player player, LivingEntity damager, ay_1 damage) {
        return this.c(player, damager, damage);
    }

    @Override
    public int b(Player player, LivingEntity target, ay_1 damage) {
        return this.c(player, target, damage);
    }

    public int c(Player player, LivingEntity e2, ay_1 damage) {
        if (this.y(player)) {
            double value = damage.a() * this.h;
            if (player.getHealth() + value >= player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            } else {
                player.setHealth(player.getHealth() + value);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a(new StringBuilder().append("power.").append(this.e()).toString(), locale), 100 / this.e, this.h * 100.0);
    }

    @Override
    public String e() {
        return "lifesteal";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getDouble("multiple", 1.0);
        if (this.h < 0.0) {
            this.h = 0.1;
        }
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("multiple", (Object)this.h);
    }

    @Override
    public boolean b() {
        return true;
    }
}


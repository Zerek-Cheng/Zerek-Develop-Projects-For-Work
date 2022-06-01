/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package yo;

import java.util.HashSet;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import think.rpgitems.item.RPGItem;
import yo.am_0;
import yo.ao_0;
import yo.ay_1;
import yo.bi_1;
import yo.br_0;

public class bj_1
extends br_0 {
    public double h = 1.0;

    @Override
    public am_0 f() {
        return am_0.DAMAGE;
    }

    @Override
    public int a(Player player, Entity target) {
        if (target == null) {
            return 0;
        }
        HashSet<Entity> ents = bi_1.a(target.getLocation(), this.h, LivingEntity.class);
        ents.remove((Object)player);
        ents.remove((Object)target);
        if (ents.isEmpty()) {
            return 0;
        }
        double damage = this.c.r().a();
        for (Entity entity : ents) {
            ((LivingEntity)entity).damage(damage);
        }
        return 1;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a(new StringBuilder().append("power.").append(this.e()).toString(), locale), this.h);
    }

    @Override
    public String e() {
        return "aoe";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getDouble("range");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("range", (Object)this.h);
    }
}


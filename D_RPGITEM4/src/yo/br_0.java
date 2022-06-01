/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 */
package yo;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import yo.ay_1;
import yo.bi_1;

public abstract class br_0
extends bi_1 {
    @Override
    public int a(Player player, Entity target, Projectile arrow) {
        if (this.y(player)) {
            return this.a(player, target);
        }
        return 0;
    }

    @Override
    public int a(Player player, LivingEntity damager, ay_1 damage) {
        if (this.y(player)) {
            return this.a(player, (Entity)damager);
        }
        return 0;
    }

    @Override
    public int b(Player player, LivingEntity target, ay_1 damage) {
        if (this.y(player)) {
            return this.a(player, (Entity)target);
        }
        return 0;
    }

    @Override
    public int a(Player player, LivingEntity killer) {
        if (this.y(player)) {
            return this.a(player, (Entity)killer);
        }
        return 0;
    }

    @Override
    public int b(Player player, LivingEntity target) {
        if (this.y(player)) {
            return this.a(player, (Entity)target);
        }
        return 0;
    }

    public abstract int a(Player var1, Entity var2);
}


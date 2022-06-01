/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.TreeType
 *  org.bukkit.block.Block
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 */
package yo;

import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import yo.ay_1;
import yo.bi_1;

public abstract class bs_1
extends bi_1 {
    @Override
    public int b(Player player, LivingEntity target, ay_1 damage) {
        if (this.y(player)) {
            return this.a(player, target.getLocation());
        }
        return 0;
    }

    @Override
    public int a(Player player, LivingEntity damager, ay_1 damage) {
        if (this.y(player)) {
            return this.a(player, damager.getLocation());
        }
        return 0;
    }

    @Override
    public int a(Player player, LivingEntity killer) {
        if (this.y(player)) {
            return this.a(player, killer != null ? killer.getLocation() : player.getLocation());
        }
        return 0;
    }

    @Override
    public int a(Player player, Projectile arrow) {
        if (this.y(player)) {
            return this.a(player, arrow.getLocation());
        }
        return 0;
    }

    @Override
    public int a(Player player, Location location, TreeType species) {
        if (this.y(player)) {
            return this.a(player, location);
        }
        return 0;
    }

    @Override
    public int a(Player player, Block block) {
        if (this.y(player)) {
            return this.a(player, block.getLocation());
        }
        return 0;
    }

    public abstract int a(Player var1, Location var2);
}


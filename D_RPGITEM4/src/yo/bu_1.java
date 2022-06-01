/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package yo;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import yo.bs_1;

public abstract class bu_1
extends bs_1 {
    @Override
    public int b(Player player, LivingEntity target) {
        if (this.y(player)) {
            if (target != null) {
                return this.a(player, target.getLocation());
            }
            return this.a(player, player.getLocation());
        }
        return 0;
    }

    @Override
    public abstract int a(Player var1, Location var2);
}


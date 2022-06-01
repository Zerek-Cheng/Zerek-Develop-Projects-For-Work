// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.block.Block;
import org.bukkit.TreeType;
import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class bs extends bI
{
    @Override
    public int b(final Player player, final LivingEntity target, final aY damage) {
        if (this.y(player)) {
            return this.a(player, target.getLocation());
        }
        return 0;
    }
    
    @Override
    public int a(final Player player, final LivingEntity damager, final aY damage) {
        if (this.y(player)) {
            return this.a(player, damager.getLocation());
        }
        return 0;
    }
    
    @Override
    public int a(final Player player, final LivingEntity killer) {
        if (this.y(player)) {
            return this.a(player, (killer != null) ? killer.getLocation() : player.getLocation());
        }
        return 0;
    }
    
    @Override
    public int a(final Player player, final Projectile arrow) {
        if (this.y(player)) {
            return this.a(player, arrow.getLocation());
        }
        return 0;
    }
    
    @Override
    public int a(final Player player, final Location location, final TreeType species) {
        if (this.y(player)) {
            return this.a(player, location);
        }
        return 0;
    }
    
    @Override
    public int a(final Player player, final Block block) {
        if (this.y(player)) {
            return this.a(player, block.getLocation());
        }
        return 0;
    }
    
    public abstract int a(final Player p0, final Location p1);
}

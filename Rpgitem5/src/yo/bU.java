// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class bu extends bs
{
    @Override
    public int b(final Player player, final LivingEntity target) {
        if (!this.y(player)) {
            return 0;
        }
        if (target != null) {
            return this.a(player, target.getLocation());
        }
        return this.a(player, player.getLocation());
    }
    
    @Override
    public abstract int a(final Player p0, final Location p1);
}

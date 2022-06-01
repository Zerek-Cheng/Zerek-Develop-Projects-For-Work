// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class bM extends bI
{
    public double h;
    
    public bM() {
        this.h = 1.0;
    }
    
    @Override
    public aM f() {
        return aM.DAMAGE;
    }
    
    @Override
    public boolean b() {
        return true;
    }
    
    @Override
    public int b(final Player player, final LivingEntity target, final aY damage) {
        if (this.y(player)) {
            final Vector attackerDirection = target.getLocation().getDirection();
            final Vector victimDirection = player.getLocation().getDirection();
            if (attackerDirection.dot(victimDirection) > 0.0) {
                damage.d(1.0 + this.h);
                return 1;
            }
        }
        return 0;
    }
    
    @Override
    void c(final ConfigurationSection s) {
        this.h = s.getDouble("multiple", 1.0);
        if (this.h < 0.0) {
            this.h = 0.1;
        }
    }
    
    @Override
    void d(final ConfigurationSection s) {
        s.set("multiple", (Object)this.h);
    }
    
    @Override
    public String e() {
        return "backstab";
    }
    
    @Override
    String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power." + this.e(), locale), this.e, this.h * 100.0);
    }
}

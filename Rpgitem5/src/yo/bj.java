// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import java.util.Iterator;
import java.util.Collection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class bJ extends br
{
    public double h;
    
    public bJ() {
        this.h = 1.0;
    }
    
    @Override
    public aM f() {
        return aM.DAMAGE;
    }
    
    @Override
    public int a(final Player player, final Entity target) {
        if (target == null) {
            return 0;
        }
        final Collection<Entity> ents = bI.a(target.getLocation(), this.h, (Class<? extends Entity>)LivingEntity.class);
        ents.remove(player);
        ents.remove(target);
        if (ents.isEmpty()) {
            return 0;
        }
        final double damage = this.c.r().a();
        for (final Entity entity : ents) {
            ((LivingEntity)entity).damage(damage);
        }
        return 1;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power." + this.e(), locale), this.h);
    }
    
    @Override
    public String e() {
        return "aoe";
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getDouble("range");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("range", (Object)this.h);
    }
}

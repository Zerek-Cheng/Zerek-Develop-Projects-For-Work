// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ca extends bI
{
    public double h;
    
    public ca() {
        this.h = 1.0;
    }
    
    @Override
    public aM f() {
        return aM.DAMAGE;
    }
    
    @Override
    public int a(final Player player, final LivingEntity damager, final aY damage) {
        return this.c(player, damager, damage);
    }
    
    @Override
    public int b(final Player player, final LivingEntity target, final aY damage) {
        return this.c(player, target, damage);
    }
    
    public int c(final Player player, final LivingEntity e, final aY damage) {
        if (this.y(player)) {
            final double value = damage.a() * this.h;
            if (player.getHealth() + value >= player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
            else {
                player.setHealth(player.getHealth() + value);
            }
            return 1;
        }
        return 0;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power." + this.e(), locale), 100 / this.e, this.h * 100.0);
    }
    
    @Override
    public String e() {
        return "lifesteal";
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getDouble("multiple", 1.0);
        if (this.h < 0.0) {
            this.h = 0.1;
        }
    }
    
    public void d(final ConfigurationSection s) {
        s.set("multiple", (Object)this.h);
    }
    
    @Override
    public boolean b() {
        return true;
    }
}

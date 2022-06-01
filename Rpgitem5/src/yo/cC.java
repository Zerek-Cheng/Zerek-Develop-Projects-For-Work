// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class cc extends br
{
    public PotionEffectType h;
    public int i;
    public int j;
    
    public cc() {
        this.h = PotionEffectType.HARM;
        this.i = 20;
        this.j = 1;
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
        if (target instanceof LivingEntity && this.y(player)) {
            ((LivingEntity)target).addPotionEffect(new PotionEffect(this.h, this.i, this.j), true);
            return 1;
        }
        return 0;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.potionhit", locale), (int)(1.0 / this.e * 100.0), aO.b(aL.POTION, this.h.getName(), locale));
    }
    
    @Override
    public String e() {
        return "potionhit";
    }
    
    public void c(final ConfigurationSection s) {
        this.i = s.getInt("duration", 20);
        this.j = s.getInt("amplifier", 1);
        this.h = PotionEffectType.getByName(s.getString("type", PotionEffectType.HARM.getName()));
    }
    
    public void d(final ConfigurationSection s) {
        s.set("duration", (Object)this.i);
        s.set("amplifier", (Object)this.j);
        s.set("type", (Object)this.h.getName());
    }
    
    @Override
    public boolean b() {
        return true;
    }
}

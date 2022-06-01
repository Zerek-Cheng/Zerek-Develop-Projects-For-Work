// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;

@Deprecated
public class cj extends bt
{
    private int h;
    private int i;
    
    public cj() {
        this.h = 3;
        this.i = 20;
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.i, this.h), true);
            return 1;
        }
        return 0;
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getInt("speed");
        this.i = s.getInt("time");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("speed", (Object)this.h);
        s.set("time", (Object)this.i);
    }
    
    @Override
    public String e() {
        return "rush";
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + "Gives temporary speed boost";
    }
}

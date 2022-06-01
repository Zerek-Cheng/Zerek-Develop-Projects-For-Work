// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class ce extends bt
{
    public int h;
    public int i;
    public PotionEffectType j;
    
    public ce() {
        this.h = 3;
        this.i = 20;
        this.j = PotionEffectType.HEAL;
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            player.addPotionEffect(new PotionEffect(this.j, this.i, this.h), true);
            return 1;
        }
        return 0;
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getInt("amp");
        this.i = s.getInt("time");
        this.j = PotionEffectType.getByName(s.getString("type", "heal"));
    }
    
    public void d(final ConfigurationSection s) {
        s.set("amp", (Object)this.h);
        s.set("time", (Object)this.i);
        s.set("type", (Object)this.j.getName());
    }
    
    @Override
    public String e() {
        return "potionself";
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.potionself", locale), aO.b(aL.POTION, this.j.getName(), locale), this.h + 1, this.i / 20.0);
    }
}

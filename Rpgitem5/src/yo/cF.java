// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class cf extends bt
{
    public PotionEffectType h;
    public int i;
    public int j;
    
    public cf() {
        this.h = PotionEffectType.HARM;
        this.i = 20;
        this.j = 1;
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), Sound.SPLASH2, 1.0f, 1.0f);
            final ThrownPotion thrownPotion = (ThrownPotion)player.launchProjectile((Class)ThrownPotion.class);
            thrownPotion.getEffects().clear();
            final PotionEffect pe = new PotionEffect(this.h, this.i, this.j);
            aR.a((Metadatable)thrownPotion, aR.a.RPGITEM_MAKE, null);
            aR.a((Metadatable)thrownPotion, aR.a.POTION_EFFECT, pe);
            return 1;
        }
        return 0;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.potionthrow", locale), aO.b(aL.POTION, this.h.getName(), locale), this.g / 20.0);
    }
    
    @Override
    public String e() {
        return "potionthrow";
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
}

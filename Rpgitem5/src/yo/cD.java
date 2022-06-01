// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.metadata.Metadatable;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class cd extends bt
{
    public long h;
    public bC i;
    
    public cd() {
        this.h = 20L;
        this.i = bC.ALL;
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
            aR.a((Metadatable)thrownPotion, aR.a.RPGITEM_MAKE, null);
            aR.a((Metadatable)thrownPotion, aR.a.POTION_PURGE_EFFECT, new bd(this.i, System.currentTimeMillis() + this.h * 50L));
            return 1;
        }
        return 0;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.potionpurge", locale), aO.a("power.potionpurge.type." + this.i.name().toLowerCase(), locale), this.g / 20.0);
    }
    
    @Override
    public String e() {
        return "potionpurge";
    }
    
    public void c(final ConfigurationSection s) {
        this.i = bC.getPurgeType(s.getString("type"));
    }
    
    public void d(final ConfigurationSection s) {
        s.set("type", (Object)this.i.name());
    }
}

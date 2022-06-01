// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.metadata.Metadatable;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class cn extends bt
{
    public double h;
    
    public cn() {
        this.h = 0.0;
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
            final TNTPrimed tnt = (TNTPrimed)player.getWorld().spawn(player.getLocation().add(0.0, 1.8, 0.0), (Class)TNTPrimed.class);
            tnt.setVelocity(player.getLocation().getDirection().multiply(2.0));
            aR.a((Metadatable)tnt, aR.a.RPGITEM_MAKE, null);
            if (this.h > 0.0) {
                aR.a((Metadatable)tnt, aR.a.RPGITEM_DAMAGE, this.h);
            }
            return 1;
        }
        return 0;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.tntcannon", locale), this.g / 20.0);
    }
    
    @Override
    public String e() {
        return "tntcannon";
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getDouble("damage");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("damage", (Object)this.h);
    }
    
    @Override
    public boolean b() {
        return true;
    }
}

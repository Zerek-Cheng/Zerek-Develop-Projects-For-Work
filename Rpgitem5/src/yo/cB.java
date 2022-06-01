// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.Metadatable;
import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class cb extends bt
{
    public double h;
    
    @Override
    public int a(final Player player) {
        return this.a(player, bf.a((LivingEntity)player, 6));
    }
    
    @Override
    public int b(final Player player, final LivingEntity target, final aY damage) {
        return this.a(player, target.getLocation());
    }
    
    @Override
    public int a(final Player player, final LivingEntity damager, final aY damage) {
        return this.a(player, damager.getLocation());
    }
    
    @Override
    public int a(final Player player, final Projectile arrow) {
        return this.a(player, arrow.getLocation());
    }
    
    @Override
    public int a(final Player player, final LivingEntity killer) {
        return this.a(player, (killer != null) ? killer.getLocation() : player.getLocation());
    }
    
    private int a(final Player player, final Location loc) {
        if (this.a(player, true)) {
            final Entity entity = (Entity)loc.getWorld().strikeLightning(loc);
            aR.a((Metadatable)entity, aR.a.RPGITEM_MAKE, null);
            if (this.h > 0.0) {
                aR.a((Metadatable)entity, aR.a.RPGITEM_DAMAGE, this.h);
            }
            return 1;
        }
        return 0;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.lightning", locale), (int)(1.0 / this.e * 100.0));
    }
    
    @Override
    public String e() {
        return "lightning";
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

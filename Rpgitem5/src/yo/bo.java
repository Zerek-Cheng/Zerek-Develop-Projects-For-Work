// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.metadata.Metadatable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class bO extends br
{
    public int h;
    
    public bO() {
        this.h = 60;
    }
    
    @Override
    public aM f() {
        return aM.DAMAGE;
    }
    
    @Override
    public int a(final Player player, final Entity target) {
        return this.a(player, target, aR.a.POWER_BLOCKBUFF);
    }
    
    public int a(final Player player, final Entity target, final aR.a metadataKey) {
        if (target == null) {
            return 0;
        }
        aR.a((Metadatable)target, metadataKey, "", this.h, true);
        return 1;
    }
    
    @Override
    String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power." + this.e(), locale), this.e, this.h / 20.0);
    }
    
    @Override
    public String e() {
        return "blockbuff";
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getInt("duration");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("duration", (Object)this.h);
    }
    
    @Override
    public boolean b() {
        return true;
    }
}

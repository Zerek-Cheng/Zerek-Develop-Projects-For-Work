// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class cr extends bI
{
    public void a(final Player player, final LivingEntity e, final double damage) {
        player.getItemInHand().setDurability((short)0);
        player.updateInventory();
    }
    
    public void c(final ConfigurationSection s) {
    }
    
    public void d(final ConfigurationSection s) {
    }
    
    @Override
    public String e() {
        return "unbreakable";
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + aO.a("power.unbreakable", locale);
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class cs extends bI
{
    public int h;
    
    public cs() {
        this.h = 1;
    }
    
    public void a(final Player player, final LivingEntity e, final double damage) {
        if (this.f.nextDouble() < this.h / 100.0) {
            System.out.println(player.getItemInHand().getDurability());
            player.getItemInHand().setDurability((short)(player.getItemInHand().getDurability() - 1));
            System.out.println(player.getItemInHand().getDurability());
            player.updateInventory();
        }
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getInt("level", 1);
    }
    
    public void d(final ConfigurationSection s) {
        s.set("level", (Object)this.h);
    }
    
    @Override
    public String e() {
        return "unbreaking";
    }
    
    public String b(final String locale) {
        return String.format(ChatColor.GREEN + aO.a("power.unbreaking", locale), this.h);
    }
}

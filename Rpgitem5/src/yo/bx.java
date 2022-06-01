// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class bX extends bt
{
    public int h;
    
    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            final ItemStack item = player.getInventory().getItemInHand();
            if (item.getAmount() > 0) {
                final int count = item.getAmount() - 1;
                if (count == 0) {
                    player.setFoodLevel(player.getFoodLevel() + this.h);
                    item.setAmount(0);
                    item.setType(Material.AIR);
                    player.setItemInHand(item);
                }
                else {
                    player.setFoodLevel(player.getFoodLevel() + this.h);
                    item.setAmount(count);
                }
                return 1;
            }
        }
        return 0;
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getInt("foodpoints");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("foodpoints", (Object)this.h);
    }
    
    @Override
    public String e() {
        return "food";
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.food", locale), this.h);
    }
}

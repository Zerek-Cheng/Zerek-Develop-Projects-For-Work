/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import yo.ao_0;
import yo.bt_1;

public class bx_1
extends bt_1 {
    public int h;

    @Override
    public int a(Player player) {
        ItemStack item;
        if (this.y(player) && (item = player.getInventory().getItemInHand()).getAmount() > 0) {
            int count = item.getAmount() - 1;
            if (count == 0) {
                player.setFoodLevel(player.getFoodLevel() + this.h);
                item.setAmount(0);
                item.setType(Material.AIR);
                player.setItemInHand(item);
            } else {
                player.setFoodLevel(player.getFoodLevel() + this.h);
                item.setAmount(count);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getInt("foodpoints");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("foodpoints", (Object)this.h);
    }

    @Override
    public String e() {
        return "food";
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.food", locale), this.h);
    }
}


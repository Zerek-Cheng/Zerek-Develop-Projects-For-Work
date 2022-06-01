/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import think.rpgitems.item.RPGItem;
import yo.ao_0;
import yo.bg_1;
import yo.bt_1;

public class bs_0
extends bt_1 {
    @Override
    public int a(Player player) {
        if (this.y(player)) {
            ItemStack item = player.getInventory().getItemInHand();
            if (this.c.isSimilar(item)) {
                int count = item.getAmount() - 1;
                if (count >= 0) {
                    if (count == 0) {
                        item.setAmount(0);
                        item.setType(Material.AIR);
                        player.setItemInHand(null);
                    } else {
                        item.setAmount(count);
                    }
                    return 1;
                }
            } else {
                return bg_1.a((Inventory)player.getInventory(), this.c, 1);
            }
        }
        return 0;
    }

    @Override
    public void c(ConfigurationSection s) {
    }

    @Override
    public void d(ConfigurationSection s) {
    }

    @Override
    public String e() {
        return "consume";
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + ao_0.a("power.consume", locale);
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import yo.ao_0;
import yo.bi_1;

public class cr
extends bi_1 {
    public void a(Player player, LivingEntity e2, double damage) {
        player.getItemInHand().setDurability((short)0);
        player.updateInventory();
    }

    @Override
    public void c(ConfigurationSection s) {
    }

    @Override
    public void d(ConfigurationSection s) {
    }

    @Override
    public String e() {
        return "unbreakable";
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + ao_0.a("power.unbreakable", locale);
    }
}


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

import java.io.PrintStream;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import yo.ao_0;
import yo.bi_1;

public class cs
extends bi_1 {
    public int h = 1;

    public void a(Player player, LivingEntity e2, double damage) {
        if (this.f.nextDouble() < (double)this.h / 100.0) {
            System.out.println(player.getItemInHand().getDurability());
            player.getItemInHand().setDurability((short)(player.getItemInHand().getDurability() - 1));
            System.out.println(player.getItemInHand().getDurability());
            player.updateInventory();
        }
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getInt("level", 1);
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("level", (Object)this.h);
    }

    @Override
    public String e() {
        return "unbreaking";
    }

    @Override
    public String b(String locale) {
        return String.format((Object)ChatColor.GREEN + ao_0.a("power.unbreaking", locale), this.h);
    }
}


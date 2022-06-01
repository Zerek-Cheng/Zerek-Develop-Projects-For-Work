/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import yo.bt_1;

@Deprecated
public class cj
extends bt_1 {
    private int h = 3;
    private int i = 20;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(Player player) {
        if (this.y(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.i, this.h), true);
            return 1;
        }
        return 0;
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getInt("speed");
        this.i = s.getInt("time");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("speed", (Object)this.h);
        s.set("time", (Object)this.i);
    }

    @Override
    public String e() {
        return "rush";
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + "Gives temporary speed boost";
    }
}


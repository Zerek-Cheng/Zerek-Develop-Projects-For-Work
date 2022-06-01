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
import yo.al_0;
import yo.ao_0;
import yo.bt_1;

public class ce_1
extends bt_1 {
    public int h = 3;
    public int i = 20;
    public PotionEffectType j = PotionEffectType.HEAL;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(Player player) {
        if (this.y(player)) {
            player.addPotionEffect(new PotionEffect(this.j, this.i, this.h), true);
            return 1;
        }
        return 0;
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getInt("amp");
        this.i = s.getInt("time");
        this.j = PotionEffectType.getByName((String)s.getString("type", "heal"));
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("amp", (Object)this.h);
        s.set("time", (Object)this.i);
        s.set("type", (Object)this.j.getName());
    }

    @Override
    public String e() {
        return "potionself";
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.potionself", locale), ao_0.b(al_0.POTION, this.j.getName(), locale), this.h + 1, (double)this.i / 20.0);
    }
}


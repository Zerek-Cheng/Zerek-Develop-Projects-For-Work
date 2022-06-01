/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import yo.al_0;
import yo.am_0;
import yo.ao_0;
import yo.br_0;

public class cc_1
extends br_0 {
    public PotionEffectType h = PotionEffectType.HARM;
    public int i = 20;
    public int j = 1;

    @Override
    public am_0 f() {
        return am_0.DAMAGE;
    }

    @Override
    public int a(Player player, Entity target) {
        if (target == null) {
            return 0;
        }
        if (target instanceof LivingEntity && this.y(player)) {
            ((LivingEntity)target).addPotionEffect(new PotionEffect(this.h, this.i, this.j), true);
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.potionhit", locale), (int)(1.0 / (double)this.e * 100.0), ao_0.b(al_0.POTION, this.h.getName(), locale));
    }

    @Override
    public String e() {
        return "potionhit";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.i = s.getInt("duration", 20);
        this.j = s.getInt("amplifier", 1);
        this.h = PotionEffectType.getByName((String)s.getString("type", PotionEffectType.HARM.getName()));
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("duration", (Object)this.i);
        s.set("amplifier", (Object)this.j);
        s.set("type", (Object)this.h.getName());
    }

    @Override
    public boolean b() {
        return true;
    }
}


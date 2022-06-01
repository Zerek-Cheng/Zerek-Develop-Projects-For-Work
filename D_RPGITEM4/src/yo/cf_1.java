/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.ThrownPotion
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  yo.aR
 */
package yo;

import java.util.Collection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import yo.aR;
import yo.al_0;
import yo.ao_0;
import yo.ar_0;
import yo.bt_1;

public class cf_1
extends bt_1 {
    public PotionEffectType h = PotionEffectType.HARM;
    public int i = 20;
    public int j = 1;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), Sound.SPLASH2, 1.0f, 1.0f);
            ThrownPotion thrownPotion = (ThrownPotion)player.launchProjectile(ThrownPotion.class);
            thrownPotion.getEffects().clear();
            PotionEffect pe = new PotionEffect(this.h, this.i, this.j);
            aR.a((Metadatable)thrownPotion, (ar_0.a)ar_0.a.RPGITEM_MAKE, null);
            aR.a((Metadatable)thrownPotion, (ar_0.a)ar_0.a.POTION_EFFECT, (Object)pe);
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.potionthrow", locale), ao_0.b(al_0.POTION, this.h.getName(), locale), (double)this.g / 20.0);
    }

    @Override
    public String e() {
        return "potionthrow";
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
}


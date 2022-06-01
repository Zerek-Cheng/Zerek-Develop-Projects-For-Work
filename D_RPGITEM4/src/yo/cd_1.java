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
import yo.aR;
import yo.ao_0;
import yo.ar_0;
import yo.bc_0;
import yo.bd_1;
import yo.bt_1;

public class cd_1
extends bt_1 {
    public long h = 20L;
    public bc_0 i = bc_0.ALL;

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
            aR.a((Metadatable)thrownPotion, (ar_0.a)ar_0.a.RPGITEM_MAKE, null);
            aR.a((Metadatable)thrownPotion, (ar_0.a)ar_0.a.POTION_PURGE_EFFECT, (Object)new bd_1(this.i, System.currentTimeMillis() + this.h * 50L));
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.potionpurge", locale), ao_0.a(new StringBuilder().append("power.potionpurge.type.").append(this.i.name().toLowerCase()).toString(), locale), (double)this.g / 20.0);
    }

    @Override
    public String e() {
        return "potionpurge";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.i = bc_0.getPurgeType(s.getString("type"));
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("type", (Object)this.i.name());
    }
}


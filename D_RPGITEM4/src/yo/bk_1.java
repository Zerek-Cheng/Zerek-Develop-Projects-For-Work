/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.metadata.Metadatable
 *  yo.aR
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.Metadatable;
import yo.aR;
import yo.ao_0;
import yo.ar_0;
import yo.bf_1;
import yo.bt_1;
import yo.v_1;

public class bk_1
extends bt_1 {
    public double h;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(Player player) {
        if (this.a(player, true)) {
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
            Arrow arrow = (Arrow)player.launchProjectile(Arrow.class);
            aR.a((Metadatable)arrow, (ar_0.a)ar_0.a.RPGITEM_MAKE, null);
            if (this.h > 0.0) {
                aR.a((Metadatable)arrow, (ar_0.a)ar_0.a.RPGITEM_DAMAGE, (Object)this.h);
            }
            bf_1.a.a(arrow.getEntityId(), (byte)1);
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.arrow", locale), (double)this.g / 20.0);
    }

    @Override
    public String e() {
        return "arrow";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getDouble("damage");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("damage", (Object)this.h);
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.util.Vector
 *  yo.aR
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.Metadatable;
import org.bukkit.util.Vector;
import yo.aR;
import yo.ao_0;
import yo.ar_0;
import yo.bt_1;

public class cn
extends bt_1 {
    public double h = 0.0;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(Player player) {
        if (this.y(player)) {
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1.0f, 1.0f);
            TNTPrimed tnt = (TNTPrimed)player.getWorld().spawn(player.getLocation().add(0.0, 1.8, 0.0), TNTPrimed.class);
            tnt.setVelocity(player.getLocation().getDirection().multiply(2.0));
            aR.a((Metadatable)tnt, (ar_0.a)ar_0.a.RPGITEM_MAKE, null);
            if (this.h > 0.0) {
                aR.a((Metadatable)tnt, (ar_0.a)ar_0.a.RPGITEM_DAMAGE, (Object)this.h);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.tntcannon", locale), (double)this.g / 20.0);
    }

    @Override
    public String e() {
        return "tntcannon";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getDouble("damage");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("damage", (Object)this.h);
    }

    @Override
    public boolean b() {
        return true;
    }
}


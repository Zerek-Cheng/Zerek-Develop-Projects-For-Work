/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.LightningStrike
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.metadata.Metadatable
 *  yo.aR
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.Metadatable;
import yo.aR;
import yo.ao_0;
import yo.ar_0;
import yo.ay_1;
import yo.bf_0;
import yo.bt_1;

public class cb_1
extends bt_1 {
    public double h;

    @Override
    public int a(Player player) {
        return this.a(player, bf_0.a((LivingEntity)player, 6));
    }

    @Override
    public int b(Player player, LivingEntity target, ay_1 damage) {
        return this.a(player, target.getLocation());
    }

    @Override
    public int a(Player player, LivingEntity damager, ay_1 damage) {
        return this.a(player, damager.getLocation());
    }

    @Override
    public int a(Player player, Projectile arrow) {
        return this.a(player, arrow.getLocation());
    }

    @Override
    public int a(Player player, LivingEntity killer) {
        return this.a(player, killer != null ? killer.getLocation() : player.getLocation());
    }

    private int a(Player player, Location loc) {
        if (this.a(player, true)) {
            LightningStrike entity = loc.getWorld().strikeLightning(loc);
            aR.a((Metadatable)entity, (ar_0.a)ar_0.a.RPGITEM_MAKE, null);
            if (this.h > 0.0) {
                aR.a((Metadatable)entity, (ar_0.a)ar_0.a.RPGITEM_DAMAGE, (Object)this.h);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.lightning", locale), (int)(1.0 / (double)this.e * 100.0));
    }

    @Override
    public String e() {
        return "lightning";
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


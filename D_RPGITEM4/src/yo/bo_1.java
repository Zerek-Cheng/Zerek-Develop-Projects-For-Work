/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.Metadatable
 *  yo.aR
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.Metadatable;
import yo.aR;
import yo.am_0;
import yo.ao_0;
import yo.ar_0;
import yo.br_0;

public class bo_1
extends br_0 {
    public int h = 60;

    @Override
    public am_0 f() {
        return am_0.DAMAGE;
    }

    @Override
    public int a(Player player, Entity target) {
        return this.a(player, target, ar_0.a.POWER_BLOCKBUFF);
    }

    public int a(Player player, Entity target, ar_0.a metadataKey) {
        if (target == null) {
            return 0;
        }
        aR.a((Metadatable)target, (ar_0.a)metadataKey, (Object)"", (long)this.h, (boolean)true);
        return 1;
    }

    @Override
    String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a(new StringBuilder().append("power.").append(this.e()).toString(), locale), this.e, (double)this.h / 20.0);
    }

    @Override
    public String e() {
        return "blockbuff";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getInt("duration");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("duration", (Object)this.h);
    }

    @Override
    public boolean b() {
        return true;
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Snowball
 */
package yo;

import org.bukkit.Sound;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import yo.cg_1;

public class cm
extends cg_1 {
    @Override
    public String e() {
        return "snowball";
    }

    @Override
    Class<? extends Projectile> g() {
        return Snowball.class;
    }

    @Override
    Sound h() {
        return Sound.GHAST_FIREBALL;
    }
}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.LargeFireball
 *  org.bukkit.entity.Projectile
 */
package yo;

import org.bukkit.Sound;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Projectile;
import yo.cg_1;

public class bn_1
extends cg_1 {
    @Override
    Class<? extends Projectile> g() {
        return LargeFireball.class;
    }

    @Override
    Sound h() {
        return Sound.GHAST_FIREBALL;
    }

    @Override
    public String e() {
        return "bigfireball";
    }
}


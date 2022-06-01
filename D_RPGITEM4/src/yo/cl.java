/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.SmallFireball
 */
package yo;

import org.bukkit.Sound;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import yo.cg_1;

public class cl
extends cg_1 {
    @Override
    Class<? extends Projectile> g() {
        return SmallFireball.class;
    }

    @Override
    Sound h() {
        return Sound.GHAST_FIREBALL;
    }

    @Override
    public String e() {
        return "smallfireball";
    }
}


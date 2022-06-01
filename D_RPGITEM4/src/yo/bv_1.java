/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.Fish
 *  org.bukkit.entity.Projectile
 */
package yo;

import org.bukkit.Sound;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Projectile;
import yo.cg_1;

public class bv_1
extends cg_1 {
    @Override
    public String e() {
        return "fishinghook";
    }

    @Override
    Class<? extends Projectile> g() {
        return Fish.class;
    }

    @Override
    Sound h() {
        return Sound.SHOOT_ARROW;
    }
}


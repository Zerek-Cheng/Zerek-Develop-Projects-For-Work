/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.Egg
 *  org.bukkit.entity.Projectile
 */
package yo;

import org.bukkit.Sound;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Projectile;
import yo.cg_1;

public class bu_0
extends cg_1 {
    @Override
    public String e() {
        return "egg";
    }

    @Override
    Class<? extends Projectile> g() {
        return Egg.class;
    }

    @Override
    Sound h() {
        return Sound.CHICKEN_EGG_POP;
    }
}


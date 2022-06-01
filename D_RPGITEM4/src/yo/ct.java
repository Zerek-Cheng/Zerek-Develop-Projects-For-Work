/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.WitherSkull
 */
package yo;

import org.bukkit.Sound;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import yo.cg_1;

public class ct
extends cg_1 {
    @Override
    public String e() {
        return "witherskull";
    }

    @Override
    Class<? extends Projectile> g() {
        return WitherSkull.class;
    }

    @Override
    Sound h() {
        return Sound.WITHER_SHOOT;
    }
}


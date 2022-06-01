// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.Sound;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Projectile;

public class ct extends cg
{
    @Override
    public String e() {
        return "witherskull";
    }
    
    @Override
    Class<? extends Projectile> g() {
        return (Class<? extends Projectile>)WitherSkull.class;
    }
    
    @Override
    Sound h() {
        return Sound.WITHER_SHOOT;
    }
}

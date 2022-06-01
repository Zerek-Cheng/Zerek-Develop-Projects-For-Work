// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.Sound;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Projectile;

public class bV extends cg
{
    @Override
    public String e() {
        return "fishinghook";
    }
    
    @Override
    Class<? extends Projectile> g() {
        return (Class<? extends Projectile>)Fish.class;
    }
    
    @Override
    Sound h() {
        return Sound.SHOOT_ARROW;
    }
}

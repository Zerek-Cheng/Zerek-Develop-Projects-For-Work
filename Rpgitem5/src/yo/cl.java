// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.Sound;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Projectile;

public class cl extends cg
{
    @Override
    Class<? extends Projectile> g() {
        return (Class<? extends Projectile>)SmallFireball.class;
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

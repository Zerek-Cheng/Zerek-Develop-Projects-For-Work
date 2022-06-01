// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.Sound;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Projectile;

public class cm extends cg
{
    @Override
    public String e() {
        return "snowball";
    }
    
    @Override
    Class<? extends Projectile> g() {
        return (Class<? extends Projectile>)Snowball.class;
    }
    
    @Override
    Sound h() {
        return Sound.GHAST_FIREBALL;
    }
}

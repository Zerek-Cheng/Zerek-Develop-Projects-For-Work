// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class bQ extends bO
{
    @Override
    public int a(final Player player, final Entity target, final aR.a metadataKey) {
        return super.a(player, target, aR.a.POWER_BLOCK);
    }
    
    @Override
    public String e() {
        return "blockrpgitem";
    }
}

package noppes.npcs;

import net.minecraft.util.*;
import net.minecraft.entity.*;

public class NpcDamageSource extends EntityDamageSource
{
    public NpcDamageSource(final String par1Str, final Entity par2Entity) {
        super(par1Str, par2Entity);
    }
    
    public boolean isDifficultyScaled() {
        return false;
    }
}

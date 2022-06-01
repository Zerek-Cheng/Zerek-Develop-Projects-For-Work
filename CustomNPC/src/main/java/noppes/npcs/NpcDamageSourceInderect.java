package noppes.npcs;

import net.minecraft.util.*;
import net.minecraft.entity.*;

public class NpcDamageSourceInderect extends EntityDamageSourceIndirect
{
    public NpcDamageSourceInderect(final String par1Str, final Entity par2Entity, final Entity par3Entity) {
        super(par1Str, par2Entity, par3Entity);
    }
    
    public boolean isDifficultyScaled() {
        return false;
    }
}

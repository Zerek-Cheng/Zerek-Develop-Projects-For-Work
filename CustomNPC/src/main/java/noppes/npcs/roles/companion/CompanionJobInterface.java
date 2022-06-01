package noppes.npcs.roles.companion;

import noppes.npcs.entity.*;
import net.minecraft.nbt.*;

public abstract class CompanionJobInterface
{
    public EntityNPCInterface npc;
    
    public abstract NBTTagCompound getNBT();
    
    public abstract void setNBT(final NBTTagCompound p0);
    
    public void onUpdate() {
    }
    
    public boolean isSelfSufficient() {
        return false;
    }
}

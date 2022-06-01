package noppes.npcs.quests;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import java.util.*;

public abstract class QuestInterface
{
    public int questId;
    
    public abstract void writeEntityToNBT(final NBTTagCompound p0);
    
    public abstract void readEntityFromNBT(final NBTTagCompound p0);
    
    public abstract boolean isCompleted(final EntityPlayer p0);
    
    public abstract void handleComplete(final EntityPlayer p0);
    
    public abstract Vector<String> getQuestLogStatus(final EntityPlayer p0);
}

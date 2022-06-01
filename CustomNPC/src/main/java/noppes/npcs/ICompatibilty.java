package noppes.npcs;

import net.minecraft.nbt.*;

public interface ICompatibilty
{
    int getVersion();
    
    void setVersion(final int p0);
    
    NBTTagCompound writeToNBT(final NBTTagCompound p0);
}

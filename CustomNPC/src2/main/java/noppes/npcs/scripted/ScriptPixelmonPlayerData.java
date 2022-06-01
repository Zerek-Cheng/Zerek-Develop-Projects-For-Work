package noppes.npcs.scripted;

import noppes.npcs.controllers.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.passive.*;

public class ScriptPixelmonPlayerData
{
    private EntityPlayerMP player;
    
    public ScriptPixelmonPlayerData(final EntityPlayerMP player) {
        this.player = player;
    }
    
    public ScriptPixelmon getPartySlot(final int slot) {
        final NBTTagCompound compound = PixelmonHelper.getPartySlot(slot, (EntityPlayer)this.player);
        if (compound == null) {
            return null;
        }
        final EntityTameable pixelmon = PixelmonHelper.pixelmonFromNBT(compound, (EntityPlayer)this.player);
        return new ScriptPixelmon(pixelmon, compound);
    }
    
    public int countPCPixelmon() {
        return PixelmonHelper.countPCPixelmon(this.player);
    }
}

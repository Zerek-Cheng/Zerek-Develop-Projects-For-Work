package noppes.npcs.roles.companion;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;

public class CompanionTrader extends CompanionJobInterface
{
    @Override
    public NBTTagCompound getNBT() {
        final NBTTagCompound compound = new NBTTagCompound();
        return compound;
    }
    
    @Override
    public void setNBT(final NBTTagCompound compound) {
    }
    
    public void interact(final EntityPlayer player) {
        NoppesUtilServer.sendOpenGui(player, EnumGuiType.CompanionTrader, this.npc);
    }
}

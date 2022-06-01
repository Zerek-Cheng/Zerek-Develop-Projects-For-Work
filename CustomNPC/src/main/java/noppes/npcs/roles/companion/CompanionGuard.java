package noppes.npcs.roles.companion;

import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.monster.*;

public class CompanionGuard extends CompanionJobInterface
{
    public boolean isStanding;
    
    public CompanionGuard() {
        this.isStanding = false;
    }
    
    @Override
    public NBTTagCompound getNBT() {
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("CompanionGuardStanding", this.isStanding);
        return compound;
    }
    
    @Override
    public void setNBT(final NBTTagCompound compound) {
        this.isStanding = compound.getBoolean("CompanionGuardStanding");
    }
    
    public boolean isEntityApplicable(final Entity entity) {
        return !(entity instanceof EntityPlayer) && !(entity instanceof EntityNPCInterface) && !(entity instanceof EntityCreeper) && entity instanceof IMob;
    }
    
    @Override
    public boolean isSelfSufficient() {
        return this.isStanding;
    }
}

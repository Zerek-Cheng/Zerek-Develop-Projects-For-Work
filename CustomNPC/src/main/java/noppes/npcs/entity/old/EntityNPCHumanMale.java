package noppes.npcs.entity.old;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;

public class EntityNPCHumanMale extends EntityNPCInterface
{
    public EntityNPCHumanMale(final World world) {
        super(world);
    }
    
    @Override
    public void onUpdate() {
        this.isDead = true;
        if (!this.worldObj.isRemote) {
            final NBTTagCompound compound = new NBTTagCompound();
            this.writeToNBT(compound);
            final EntityCustomNpc npc = new EntityCustomNpc(this.worldObj);
            npc.readFromNBT(compound);
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

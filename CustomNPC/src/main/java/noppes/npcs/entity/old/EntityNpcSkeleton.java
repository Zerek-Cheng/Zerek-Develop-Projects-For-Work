package noppes.npcs.entity.old;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNpcSkeleton extends EntityNPCInterface
{
    public EntityNpcSkeleton(final World world) {
        super(world);
        this.display.texture = "customnpcs:textures/entity/skeleton/Skeleton.png";
    }
    
    @Override
    public void onUpdate() {
        this.isDead = true;
        if (!this.worldObj.isRemote) {
            final NBTTagCompound compound = new NBTTagCompound();
            this.writeToNBT(compound);
            final EntityCustomNpc npc = new EntityCustomNpc(this.worldObj);
            npc.readFromNBT(compound);
            final ModelData data = npc.modelData;
            data.setEntityClass((Class<? extends EntityLivingBase>)EntitySkeleton.class);
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

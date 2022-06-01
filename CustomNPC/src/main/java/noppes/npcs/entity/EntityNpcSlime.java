package noppes.npcs.entity;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNpcSlime extends EntityNPCInterface
{
    public EntityNpcSlime(final World world) {
        super(world);
        this.scaleX = 2.0f;
        this.scaleY = 2.0f;
        this.scaleZ = 2.0f;
        this.display.texture = "customnpcs:textures/entity/slime/Slime.png";
        this.width = 0.8f;
        this.height = 0.8f;
    }
    
    @Override
    public void updateHitbox() {
        this.width = 0.8f;
        this.height = 0.8f;
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
            data.setEntityClass((Class<? extends EntityLivingBase>)EntityNpcSlime.class);
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

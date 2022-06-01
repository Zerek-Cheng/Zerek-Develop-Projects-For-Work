package noppes.npcs.entity;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNpcCrystal extends EntityNPCInterface
{
    public EntityNpcCrystal(final World world) {
        super(world);
        this.scaleX = 0.7f;
        this.scaleY = 0.7f;
        this.scaleZ = 0.7f;
        this.display.texture = "customnpcs:textures/entity/crystal/EnderCrystal.png";
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
            data.setEntityClass((Class<? extends EntityLivingBase>)EntityNpcCrystal.class);
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

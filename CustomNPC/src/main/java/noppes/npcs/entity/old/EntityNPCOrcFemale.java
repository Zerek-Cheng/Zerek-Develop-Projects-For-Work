package noppes.npcs.entity.old;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNPCOrcFemale extends EntityNPCInterface
{
    public EntityNPCOrcFemale(final World world) {
        super(world);
        final float scaleX = 0.9375f;
        this.scaleZ = scaleX;
        this.scaleY = scaleX;
        this.scaleX = scaleX;
        this.display.texture = "customnpcs:textures/entity/orcfemale/StrandedFemaleOrc.png";
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
            data.breasts = 2;
            data.legs.setScale(0.9f, 0.65f);
            data.arms.setScale(0.9f, 0.65f);
            data.body.setScale(1.0f, 0.65f, 1.1f);
            data.head.setScale(0.85f, 0.85f);
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

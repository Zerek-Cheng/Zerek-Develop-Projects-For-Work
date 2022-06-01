package noppes.npcs.entity.old;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNPCElfFemale extends EntityNPCInterface
{
    public EntityNPCElfFemale(final World world) {
        super(world);
        this.display.texture = "customnpcs:textures/entity/elffemale/ElfFemale.png";
        this.scaleX = 0.8f;
        this.scaleY = 1.0f;
        this.scaleZ = 0.8f;
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
            data.legs.setScale(0.8f, 1.05f);
            data.arms.setScale(0.8f, 1.05f);
            data.body.setScale(0.8f, 1.05f);
            data.head.setScale(0.8f, 0.85f);
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

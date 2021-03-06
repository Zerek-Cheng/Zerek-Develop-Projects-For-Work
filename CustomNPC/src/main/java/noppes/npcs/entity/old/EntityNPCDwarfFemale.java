package noppes.npcs.entity.old;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNPCDwarfFemale extends EntityNPCInterface
{
    public EntityNPCDwarfFemale(final World world) {
        super(world);
        final float n = 0.75f;
        this.scaleZ = n;
        this.scaleX = n;
        this.scaleY = 0.6275f;
        this.display.texture = "customnpcs:textures/entity/dwarffemale/Simone.png";
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

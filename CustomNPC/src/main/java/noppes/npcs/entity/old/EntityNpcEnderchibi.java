package noppes.npcs.entity.old;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNpcEnderchibi extends EntityNPCInterface
{
    public EntityNpcEnderchibi(final World world) {
        super(world);
        this.display.texture = "customnpcs:textures/entity/enderchibi/MrEnderchibi.png";
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
            data.legs.setScale(0.65f, 0.75f);
            data.arms.setScale(0.5f, 1.45f);
            final ModelPartData part = data.getOrCreatePart("particles");
            part.playerTexture = true;
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

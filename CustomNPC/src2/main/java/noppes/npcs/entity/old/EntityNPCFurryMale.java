package noppes.npcs.entity.old;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNPCFurryMale extends EntityNPCInterface
{
    public EntityNPCFurryMale(final World world) {
        super(world);
        this.display.texture = "customnpcs:textures/entity/furrymale/WolfGrey.png";
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
            final ModelPartData hair = data.getOrCreatePart("ears");
            hair.playerTexture = true;
            final ModelPartData snout = data.getOrCreatePart("snout");
            snout.playerTexture = true;
            snout.type = 1;
            final ModelPartData tail = data.getOrCreatePart("tail");
            tail.playerTexture = true;
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

package noppes.npcs.entity.old;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNPCFurryFemale extends EntityNPCInterface
{
    public EntityNPCFurryFemale(final World world) {
        super(world);
        final float scaleX = 0.9075f;
        this.scaleZ = scaleX;
        this.scaleY = scaleX;
        this.scaleX = scaleX;
        this.display.texture = "customnpcs:textures/entity/furryfemale/WolfBlack.png";
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
            data.head.setScale(0.95f, 0.95f);
            data.legs.setScale(0.92f, 0.92f);
            data.arms.setScale(0.8f, 0.92f);
            data.body.setScale(0.92f, 0.92f);
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

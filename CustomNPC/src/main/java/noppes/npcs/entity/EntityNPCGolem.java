package noppes.npcs.entity;

import net.minecraft.world.*;
import noppes.npcs.constants.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class EntityNPCGolem extends EntityNPCInterface
{
    public EntityNPCGolem(final World world) {
        super(world);
        this.display.texture = "customnpcs:textures/entity/golem/Iron Golem.png";
        this.width = 1.4f;
        this.height = 2.5f;
    }
    
    @Override
    public void updateHitbox() {
        this.currentAnimation = EnumAnimation.values()[this.dataWatcher.getWatchableObjectInt(14)];
        if (this.currentAnimation == EnumAnimation.LYING) {
            final float n = 0.5f;
            this.height = n;
            this.width = n;
        }
        else if (this.currentAnimation == EnumAnimation.SITTING) {
            this.width = 1.4f;
            this.height = 2.0f;
        }
        else {
            this.width = 1.4f;
            this.height = 2.5f;
        }
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
            data.setEntityClass((Class<? extends EntityLivingBase>)EntityNPCGolem.class);
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

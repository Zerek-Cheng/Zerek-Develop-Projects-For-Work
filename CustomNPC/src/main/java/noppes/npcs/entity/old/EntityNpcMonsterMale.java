package noppes.npcs.entity.old;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import noppes.npcs.constants.*;
import net.minecraft.entity.*;

public class EntityNpcMonsterMale extends EntityNPCInterface
{
    public EntityNpcMonsterMale(final World world) {
        super(world);
        this.display.texture = "customnpcs:textures/entity/monstermale/ZombieSteve.png";
    }
    
    @Override
    public void onUpdate() {
        this.isDead = true;
        if (!this.worldObj.isRemote) {
            final NBTTagCompound compound = new NBTTagCompound();
            this.writeToNBT(compound);
            final EntityCustomNpc npc = new EntityCustomNpc(this.worldObj);
            npc.readFromNBT(compound);
            npc.ai.animationType = EnumAnimation.HUG;
            this.worldObj.spawnEntityInWorld((Entity)npc);
        }
        super.onUpdate();
    }
}

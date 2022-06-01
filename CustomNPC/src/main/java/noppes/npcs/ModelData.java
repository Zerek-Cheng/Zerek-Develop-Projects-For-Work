package noppes.npcs;

import noppes.npcs.entity.*;
import net.minecraft.world.*;
import noppes.npcs.controllers.*;
import net.minecraft.entity.*;

public class ModelData extends ModelDataShared
{
    public EntityLivingBase getEntity(final EntityNPCInterface npc) {
        if (this.entityClass == null) {
            return null;
        }
        if (this.entity == null) {
            try {
                (this.entity = (EntityLivingBase)this.entityClass.getConstructor(World.class).newInstance(npc.worldObj)).readEntityFromNBT(this.extra);
                if (this.entity instanceof EntityLiving) {
                    final EntityLiving living = (EntityLiving)this.entity;
                    living.setCurrentItemOrArmor(0, (npc.getHeldItem() != null) ? npc.getHeldItem() : npc.getOffHand());
                    living.setCurrentItemOrArmor(1, npc.inventory.armorItemInSlot(3));
                    living.setCurrentItemOrArmor(2, npc.inventory.armorItemInSlot(2));
                    living.setCurrentItemOrArmor(3, npc.inventory.armorItemInSlot(1));
                    living.setCurrentItemOrArmor(4, npc.inventory.armorItemInSlot(0));
                }
                if (PixelmonHelper.isPixelmon((Entity)this.entity) && npc.worldObj.isRemote) {
                    if (this.extra.hasKey("Name")) {
                        PixelmonHelper.setName(this.entity, this.extra.getString("Name"));
                    }
                    else {
                        PixelmonHelper.setName(this.entity, "Abra");
                    }
                }
            }
            catch (Exception ex) {}
        }
        return this.entity;
    }
    
    public ModelData copy() {
        final ModelData data = new ModelData();
        data.readFromNBT(this.writeToNBT());
        return data;
    }
}

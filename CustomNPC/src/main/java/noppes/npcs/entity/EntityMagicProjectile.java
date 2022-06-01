package noppes.npcs.entity;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityMagicProjectile extends EntityProjectile
{
    private EntityPlayer player;
    private ItemStack equiped;
    
    public EntityMagicProjectile(final World par1World, final EntityPlayer player, final ItemStack item, final boolean isNPC) {
        super(par1World, (EntityLivingBase)player, item, isNPC);
        this.player = player;
        this.equiped = player.inventory.getCurrentItem();
    }
    
    @Override
    public void onUpdate() {
        if (this.player.inventory.getCurrentItem() != this.equiped) {
            this.setDead();
        }
        super.onUpdate();
    }
    
    public String getCommandSenderName() {
        return StatCollector.translateToLocal("entity.throwableitem.name");
    }
}

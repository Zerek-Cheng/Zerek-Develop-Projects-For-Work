package noppes.npcs.items;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;

public class ItemGunChainsaw extends ItemNpcWeaponInterface
{
    public ItemGunChainsaw(final int par1, final Item.ToolMaterial tool) {
        super(par1, tool);
    }
    
    public boolean hitEntity(final ItemStack par1ItemStack, final EntityLivingBase par2EntityLiving, final EntityLivingBase par3EntityLiving) {
        if (par2EntityLiving.getHealth() <= 0.0f) {
            return false;
        }
        final double x = par2EntityLiving.posX;
        final double y = par2EntityLiving.posY + par2EntityLiving.height / 2.0f;
        final double z = par2EntityLiving.posZ;
        par3EntityLiving.worldObj.playSoundEffect(x, y, z, "random.explode", 0.8f, (1.0f + (par3EntityLiving.worldObj.rand.nextFloat() - par3EntityLiving.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        par3EntityLiving.worldObj.spawnParticle("largeexplode", x, y, z, 0.0, 0.0, 0.0);
        return super.hitEntity(par1ItemStack, par2EntityLiving, par3EntityLiving);
    }
    
    @Override
    public void renderSpecial() {
        super.renderSpecial();
        GL11.glTranslatef(-0.1f, 0.0f, -0.16f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-16.0f, 0.0f, 0.0f, 1.0f);
    }
}

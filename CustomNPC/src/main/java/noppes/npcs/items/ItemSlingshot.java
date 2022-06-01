package noppes.npcs.items;

import noppes.npcs.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import noppes.npcs.entity.*;
import noppes.npcs.constants.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;

public class ItemSlingshot extends ItemNpcInterface
{
    public ItemSlingshot(final int par1) {
        super(par1);
        this.maxStackSize = 1;
        this.setMaxDurability(384);
        this.setCreativeTab((CreativeTabs)CustomItems.tabWeapon);
    }
    
    public void onPlayerStoppedUsing(final ItemStack par1ItemStack, final World worldObj, final EntityPlayer player, final int par4) {
        if (worldObj.isRemote) {
            return;
        }
        final int ticks = this.getMaxItemUseDuration(par1ItemStack) - par4;
        if (ticks < 6) {
            return;
        }
        if (!player.capabilities.isCreativeMode && !this.consumeItem(player, Item.getItemFromBlock(Blocks.cobblestone))) {
            return;
        }
        par1ItemStack.damageItem(1, (EntityLivingBase)player);
        final EntityProjectile projectile = new EntityProjectile(worldObj, (EntityLivingBase)player, new ItemStack(Blocks.cobblestone), false);
        projectile.damage = 4.0f;
        projectile.punch = 1;
        projectile.setRotating(true);
        if (ticks > 24) {
            projectile.setParticleEffect(EnumParticleType.Crit);
            projectile.punch = 2;
        }
        projectile.setHasGravity(true);
        projectile.setSpeed(14);
        projectile.shoot(1.0f);
        worldObj.playSoundAtEntity((Entity)player, "random.bow", 1.0f, ItemSlingshot.itemRand.nextFloat() * 0.3f + 0.8f);
        worldObj.spawnEntityInWorld((Entity)projectile);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glTranslatef(0.0f, 0.5f, 0.0f);
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
}

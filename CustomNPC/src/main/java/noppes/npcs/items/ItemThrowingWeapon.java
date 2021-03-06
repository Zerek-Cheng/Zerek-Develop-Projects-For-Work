package noppes.npcs.items;

import noppes.npcs.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import noppes.npcs.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;

public class ItemThrowingWeapon extends ItemNpcInterface
{
    private boolean rotating;
    private int damage;
    private boolean dropItem;
    
    public ItemThrowingWeapon(final int par1) {
        super(par1);
        this.rotating = false;
        this.damage = 2;
        this.dropItem = false;
        this.setCreativeTab((CreativeTabs)CustomItems.tabWeapon);
    }
    
    public void onPlayerStoppedUsing(final ItemStack par1ItemStack, final World worldObj, final EntityPlayer player, final int par4) {
        if (worldObj.isRemote) {
            player.swingItem();
            return;
        }
        final EntityProjectile projectile = new EntityProjectile(worldObj, (EntityLivingBase)player, new ItemStack(par1ItemStack.getItem(), 1, par1ItemStack.getMetadata()), false);
        projectile.damage = this.damage;
        projectile.canBePickedUp = (!player.capabilities.isCreativeMode && this.dropItem);
        projectile.setRotating(this.rotating);
        projectile.setIs3D(true);
        projectile.setStickInWall(true);
        projectile.setHasGravity(true);
        projectile.setSpeed(12);
        if (!player.capabilities.isCreativeMode) {
            this.consumeItem(player, this);
        }
        projectile.shoot(1.0f);
        worldObj.playSoundAtEntity((Entity)player, "customnpcs:misc.swosh", 1.0f, 1.0f);
        worldObj.spawnEntityInWorld((Entity)projectile);
    }
    
    public ItemThrowingWeapon setRotating() {
        this.rotating = true;
        return this;
    }
    
    public ItemThrowingWeapon setDamage(final int damage) {
        this.damage = damage;
        return this;
    }
    
    public ItemThrowingWeapon setDropItem() {
        this.dropItem = true;
        return this;
    }
    
    @Override
    public void renderSpecial() {
        super.renderSpecial();
        GL11.glTranslatef(0.2f, 0.1f, 0.06f);
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
}

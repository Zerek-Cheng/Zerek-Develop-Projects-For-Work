package noppes.npcs.items;

import noppes.npcs.util.*;
import noppes.npcs.constants.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import noppes.npcs.*;
import net.minecraft.entity.*;
import noppes.npcs.enchants.*;
import noppes.npcs.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;

public class ItemGun extends ItemNpcInterface implements IProjectileCallback
{
    private EnumNpcToolMaterial material;
    
    public ItemGun(final int par1, final EnumNpcToolMaterial material) {
        super(par1);
        this.maxStackSize = 1;
        this.material = material;
        this.setMaxDurability(material.getMaxUses());
        this.setCreativeTab((CreativeTabs)CustomItems.tabWeapon);
    }
    
    public void onPlayerStoppedUsing(final ItemStack stack, final World worldObj, final EntityPlayer player, final int par4) {
        if (worldObj.isRemote) {
            return;
        }
        if (!this.hasBullet(player, stack) || !CustomNpcs.GunsEnabled) {
            worldObj.playSoundAtEntity((Entity)player, "customnpcs:gun.empty", 1.0f, 1.0f);
            return;
        }
        final int ticks = this.getMaxItemUseDuration(stack) - par4;
        if (ticks < 10) {
            return;
        }
        stack.damageItem(1, (EntityLivingBase)player);
        final ItemBullet bullet = (ItemBullet)this.getBullet();
        int damage = (bullet.getBulletDamage() + this.material.getDamageVsEntity() + 1) / 2 + 5;
        damage += (int)(damage * EnchantInterface.getLevel(EnchantInterface.Damage, stack) * 0.5f);
        final EntityProjectile projectile = new EntityProjectile(worldObj, (EntityLivingBase)player, new ItemStack(this.getBullet()), false);
        projectile.damage = damage;
        projectile.callback = this;
        projectile.callbackItem = stack;
        projectile.setSpeed(40);
        projectile.shoot(this.material.getDamageVsEntity() + 1);
        if (!player.capabilities.isCreativeMode && !this.hasInfinite(stack)) {
            this.consumeItem(player, this.getBullet());
        }
        worldObj.playSoundAtEntity((Entity)player, "customnpcs:gun.pistol.shot", 1.0f, ItemGun.itemRand.nextFloat() * 0.3f + 0.8f);
        worldObj.spawnEntityInWorld((Entity)projectile);
    }
    
    public void onUsingTick(final ItemStack stack, final EntityPlayer player, final int count) {
        final int ticks = this.getMaxItemUseDuration(stack) - count;
        if (ticks == 8 && !player.worldObj.isRemote) {
            player.worldObj.playSoundAtEntity((Entity)player, "customnpcs:gun.pistol.trigger", 1.0f, 1.0f / (player.worldObj.rand.nextFloat() * 0.4f + 0.8f));
        }
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(0.7f, 0.7f, 0.7f);
        GL11.glTranslatef(0.3f, 0.3f, 0.1f);
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 72000;
    }
    
    private boolean hasBullet(final EntityPlayer player, final ItemStack stack) {
        return player.capabilities.isCreativeMode || this.hasInfinite(stack) || this.hasItem(player, this.getBullet());
    }
    
    private Item getBullet() {
        switch (this.material) {
            case EMERALD: {
                return CustomItems.bulletEmerald;
            }
            case DIA: {
                return CustomItems.bulletDiamond;
            }
            case IRON: {
                return CustomItems.bulletIron;
            }
            case BRONZE: {
                return CustomItems.bulletBronze;
            }
            case GOLD: {
                return CustomItems.bulletGold;
            }
            case STONE: {
                return CustomItems.bulletStone;
            }
            case WOOD: {
                return CustomItems.bulletWood;
            }
            default: {
                return CustomItems.bulletBlack;
            }
        }
    }
    
    public boolean hasInfinite(final ItemStack stack) {
        return EnchantInterface.getLevel(EnchantInterface.Infinite, stack) > 0;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }
    
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.bow;
    }
    
    @Override
    public boolean onImpact(final EntityProjectile entityProjectile, final EntityLivingBase entity, final ItemStack itemstack) {
        final int confusion = EnchantInterface.getLevel(EnchantInterface.Confusion, itemstack);
        if (confusion > 0 && entity.getRNG().nextInt(4) > confusion) {
            entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 100));
        }
        final int poison = EnchantInterface.getLevel(EnchantInterface.Poison, itemstack);
        if (poison > 0 && entity.getRNG().nextInt(4) > poison) {
            entity.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
        }
        return false;
    }
}

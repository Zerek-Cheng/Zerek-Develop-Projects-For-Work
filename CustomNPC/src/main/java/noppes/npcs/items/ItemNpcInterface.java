package noppes.npcs.items;

import net.minecraft.creativetab.*;
import noppes.npcs.*;
import org.lwjgl.opengl.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class ItemNpcInterface extends Item implements ItemRenderInterface
{
    public ItemNpcInterface(final int par1) {
        this();
    }
    
    public ItemNpcInterface() {
        this.setCreativeTab((CreativeTabs)CustomItems.tab);
        CustomNpcs.proxy.registerItem(this);
    }
    
    public void renderSpecial() {
        GL11.glScalef(0.66f, 0.66f, 0.66f);
        GL11.glTranslatef(0.0f, 0.3f, 0.0f);
    }
    
    public int getItemEnchantability() {
        return super.getItemEnchantability();
    }
    
    public Item setUnlocalizedName(final String name) {
        super.setUnlocalizedName(name);
        GameRegistry.registerItem((Item)this, name);
        return this;
    }
    
    public boolean hitEntity(final ItemStack par1ItemStack, final EntityLivingBase par2EntityLiving, final EntityLivingBase par3EntityLiving) {
        if (par2EntityLiving.getHealth() <= 0.0f) {
            return false;
        }
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }
    
    public boolean hasItem(final EntityPlayer player, final Item item) {
        return player.inventory.hasItem(item);
    }
    
    public boolean consumeItem(final EntityPlayer player, final Item item) {
        return player.inventory.consumeInventoryItem(item);
    }
}

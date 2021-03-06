package noppes.npcs.items;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import noppes.npcs.*;
import noppes.npcs.constants.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import cpw.mods.fml.relauncher.*;
import cpw.mods.fml.common.registry.*;

public class ItemNpcCloner extends Item
{
    public ItemNpcCloner() {
        this.maxStackSize = 1;
        this.setCreativeTab((CreativeTabs)CustomItems.tab);
    }
    
    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (par3World.isRemote) {
            CustomNpcs.proxy.openGui(par4, par5, par6, EnumGuiType.MobSpawner, par2EntityPlayer);
        }
        return true;
    }
    
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        return 9127187;
    }
    
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister par1IconRegister) {
        this.itemIcon = Items.iron_axe.getIconFromDamage(0);
    }
    
    public Item setUnlocalizedName(final String name) {
        GameRegistry.registerItem((Item)this, name);
        return super.setUnlocalizedName(name);
    }
}

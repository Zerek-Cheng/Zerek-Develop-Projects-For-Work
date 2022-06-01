package noppes.npcs.items;

import noppes.npcs.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import cpw.mods.fml.relauncher.*;
import cpw.mods.fml.common.registry.*;

public class ItemNpcScripter extends Item
{
    public ItemNpcScripter() {
        this.maxStackSize = 1;
        this.setCreativeTab((CreativeTabs)CustomItems.tab);
    }
    
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        return 9127187;
    }
    
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister par1IconRegister) {
        this.itemIcon = Items.iron_shovel.getIconFromDamage(0);
    }
    
    public Item setUnlocalizedName(final String name) {
        GameRegistry.registerItem((Item)this, name);
        return super.setUnlocalizedName(name);
    }
}

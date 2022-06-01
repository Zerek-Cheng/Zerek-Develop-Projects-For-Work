package noppes.npcs.items;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;

public class ItemPlaceholder extends ItemBlock
{
    public ItemPlaceholder(final Block p_i45328_1_) {
        super(p_i45328_1_);
        this.setHasSubtypes(true);
    }
    
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        return super.getUnlocalizedName(par1ItemStack) + "_" + par1ItemStack.getMetadata();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int par1) {
        return this.blockInstance.getIcon(0, par1);
    }
}

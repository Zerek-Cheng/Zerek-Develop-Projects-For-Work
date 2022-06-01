package noppes.npcs.blocks;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;

public class BlockPlaceholder extends Block
{
    private IIcon[] icons;
    
    public BlockPlaceholder() {
        super(Material.rock);
        this.icons = new IIcon[16];
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
    }
    
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int i = 0; i < 16; ++i) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
    
    public int damageDropped(final int par1) {
        return par1;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister par1IconRegister) {
        for (int i = 0; i < 16; ++i) {
            this.icons[i] = par1IconRegister.registerIcon("customnpcs:placeholder_" + i);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        if (meta < this.icons.length) {
            return this.icons[meta];
        }
        return this.icons[0];
    }
}

package noppes.npcs.blocks;

import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import noppes.npcs.blocks.tiles.*;

public class BlockBeam extends BlockRotated
{
    public BlockBeam() {
        super(Blocks.planks);
    }
    
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 5));
    }
    
    public int damageDropped(final int par1) {
        return par1;
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        final TileEntity tileentity = world.getTileEntity(x, y, z);
        if (!(tileentity instanceof TileColorable)) {
            super.setBlockBoundsBasedOnState(world, x, y, z);
            return;
        }
        final TileColorable tile = (TileColorable)tileentity;
        if (tile.rotation == 0) {
            this.setBlockBounds(0.33f, 0.33f, 0.25f, 0.67f, 0.67f, 1.0f);
        }
        else if (tile.rotation == 2) {
            this.setBlockBounds(0.33f, 0.33f, 0.0f, 0.67f, 0.67f, 0.75f);
        }
        else if (tile.rotation == 3) {
            this.setBlockBounds(0.25f, 0.33f, 0.33f, 1.0f, 0.67f, 0.67f);
        }
        else if (tile.rotation == 1) {
            this.setBlockBounds(0.0f, 0.33f, 0.33f, 0.75f, 0.67f, 0.67f);
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileBeam();
    }
}

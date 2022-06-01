package noppes.npcs.blocks;

import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;

public class BlockShelf extends BlockRotated
{
    public BlockShelf() {
        super(Blocks.planks);
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World worldIn, final int x, final int y, final int z) {
        this.setBlockBoundsBasedOnState((IBlockAccess)worldIn, x, y, z);
        return AxisAlignedBB.getBoundingBox(x + this.minX, (double)(y + 0.9f), z + this.minZ, x + this.maxX, (double)(y + 1), z + this.maxZ);
    }
    
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 5));
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        final TileEntity tileentity = world.getTileEntity(x, y, z);
        if (!(tileentity instanceof TileColorable)) {
            super.setBlockBoundsBasedOnState(world, x, y, z);
            return;
        }
        final TileColorable tile = (TileColorable)tileentity;
        float xStart = 0.0f;
        float zStart = 0.0f;
        float xEnd = 1.0f;
        float zEnd = 1.0f;
        if (tile.rotation == 0) {
            zStart = 0.3f;
        }
        else if (tile.rotation == 2) {
            zEnd = 0.7f;
        }
        else if (tile.rotation == 3) {
            xStart = 0.3f;
        }
        else if (tile.rotation == 1) {
            xEnd = 0.7f;
        }
        this.setBlockBounds(xStart, 0.44f, zStart, xEnd, 1.0f, zEnd);
    }
    
    public int damageDropped(final int par1) {
        return par1;
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileShelf();
    }
}

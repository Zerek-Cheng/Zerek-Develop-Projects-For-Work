package noppes.npcs.blocks;

import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;

public class BlockWeaponRack extends BlockTrigger
{
    public BlockWeaponRack() {
        super(Blocks.planks);
    }
    
    public boolean onBlockActivated(final World par1World, final int i, int j, final int k, final EntityPlayer player, final int side, final float hitX, final float hitY, final float hitZ) {
        if (par1World.isRemote) {
            return true;
        }
        final int meta = par1World.getBlockMetadata(i, j, k);
        if (meta >= 7) {
            --j;
        }
        final TileWeaponRack tile = (TileWeaponRack)par1World.getTileEntity(i, j, k);
        float hit = hitX;
        if (tile.rotation == 2) {
            hit = 1.0f - hitX;
        }
        if (tile.rotation == 3) {
            hit = 1.0f - hitZ;
        }
        if (tile.rotation == 1) {
            hit = hitZ;
        }
        final int selected = 2 - (int)(hit / 0.34);
        final ItemStack item = player.getCurrentEquippedItem();
        final ItemStack weapon = tile.getStackInSlot(selected);
        if (item == null && weapon != null) {
            tile.setInventorySlotContents(selected, null);
            player.inventory.setInventorySlotContents(player.inventory.currentItem, weapon);
            par1World.markBlockForUpdate(i, j, k);
            this.updateSurrounding(par1World, i, j, k);
        }
        else {
            if (item == null || item.getItem() == null || item.getItem() instanceof ItemBlock) {
                return true;
            }
            if (item != null && weapon == null) {
                tile.setInventorySlotContents(selected, item);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                par1World.markBlockForUpdate(i, j, k);
                this.updateSurrounding(par1World, i, j, k);
            }
        }
        return true;
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
        return par1 % 7;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        if (!par1World.isAirBlock(par2, par3 + 1, par4)) {
            par1World.setBlockToAir(par2, par3, par4);
        }
        else {
            int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
            l %= 4;
            final TileColorable tile = (TileColorable)par1World.getTileEntity(par2, par3, par4);
            tile.rotation = l;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
            par1World.setBlock(par2, par3 + 1, par4, (Block)this, par6ItemStack.getMetadata() + 7, 2);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (meta >= 7) {
            --y;
        }
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
            zStart = 0.7f;
        }
        else if (tile.rotation == 2) {
            zEnd = 0.3f;
        }
        else if (tile.rotation == 3) {
            xStart = 0.7f;
        }
        else if (tile.rotation == 1) {
            xEnd = 0.3f;
        }
        if (meta >= 7) {
            this.setBlockBounds(xStart, -1.0f, zStart, xEnd, 0.8f, zEnd);
        }
        else {
            this.setBlockBounds(xStart, 0.0f, zStart, xEnd, 1.8f, zEnd);
        }
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        if (var2 < 7) {
            return new TileWeaponRack();
        }
        return null;
    }
    
    public void onBlockHarvested(final World worldIn, final int x, final int y, final int z, final int meta, final EntityPlayer player) {
        if (meta >= 7 && worldIn.getBlock(x, y - 1, z) == this) {
            worldIn.setBlockToAir(x, y - 1, z);
        }
        else if (meta < 7 && worldIn.getBlock(x, y + 1, z) == this) {
            worldIn.setBlockToAir(x, y + 1, z);
        }
    }
    
    public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int meta) {
        final TileNpcContainer tile = (TileNpcContainer)world.getTileEntity(x, y, z);
        if (tile == null) {
            return;
        }
        tile.dropItems(world, x, y, z);
        world.updateNeighborsAboutBlockChange(x, y, z, block);
        super.breakBlock(world, x, y, z, block, meta);
    }
}

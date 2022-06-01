package noppes.npcs.blocks;

import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.block.*;
import noppes.npcs.blocks.tiles.*;

public class BlockPedestal extends BlockTrigger
{
    public BlockPedestal() {
        super(Blocks.stone);
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int side, final float hitX, final float hitY, final float hitZ) {
        if (par1World.isRemote) {
            return true;
        }
        final TilePedestal tile = (TilePedestal)par1World.getTileEntity(i, j, k);
        final ItemStack item = player.getCurrentEquippedItem();
        final ItemStack weapon = tile.getStackInSlot(0);
        if (item == null && weapon != null) {
            tile.setInventorySlotContents(0, null);
            player.inventory.setInventorySlotContents(player.inventory.currentItem, weapon);
            par1World.markBlockForUpdate(i, j, k);
            this.updateSurrounding(par1World, i, j, k);
        }
        else {
            if (item == null || item.getItem() == null || !(item.getItem() instanceof ItemSword)) {
                return true;
            }
            if (item != null && weapon == null) {
                tile.setInventorySlotContents(0, item);
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
        if (tile.rotation % 2 == 0) {
            this.setBlockBounds(0.0f, 0.0f, 0.2f, 1.0f, 0.5f, 0.8f);
        }
        else {
            this.setBlockBounds(0.2f, 0.0f, 0.0f, 0.8f, 0.5f, 1.0f);
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int side, int meta) {
        meta %= 7;
        if (meta == 1) {
            return Blocks.stone.getIcon(side, 0);
        }
        if (meta == 2) {
            return Blocks.iron_block.getIcon(side, 0);
        }
        if (meta == 3) {
            return Blocks.gold_block.getIcon(side, 0);
        }
        if (meta == 4) {
            return Blocks.diamond_block.getIcon(side, 0);
        }
        return Blocks.planks.getIcon(side, 0);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TilePedestal();
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

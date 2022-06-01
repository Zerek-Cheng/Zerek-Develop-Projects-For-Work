package noppes.npcs.blocks;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import noppes.npcs.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.entity.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class BlockCouchWood extends BlockContainer
{
    public int renderId;
    
    public BlockCouchWood() {
        super(Material.wood);
        this.renderId = -1;
    }
    
    public boolean onBlockActivated(final World par1World, final int i, int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        final ItemStack item = player.inventory.getCurrentItem();
        if (item == null || item.getItem() != Items.dye) {
            return BlockChair.MountBlock(par1World, i, j, k, player);
        }
        final int meta = par1World.getBlockMetadata(i, j, k);
        if (meta >= 7) {
            --j;
        }
        final TileColorable tile = (TileColorable)par1World.getTileEntity(i, j, k);
        final int color = BlockColored.func_150031_c(item.getMetadata());
        if (tile.color != color) {
            NoppesUtilServer.consumeItemStack(1, player);
            tile.color = color;
            par1World.markBlockForUpdate(i, j, k);
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
        return par1;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World worldIn, final int x, final int y, final int z) {
        return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), y + 0.5, (double)(z + 1));
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        l %= 4;
        final TileCouchWood tile = (TileCouchWood)par1World.getTileEntity(par2, par3, par4);
        tile.rotation = l;
        tile.color = 15 - par6ItemStack.getMetadata();
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
        this.updateModel(par1World, par2, par3, par4, tile);
        this.onNeighborBlockChange(par1World, par2 + 1, par3, par4, (Block)this);
        this.onNeighborBlockChange(par1World, par2 - 1, par3, par4, (Block)this);
        this.onNeighborBlockChange(par1World, par2, par3, par4 + 1, (Block)this);
        this.onNeighborBlockChange(par1World, par2, par3, par4 - 1, (Block)this);
        this.updateModel(par1World, par2, par3, par4, tile);
        par1World.markBlockForUpdate(par2, par3, par4);
    }
    
    public void onNeighborBlockChange(final World worldObj, final int x, final int y, final int z, final Block block) {
        if (worldObj.isRemote || block != this) {
            return;
        }
        final TileEntity tile = worldObj.getTileEntity(x, y, z);
        if (tile == null || !(tile instanceof TileCouchWood)) {
            return;
        }
        this.updateModel(worldObj, x, y, z, (TileCouchWood)tile);
        worldObj.markBlockForUpdate(x, y, z);
    }
    
    private void updateModel(final World world, final int x, final int y, final int z, final TileCouchWood tile) {
        if (world.isRemote) {
            return;
        }
        final int meta = tile.getBlockMetadata();
        if (tile.rotation == 0) {
            tile.hasLeft = this.compareTiles(tile, x - 1, y, z, world, meta);
            tile.hasRight = this.compareTiles(tile, x + 1, y, z, world, meta);
        }
        else if (tile.rotation == 2) {
            tile.hasLeft = this.compareTiles(tile, x + 1, y, z, world, meta);
            tile.hasRight = this.compareTiles(tile, x - 1, y, z, world, meta);
        }
        else if (tile.rotation == 1) {
            tile.hasLeft = this.compareTiles(tile, x, y, z - 1, world, meta);
            tile.hasRight = this.compareTiles(tile, x, y, z + 1, world, meta);
        }
        else if (tile.rotation == 3) {
            tile.hasLeft = this.compareTiles(tile, x, y, z + 1, world, meta);
            tile.hasRight = this.compareTiles(tile, x, y, z - 1, world, meta);
        }
    }
    
    private boolean compareTiles(final TileCouchWood tile, final int x, final int y, final int z, final World world, final int meta) {
        final int meta2 = world.getBlockMetadata(x, y, z);
        if (meta2 != meta) {
            return false;
        }
        final TileEntity tile2 = world.getTileEntity(x, y, z);
        if (tile2 == null || !(tile2 instanceof TileCouchWood)) {
            return false;
        }
        final TileCouchWood couch = (TileCouchWood)tile2;
        return tile.rotation == couch.rotation;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return this.renderId;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister par1IconRegister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        return Blocks.planks.getIcon(side, meta);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileCouchWood();
    }
}

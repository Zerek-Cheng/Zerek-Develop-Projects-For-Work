package noppes.npcs.blocks;

import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import noppes.npcs.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;

public class BlockTallLamp extends BlockContainer
{
    public int renderId;
    
    public BlockTallLamp() {
        super(Material.wood);
        this.renderId = -1;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        this.setLightLevel(1.0f);
    }
    
    public boolean onBlockActivated(final World par1World, final int i, int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        final ItemStack item = player.inventory.getCurrentItem();
        if (item == null || item.getItem() != Items.dye) {
            return false;
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
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
    }
    
    public int damageDropped(final int par1) {
        return par1 % 7;
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        if (!par1World.isAirBlock(par2, par3 + 1, par4)) {
            par1World.setBlockToAir(par2, par3, par4);
        }
        else {
            int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
            l %= 4;
            final TileColorable tile = (TileColorable)par1World.getTileEntity(par2, par3, par4);
            tile.rotation = l;
            tile.color = 15 - par6ItemStack.getMetadata();
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
            par1World.setBlock(par2, par3 + 1, par4, (Block)this, par6ItemStack.getMetadata() + 7, 2);
        }
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        final int meta = world.getBlockMetadata(x, y, z);
        if (meta >= 7) {
            this.setBlockBounds(0.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        }
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
        if (var2 < 7) {
            return new TileTallLamp();
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
}

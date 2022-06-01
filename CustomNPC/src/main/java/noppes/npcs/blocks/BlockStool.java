package noppes.npcs.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;

public class BlockStool extends BlockContainer
{
    public int renderId;
    
    public BlockStool() {
        super(Material.wood);
        this.renderId = -1;
        this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.6f, 0.9f);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World worldIn, final int x, final int y, final int z) {
        return AxisAlignedBB.getBoundingBox((double)(x + 0.1f), (double)y, (double)(z + 0.1f), (double)(x + 0.9f), (double)(y + 0.5f), (double)(z + 0.9f));
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
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float subX, final float subY, final float subZ) {
        return BlockChair.MountBlock(world, x, y, z, player);
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        l %= 4;
        final TileColorable tile = (TileColorable)par1World.getTileEntity(par2, par3, par4);
        tile.rotation = l;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
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
        return new TileStool();
    }
}

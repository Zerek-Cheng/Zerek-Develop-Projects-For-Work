package noppes.npcs.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import noppes.npcs.constants.*;
import noppes.npcs.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import noppes.npcs.*;
import net.minecraft.world.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class BlockBigSign extends BlockContainer
{
    public int renderId;
    
    public BlockBigSign() {
        super(Material.wood);
        this.renderId = -1;
    }
    
    public int damageDropped(final int par1) {
        return par1;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World worldIn, final int x, final int y, final int z) {
        return null;
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return false;
        }
        final ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == CustomItems.wand && CustomNpcsPermissions.hasPermission(player, CustomNpcsPermissions.EDIT_BLOCKS)) {
            final TileBigSign tile = (TileBigSign)par1World.getTileEntity(i, j, k);
            tile.canEdit = true;
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.BigSign, null, i, j, k);
            return true;
        }
        return false;
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        l %= 4;
        final TileBigSign tile = (TileBigSign)par1World.getTileEntity(par2, par3, par4);
        tile.rotation = l;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
        if (par5EntityLivingBase instanceof EntityPlayer && par1World.isRemote) {
            CustomNpcs.proxy.openGui(par2, par3, par4, EnumGuiType.BigSign, (EntityPlayer)par5EntityLivingBase);
        }
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        final TileEntity tileentity = world.getTileEntity(x, y, z);
        if (!(tileentity instanceof TileColorable)) {
            super.setBlockBoundsBasedOnState(world, x, y, z);
            return;
        }
        final TileColorable tile = (TileColorable)tileentity;
        final int meta = tile.getBlockMetadata();
        float xStart = 0.0f;
        float zStart = 0.0f;
        float xEnd = 1.0f;
        float zEnd = 1.0f;
        if (tile.rotation == 0) {
            zStart = 0.87f;
        }
        else if (tile.rotation == 2) {
            zEnd = 0.13f;
        }
        else if (tile.rotation == 3) {
            xStart = 0.87f;
        }
        else if (tile.rotation == 1) {
            xEnd = 0.13f;
        }
        this.setBlockBounds(xStart, 0.0f, zStart, xEnd, 1.0f, zEnd);
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
        return new TileBigSign();
    }
}

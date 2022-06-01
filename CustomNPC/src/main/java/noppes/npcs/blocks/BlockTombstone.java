package noppes.npcs.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import noppes.npcs.constants.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import noppes.npcs.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import noppes.npcs.blocks.tiles.*;

public class BlockTombstone extends BlockContainer
{
    public int renderId;
    
    public BlockTombstone() {
        super(Material.rock);
        this.renderId = -1;
    }
    
    public int damageDropped(final int par1) {
        return par1;
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return false;
        }
        final ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem == null || currentItem.getItem() != CustomItems.wand || !CustomNpcsPermissions.hasPermission(player, CustomNpcsPermissions.EDIT_BLOCKS)) {
            return false;
        }
        final TileBigSign tile = (TileBigSign)par1World.getTileEntity(i, j, k);
        if (tile.getBlockMetadata() >= 2) {
            return false;
        }
        tile.canEdit = true;
        NoppesUtilServer.sendOpenGui(player, EnumGuiType.BigSign, null, i, j, k);
        return true;
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        l %= 4;
        final TileBigSign tile = (TileBigSign)par1World.getTileEntity(par2, par3, par4);
        tile.rotation = l;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
        if (par5EntityLivingBase instanceof EntityPlayer && par1World.isRemote && par6ItemStack.getMetadata() < 2) {
            CustomNpcs.proxy.openGui(par2, par3, par4, EnumGuiType.BigSign, (EntityPlayer)par5EntityLivingBase);
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int x, final int y, final int z) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        final TileEntity tileentity = world.getTileEntity(x, y, z);
        if (!(tileentity instanceof TileColorable)) {
            super.setBlockBoundsBasedOnState(world, x, y, z);
            return;
        }
        final TileColorable tile = (TileColorable)tileentity;
        if (tile.rotation % 2 == 0) {
            this.setBlockBounds(0.0f, 0.0f, 0.3f, 1.0f, 1.0f, 0.7f);
        }
        else {
            this.setBlockBounds(0.3f, 0.0f, 0.0f, 0.7f, 1.0f, 1.0f);
        }
    }
    
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
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
        return Blocks.stone.getIcon(side, meta);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileTombstone();
    }
}

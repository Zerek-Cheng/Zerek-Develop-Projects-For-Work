package noppes.npcs.blocks;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.block.*;
import noppes.npcs.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;

public class BlockWallBanner extends BlockContainer
{
    public int renderId;
    
    public BlockWallBanner() {
        super(Material.rock);
        this.renderId = -1;
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        final ItemStack item = player.inventory.getCurrentItem();
        if (item == null) {
            return false;
        }
        final TileWallBanner tile = (TileWallBanner)par1World.getTileEntity(i, j, k);
        if (tile.canEdit()) {
            return true;
        }
        if (item.getItem() != Items.dye) {
            return false;
        }
        final int color = BlockColored.func_150031_c(item.getMetadata());
        if (tile.color != color) {
            NoppesUtilServer.consumeItemStack(1, player);
            tile.color = color;
            par1World.markBlockForUpdate(i, j, k);
        }
        return true;
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        l %= 4;
        final TileWallBanner tile = (TileWallBanner)par1World.getTileEntity(par2, par3, par4);
        tile.rotation = l;
        tile.color = 15 - par6ItemStack.getMetadata();
        tile.time = System.currentTimeMillis();
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
        if (par5EntityLivingBase instanceof EntityPlayer && par1World.isRemote) {
            ((EntityPlayer)par5EntityLivingBase).addChatComponentMessage((IChatComponent)new ChatComponentTranslation("availability.editIcon", new Object[0]));
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World worldIn, final int x, final int y, final int z) {
        return null;
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
        return new TileWallBanner();
    }
}

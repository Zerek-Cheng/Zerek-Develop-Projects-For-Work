package noppes.npcs.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import noppes.npcs.*;
import noppes.npcs.constants.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;

public class BlockBorder extends BlockContainer
{
    public int renderId;
    
    public BlockBorder() {
        super(Material.rock);
        this.renderId = -1;
        this.setBlockUnbreakable();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        if (side == 1) {
            return this.blockIcon;
        }
        return Blocks.iron_block.getIcon(side, meta);
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        final ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == CustomItems.wand) {
            CustomNpcs.proxy.openGui(i, j, k, EnumGuiType.Border, player);
            return true;
        }
        return false;
    }
    
    public void onBlockPlacedBy(final World par1World, final int x, final int y, final int z, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        l %= 4;
        final TileBorder tile = (TileBorder)par1World.getTileEntity(x, y, z);
        TileBorder adjacent = this.getTile(par1World, x - 1, y, z);
        if (adjacent == null) {
            adjacent = this.getTile(par1World, x, y, z - 1);
        }
        if (adjacent == null) {
            adjacent = this.getTile(par1World, x, y, z + 1);
        }
        if (adjacent == null) {
            adjacent = this.getTile(par1World, x + 1, y, z);
        }
        if (adjacent != null) {
            final NBTTagCompound compound = new NBTTagCompound();
            adjacent.writeExtraNBT(compound);
            tile.readExtraNBT(compound);
        }
        tile.rotation = l;
        if (par5EntityLivingBase instanceof EntityPlayer && par1World.isRemote) {
            CustomNpcs.proxy.openGui(x, y, z, EnumGuiType.Border, (EntityPlayer)par5EntityLivingBase);
        }
    }
    
    private TileBorder getTile(final World world, final int x, final int y, final int z) {
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileBorder) {
            return (TileBorder)tile;
        }
        return null;
    }
    
    public int getRenderType() {
        return this.renderId;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileBorder();
    }
}

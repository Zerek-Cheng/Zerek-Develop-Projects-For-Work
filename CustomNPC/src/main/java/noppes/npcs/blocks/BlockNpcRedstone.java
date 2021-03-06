package noppes.npcs.blocks;

import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import noppes.npcs.*;
import noppes.npcs.constants.*;
import net.minecraft.world.*;
import noppes.npcs.blocks.tiles.*;

public class BlockNpcRedstone extends BlockContainer
{
    public BlockNpcRedstone() {
        super(Material.rock);
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return false;
        }
        final ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == CustomItems.wand && CustomNpcsPermissions.hasPermission(player, CustomNpcsPermissions.EDIT_BLOCKS)) {
            final TileEntity tile = par1World.getTileEntity(i, j, k);
            final NBTTagCompound compound = new NBTTagCompound();
            tile.writeToNBT(compound);
            Server.sendData((EntityPlayerMP)player, EnumPacketClient.GUI_REDSTONE, compound);
            return true;
        }
        return false;
    }
    
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, (Block)this);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, (Block)this);
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entityliving, final ItemStack item) {
        if (entityliving instanceof EntityPlayer && world.isRemote) {
            CustomNpcs.proxy.openGui(i, j, k, EnumGuiType.RedstoneBlock, (EntityPlayer)entityliving);
        }
    }
    
    public void onBlockDestroyedByPlayer(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.onBlockAdded(par1World, par2, par3, par4);
    }
    
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        if (this.isActivated(par1IBlockAccess, par2, par3, par4) > 0) {
            return 16739176;
        }
        return super.colorMultiplier(par1IBlockAccess, par2, par3, par4);
    }
    
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return this.isActivated(par1IBlockAccess, par2, par3, par4);
    }
    
    public int isProvidingStrongPower(final IBlockAccess par1World, final int par2, final int par3, final int par4, final int par5) {
        return this.isActivated(par1World, par2, par3, par4);
    }
    
    public boolean canProvidePower() {
        return true;
    }
    
    public int isActivated(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 1) ? 15 : 0;
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileRedstoneBlock();
    }
}

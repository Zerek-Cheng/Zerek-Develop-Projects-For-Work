package noppes.npcs.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import noppes.npcs.*;
import noppes.npcs.constants.*;
import noppes.npcs.blocks.tiles.*;

public class BlockWaypoint extends BlockContainer
{
    public BlockWaypoint() {
        super(Material.iron);
        this.setCreativeTab((CreativeTabs)CustomItems.tab);
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
            Server.sendData((EntityPlayerMP)player, EnumPacketClient.GUI_WAYPOINT, compound);
            return true;
        }
        return false;
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entityliving, final ItemStack item) {
        if (entityliving instanceof EntityPlayer && world.isRemote) {
            CustomNpcs.proxy.openGui(i, j, k, EnumGuiType.Waypoint, (EntityPlayer)entityliving);
        }
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileWaypoint();
    }
}

package noppes.npcs.blocks;

import net.minecraft.world.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import noppes.npcs.constants.*;
import net.minecraft.nbt.*;
import noppes.npcs.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;

public class BlockBook extends BlockRotated
{
    public BlockBook() {
        super(Blocks.planks);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.2f, 1.0f);
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final TileEntity tile = par1World.getTileEntity(i, j, k);
        if (!(tile instanceof TileBook)) {
            return false;
        }
        final ItemStack currentItem = player.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == CustomItems.wand && CustomNpcsPermissions.hasPermission(player, CustomNpcsPermissions.EDIT_BLOCKS)) {
            ((TileBook)tile).book.setItem(Items.writable_book);
        }
        Server.sendData((EntityPlayerMP)player, EnumPacketClient.OPEN_BOOK, i, j, k, ((TileBook)tile).book.writeToNBT(new NBTTagCompound()));
        return true;
    }
    
    public String getUnlocalizedName() {
        return "item.book";
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileBook();
    }
}

package noppes.npcs.blocks.tiles;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;

public class TileWeaponRack extends TileNpcContainer
{
    @Override
    public boolean isItemValidForSlot(final int var1, final ItemStack itemstack) {
        return (itemstack == null || !(itemstack.getItem() instanceof ItemBlock)) && super.isItemValidForSlot(var1, itemstack);
    }
    
    @Override
    public int getSizeInventory() {
        return 3;
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 2), (double)(this.zCoord + 1));
    }
    
    @Override
    public String getName() {
        return "tile.npcWeaponRack.name";
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        final S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, compound);
        return (Packet)packet;
    }
    
    @Override
    public int powerProvided() {
        int power = 0;
        for (int i = 0; i < 3; ++i) {
            if (this.getStackInSlot(i) != null) {
                power += 5;
            }
        }
        return power;
    }
}

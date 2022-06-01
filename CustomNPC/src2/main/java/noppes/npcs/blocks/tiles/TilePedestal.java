package noppes.npcs.blocks.tiles;

import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;

public class TilePedestal extends TileNpcContainer
{
    @Override
    public String getName() {
        return "tile.npcPedestal.name";
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 2), (double)(this.zCoord + 1));
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
        return (this.getStackInSlot(0) == null) ? 0 : 15;
    }
}

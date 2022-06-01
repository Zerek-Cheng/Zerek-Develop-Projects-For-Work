package noppes.npcs.blocks.tiles;

import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class TileColorable extends TileEntity
{
    public int color;
    public int rotation;
    
    public TileColorable() {
        this.color = 14;
    }
    
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.color = compound.getInteger("BannerColor");
        this.rotation = compound.getInteger("BannerRotation");
    }
    
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BannerColor", this.color);
        compound.setInteger("BannerRotation", this.rotation);
    }
    
    public boolean canUpdate() {
        return false;
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
        final NBTTagCompound compound = pkt.getNbtCompound();
        this.readFromNBT(compound);
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        compound.removeTag("Items");
        final S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, compound);
        return (Packet)packet;
    }
    
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1));
    }
    
    public int powerProvided() {
        return 0;
    }
}

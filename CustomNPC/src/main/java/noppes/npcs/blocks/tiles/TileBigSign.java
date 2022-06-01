package noppes.npcs.blocks.tiles;

import net.minecraft.tileentity.*;
import noppes.npcs.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

public class TileBigSign extends TileEntity
{
    public int rotation;
    public boolean canEdit;
    public boolean hasChanged;
    private String signText;
    public TextBlock block;
    
    public TileBigSign() {
        this.canEdit = true;
        this.hasChanged = true;
        this.signText = "";
    }
    
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.rotation = compound.getInteger("SignRotation");
        this.setText(compound.getString("SignText"));
    }
    
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("SignRotation", this.rotation);
        compound.setString("SignText", this.signText);
    }
    
    public boolean canUpdate() {
        return false;
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
        final NBTTagCompound compound = pkt.getNbtCompound();
        this.readFromNBT(compound);
    }
    
    public void setText(final String text) {
        this.signText = text;
        this.hasChanged = true;
    }
    
    public String getText() {
        return this.signText;
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        final S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, compound);
        return (Packet)packet;
    }
}

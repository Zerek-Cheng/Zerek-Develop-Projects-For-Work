package noppes.npcs.blocks.tiles;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class TileBanner extends TileColorable
{
    public ItemStack icon;
    public long time;
    
    public TileBanner() {
        this.time = 0L;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.icon = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("BannerIcon"));
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (this.icon != null) {
            compound.setTag("BannerIcon", (NBTBase)this.icon.writeToNBT(new NBTTagCompound()));
        }
    }
    
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 2), (double)(this.zCoord + 1));
    }
    
    public boolean canEdit() {
        return System.currentTimeMillis() - this.time < 10000L;
    }
}

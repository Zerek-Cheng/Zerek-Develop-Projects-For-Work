/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.BlockState
 */
package su.nightexpress.divineitems.nbt;

import org.bukkit.block.BlockState;
import su.nightexpress.divineitems.nbt.NBTCompound;
import su.nightexpress.divineitems.nbt.NBTReflectionUtil;

public class NBTTileEntity
extends NBTCompound {
    private final BlockState tile;

    public NBTTileEntity(BlockState blockState) {
        super(null, null);
        this.tile = blockState;
    }

    @Override
    protected Object getCompound() {
        return NBTReflectionUtil.getTileEntityNBTTagCompound(this.tile);
    }

    @Override
    protected void setCompound(Object object) {
        NBTReflectionUtil.setTileEntityNBTTagCompound(this.tile, object);
    }
}


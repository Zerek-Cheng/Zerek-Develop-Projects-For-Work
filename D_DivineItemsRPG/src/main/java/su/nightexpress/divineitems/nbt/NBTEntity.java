/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 */
package su.nightexpress.divineitems.nbt;

import org.bukkit.entity.Entity;
import su.nightexpress.divineitems.nbt.NBTCompound;
import su.nightexpress.divineitems.nbt.NBTReflectionUtil;

public class NBTEntity
extends NBTCompound {
    private final Entity ent;

    public NBTEntity(Entity entity) {
        super(null, null);
        this.ent = entity;
    }

    @Override
    protected Object getCompound() {
        return NBTReflectionUtil.getEntityNBTTagCompound(NBTReflectionUtil.getNMSEntity(this.ent));
    }

    @Override
    protected void setCompound(Object object) {
        NBTReflectionUtil.setEntityNBTTag(object, NBTReflectionUtil.getNMSEntity(this.ent));
    }
}


/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.nbt;

import su.nightexpress.divineitems.nbt.NBTCompound;
import su.nightexpress.divineitems.nbt.ObjectCreator;
import su.nightexpress.divineitems.nbt.ReflectionMethod;

public class NBTContainer
extends NBTCompound {
    private Object nbt;

    public NBTContainer() {
        super(null, null);
        this.nbt = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
    }

    protected NBTContainer(Object object) {
        super(null, null);
        this.nbt = object;
    }

    public NBTContainer(String string) {
        super(null, null);
        try {
            this.nbt = ReflectionMethod.PARSE_NBT.run(null, string);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalArgumentException("Malformed Json: " + exception.getMessage());
        }
    }

    @Override
    protected Object getCompound() {
        return this.nbt;
    }

    @Override
    protected void setCompound(Object object) {
        this.nbt = object;
    }
}


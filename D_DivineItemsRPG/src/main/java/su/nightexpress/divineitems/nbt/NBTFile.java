/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.nbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import su.nightexpress.divineitems.nbt.NBTCompound;
import su.nightexpress.divineitems.nbt.NBTReflectionUtil;
import su.nightexpress.divineitems.nbt.ObjectCreator;

public class NBTFile
extends NBTCompound {
    private final File file;
    private Object nbt;

    public NBTFile(File file) {
        super(null, null);
        this.file = file;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            this.nbt = NBTReflectionUtil.readNBTFile(fileInputStream);
        } else {
            this.nbt = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
            this.save();
        }
    }

    public void save() {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            this.file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(this.file);
        NBTReflectionUtil.saveNBTFile(this.nbt, fileOutputStream);
    }

    public File getFile() {
        return this.file;
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


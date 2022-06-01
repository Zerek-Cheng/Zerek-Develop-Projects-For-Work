/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.nbt;

import java.util.Set;
import su.nightexpress.divineitems.nbt.NBTList;
import su.nightexpress.divineitems.nbt.NBTReflectionUtil;
import su.nightexpress.divineitems.nbt.NBTType;
import su.nightexpress.divineitems.nbt.ReflectionMethod;
import su.nightexpress.divineitems.nbt.utils.MinecraftVersion;

public class NBTCompound {
    private String compundName;
    private NBTCompound parent;

    protected NBTCompound(NBTCompound nBTCompound, String string) {
        this.compundName = string;
        this.parent = nBTCompound;
    }

    public String getName() {
        return this.compundName;
    }

    protected Object getCompound() {
        return this.parent.getCompound();
    }

    protected void setCompound(Object object) {
        this.parent.setCompound(object);
    }

    public NBTCompound getParent() {
        return this.parent;
    }

    public void mergeCompound(NBTCompound nBTCompound) {
        NBTReflectionUtil.addOtherNBTCompound(this, nBTCompound);
    }

    public void setString(String string, String string2) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_STRING, string, string2);
    }

    public String getString(String string) {
        return (String)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_STRING, string);
    }

    protected String getContent(String string) {
        return NBTReflectionUtil.getContent(this, string);
    }

    public void setInteger(String string, Integer n) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_INT, string, n);
    }

    public Integer getInteger(String string) {
        return (Integer)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_INT, string);
    }

    public void setDouble(String string, Double d) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_DOUBLE, string, d);
    }

    public Double getDouble(String string) {
        return (Double)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_DOUBLE, string);
    }

    public void setByte(String string, Byte by) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BYTE, string, by);
    }

    public Byte getByte(String string) {
        return (Byte)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BYTE, string);
    }

    public void setShort(String string, Short s) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_SHORT, string, s);
    }

    public Short getShort(String string) {
        return (Short)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_SHORT, string);
    }

    public void setLong(String string, Long l) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_LONG, string, l);
    }

    public Long getLong(String string) {
        return (Long)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_LONG, string);
    }

    public void setFloat(String string, Float f) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_FLOAT, string, f);
    }

    public Float getFloat(String string) {
        return (Float)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_FLOAT, string);
    }

    public void setByteArray(String string, byte[] arrby) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BYTEARRAY, string, arrby);
    }

    public byte[] getByteArray(String string) {
        return (byte[])NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BYTEARRAY, string);
    }

    public void setIntArray(String string, int[] arrn) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_INTARRAY, string, arrn);
    }

    public int[] getIntArray(String string) {
        return (int[])NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_INTARRAY, string);
    }

    public void setBoolean(String string, Boolean bl) {
        NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BOOLEAN, string, bl);
    }

    protected void set(String string, Object object) {
        NBTReflectionUtil.set(this, string, object);
    }

    public Boolean getBoolean(String string) {
        return (Boolean)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BOOLEAN, string);
    }

    public void setObject(String string, Object object) {
        NBTReflectionUtil.setObject(this, string, object);
    }

    public <T> T getObject(String string, Class<T> class_) {
        return NBTReflectionUtil.getObject(this, string, class_);
    }

    public Boolean hasKey(String string) {
        if (this.getKeys().isEmpty()) {
            return false;
        }
        return (Boolean)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_HAS_KEY, string);
    }

    public void removeKey(String string) {
        NBTReflectionUtil.remove(this, string);
    }

    public Set<String> getKeys() {
        return NBTReflectionUtil.getKeys(this);
    }

    public NBTCompound addCompound(String string) {
        NBTReflectionUtil.addNBTTagCompound(this, string);
        return this.getCompound(string);
    }

    public NBTCompound getCompound(String string) {
        NBTCompound nBTCompound = new NBTCompound(this, string);
        if (NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            return nBTCompound;
        }
        return null;
    }

    public NBTList getList(String string, NBTType nBTType) {
        return NBTReflectionUtil.getList(this, string, nBTType);
    }

    public NBTType getType(String string) {
        if (MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4) {
            return NBTType.NBTTagEnd;
        }
        return NBTType.valueOf(((Byte)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_TYPE, string)).byteValue());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : this.getKeys()) {
            stringBuilder.append(this.toString(string));
        }
        return stringBuilder.toString();
    }

    public String toString(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        NBTCompound nBTCompound = this;
        while (nBTCompound.getParent() != null) {
            stringBuilder.append("   ");
            nBTCompound = nBTCompound.getParent();
        }
        if (this.getType(string) == NBTType.NBTTagCompound) {
            return this.getCompound(string).toString();
        }
        return stringBuilder + "-" + string + ": " + this.getContent(string) + System.lineSeparator();
    }

    public String asNBTString() {
        return NBTReflectionUtil.gettoCompount(this.getCompound(), this).toString();
    }
}


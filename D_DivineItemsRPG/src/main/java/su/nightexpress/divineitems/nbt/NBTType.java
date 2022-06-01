/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.nbt;

public enum NBTType {
    NBTTagEnd(0),
    NBTTagByte(1),
    NBTTagShort(2),
    NBTTagInt(3),
    NBTTagLong(4),
    NBTTagFloat(5),
    NBTTagDouble(6),
    NBTTagByteArray(7),
    NBTTagIntArray(11),
    NBTTagString(8),
    NBTTagList(9),
    NBTTagCompound(10);
    
    private final int id;

    private NBTType(String string2, int n2, int n3) {
        this.id = (int)string2;
    }

    public int getId() {
        return this.id;
    }

    public static NBTType valueOf(String string) {
        return Enum.valueOf(NBTType.class, string);
    }
}


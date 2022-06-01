/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package su.nightexpress.divineitems.nbt;

import java.lang.reflect.Method;
import org.bukkit.inventory.ItemStack;
import su.nightexpress.divineitems.nbt.ClassWrapper;
import su.nightexpress.divineitems.nbt.utils.MinecraftVersion;

public enum ReflectionMethod {
    COMPOUND_SET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Float.TYPE}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setFloat")}),
    COMPOUND_SET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setString")}),
    COMPOUND_SET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Integer.TYPE}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setInt")}),
    COMPOUND_SET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, byte[].class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setByteArray")}),
    COMPOUND_SET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, int[].class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setIntArray")}),
    COMPOUND_SET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Long.TYPE}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setLong")}),
    COMPOUND_SET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Short.TYPE}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setShort")}),
    COMPOUND_SET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Byte.TYPE}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setByte")}),
    COMPOUND_SET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Double.TYPE}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setDouble")}),
    COMPOUND_SET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class, Boolean.TYPE}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setBoolean")}),
    COMPOUND_GET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getFloat")}),
    COMPOUND_GET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getString")}),
    COMPOUND_GET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getInt")}),
    COMPOUND_GET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getByteArray")}),
    COMPOUND_GET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getIntArray")}),
    COMPOUND_GET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getLong")}),
    COMPOUND_GET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getShort")}),
    COMPOUND_GET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getByte")}),
    COMPOUND_GET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getDouble")}),
    COMPOUND_GET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "getBoolean")}),
    COMPOUND_REMOVE_KEY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "remove")}),
    COMPOUND_HAS_KEY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "hasKey")}),
    COMPOUND_GET_TYPE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_8_R3, new Since[]{new Since(MinecraftVersion.MC1_8_R3, "b"), new Since(MinecraftVersion.MC1_9_R1, "d")}),
    COMPOUND_GET_KEYS(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[0], MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "c"), new Since(MinecraftVersion.MC1_13_R1, "getKeys")}),
    LISTCOMPOUND_GET_KEYS(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[0], MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "c"), new Since(MinecraftVersion.MC1_13_R1, "getKeys")}),
    ITEMSTACK_SET_TAG(ClassWrapper.NMS_ITEMSTACK.getClazz(), new Class[]{ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "setTag")}),
    ITEMSTACK_NMSCOPY(ClassWrapper.CRAFT_ITEMSTACK.getClazz(), new Class[]{ItemStack.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "asNMSCopy")}),
    ITEMSTACK_BUKKITMIRROR(ClassWrapper.CRAFT_ITEMSTACK.getClazz(), new Class[]{ClassWrapper.NMS_ITEMSTACK.getClazz()}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "asCraftMirror")}),
    PARSE_NBT(ClassWrapper.NMS_MOJANGSONPARSER.getClazz(), new Class[]{String.class}, MinecraftVersion.MC1_7_R4, new Since[]{new Since(MinecraftVersion.MC1_7_R4, "parse")});
    
    private Since targetVersion;
    private Method method;
    private boolean loaded = false;
    private boolean compatible = false;

    private ReflectionMethod(Class<?> class_, Class<?>[] arrclass, MinecraftVersion minecraftVersion, Since[] arrsince) {
        MinecraftVersion minecraftVersion2 = MinecraftVersion.getVersion();
        if (minecraftVersion2.compareTo(minecraftVersion) < 0) {
            return;
        }
        this.compatible = true;
        Since since = arrsince[0];
        Since[] arrsince2 = arrsince;
        int n2 = arrsince2.length;
        int n3 = 0;
        while (n3 < n2) {
            Since since2 = arrsince2[n3];
            if (since2.version.getVersionId() <= minecraftVersion2.getVersionId() && since.version.getVersionId() < since2.version.getVersionId()) {
                since = since2;
            }
            ++n3;
        }
        this.targetVersion = since;
        try {
            this.method = class_.getMethod(this.targetVersion.name, arrclass);
            this.method.setAccessible(true);
            this.loaded = true;
        }
        catch (NoSuchMethodException | NullPointerException | SecurityException exception) {
            exception.printStackTrace();
        }
    }

    public /* varargs */ Object run(Object object, Object ... arrobject) {
        try {
            return this.method.invoke(object, arrobject);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public boolean isCompatible() {
        return this.compatible;
    }

    public static class Since {
        public final MinecraftVersion version;
        public final String name;

        public Since(MinecraftVersion minecraftVersion, String string) {
            this.version = minecraftVersion;
            this.name = string;
        }
    }

}


/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.block.BlockState
 *  org.bukkit.entity.Entity
 */
package su.nightexpress.divineitems.nbt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Stack;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import su.nightexpress.divineitems.nbt.ClassWrapper;
import su.nightexpress.divineitems.nbt.NBTCompound;
import su.nightexpress.divineitems.nbt.NBTContainer;
import su.nightexpress.divineitems.nbt.NBTList;
import su.nightexpress.divineitems.nbt.NBTType;
import su.nightexpress.divineitems.nbt.ObjectCreator;
import su.nightexpress.divineitems.nbt.ReflectionMethod;
import su.nightexpress.divineitems.nbt.utils.GsonWrapper;
import su.nightexpress.divineitems.nbt.utils.MethodNames;
import su.nightexpress.divineitems.nbt.utils.MinecraftVersion;

public class NBTReflectionUtil {
    public static Object getNMSEntity(Entity entity) {
        Class<?> class_ = ClassWrapper.CRAFT_ENTITY.getClazz();
        try {
            Method method = class_.getMethod("getHandle", new Class[0]);
            return method.invoke(class_.cast((Object)entity), new Object[0]);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object readNBTFile(FileInputStream fileInputStream) {
        Class<?> class_ = ClassWrapper.NMS_NBTCOMPRESSEDSTREAMTOOLS.getClazz();
        try {
            Method method = class_.getMethod("a", InputStream.class);
            return method.invoke(class_, fileInputStream);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object saveNBTFile(Object object, FileOutputStream fileOutputStream) {
        Class<?> class_ = ClassWrapper.NMS_NBTCOMPRESSEDSTREAMTOOLS.getClazz();
        try {
            Method method = class_.getMethod("a", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), OutputStream.class);
            return method.invoke(class_, object, fileOutputStream);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object getItemRootNBTTagCompound(Object object) {
        Class<?> class_ = object.getClass();
        try {
            Method method = class_.getMethod("getTag", new Class[0]);
            Object object2 = method.invoke(object, new Object[0]);
            return object2;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object convertNBTCompoundtoNMSItem(NBTCompound nBTCompound) {
        Class<?> class_ = ClassWrapper.NMS_ITEMSTACK.getClazz();
        try {
            Object obj = class_.getConstructor(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz()).newInstance(nBTCompound.getCompound());
            return obj;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static NBTContainer convertNMSItemtoNBTCompound(Object object) {
        Class<?> class_ = object.getClass();
        try {
            Method method = class_.getMethod("save", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
            Object object2 = method.invoke(object, ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]));
            return new NBTContainer(object2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object getEntityNBTTagCompound(Object object) {
        Class<?> class_ = object.getClass();
        try {
            Method method = class_.getMethod(MethodNames.getEntityNbtGetterMethodName(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
            Object obj = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            Object object2 = method.invoke(object, obj);
            if (object2 == null) {
                object2 = obj;
            }
            return object2;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object setEntityNBTTag(Object object, Object object2) {
        try {
            Method method = object2.getClass().getMethod(MethodNames.getEntityNbtSetterMethodName(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
            method.invoke(object2, object);
            return object2;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object getTileEntityNBTTagCompound(BlockState blockState) {
        try {
            Object object = ObjectCreator.NMS_BLOCKPOSITION.getInstance(blockState.getX(), blockState.getY(), blockState.getZ());
            Object obj = ClassWrapper.CRAFT_WORLD.getClazz().cast((Object)blockState.getWorld());
            Object object2 = obj.getClass().getMethod("getHandle", new Class[0]).invoke(obj, new Object[0]);
            Object object3 = object2.getClass().getMethod("getTileEntity", object.getClass()).invoke(object2, object);
            Method method = ClassWrapper.NMS_TILEENTITY.getClazz().getMethod(MethodNames.getTileDataMethodName(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
            Object obj2 = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            Object object4 = method.invoke(object3, obj2);
            if (object4 == null) {
                object4 = obj2;
            }
            return object4;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void setTileEntityNBTTagCompound(BlockState blockState, Object object) {
        try {
            Object object2 = ObjectCreator.NMS_BLOCKPOSITION.getInstance(blockState.getX(), blockState.getY(), blockState.getZ());
            Object obj = ClassWrapper.CRAFT_WORLD.getClazz().cast((Object)blockState.getWorld());
            Object object3 = obj.getClass().getMethod("getHandle", new Class[0]).invoke(obj, new Object[0]);
            Object object4 = object3.getClass().getMethod("getTileEntity", object2.getClass()).invoke(object3, object2);
            Method method = ClassWrapper.NMS_TILEENTITY.getClazz().getMethod("a", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
            method.invoke(object4, object);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Object getSubNBTTagCompound(Object object, String string) {
        Class<?> class_ = object.getClass();
        try {
            Method method = class_.getMethod("getCompound", String.class);
            Object object2 = method.invoke(object, string);
            return object2;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void addNBTTagCompound(NBTCompound nBTCompound, String string) {
        if (string == null) {
            NBTReflectionUtil.remove(nBTCompound, string);
            return;
        }
        Object object = nBTCompound.getCompound();
        if (object == null) {
            object = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            return;
        }
        Object object2 = NBTReflectionUtil.gettoCompount(object, nBTCompound);
        try {
            Method method = object2.getClass().getMethod("set", String.class, ClassWrapper.NMS_NBTBASE.getClazz());
            method.invoke(object2, string, ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance());
            nBTCompound.setCompound(object);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Boolean valideCompound(NBTCompound nBTCompound) {
        Object object = nBTCompound.getCompound();
        if (object == null) {
            object = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (NBTReflectionUtil.gettoCompount(object, nBTCompound) != null) {
            return true;
        }
        return false;
    }

    static Object gettoCompount(Object object, NBTCompound nBTCompound) {
        Stack<String> stack = new Stack<String>();
        while (nBTCompound.getParent() != null) {
            stack.add(nBTCompound.getName());
            nBTCompound = nBTCompound.getParent();
        }
        while (!stack.isEmpty()) {
            if ((object = NBTReflectionUtil.getSubNBTTagCompound(object, (String)stack.pop())) != null) continue;
            return null;
        }
        return object;
    }

    public static void addOtherNBTCompound(NBTCompound nBTCompound, NBTCompound nBTCompound2) {
        Object object = nBTCompound.getCompound();
        if (object == null) {
            object = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            return;
        }
        Object object2 = NBTReflectionUtil.gettoCompount(object, nBTCompound);
        try {
            Method method = object2.getClass().getMethod("a", ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz());
            method.invoke(object2, nBTCompound2.getCompound());
            nBTCompound.setCompound(object);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String getContent(NBTCompound nBTCompound, String string) {
        Object object = nBTCompound.getCompound();
        if (object == null) {
            object = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            return null;
        }
        Object object2 = NBTReflectionUtil.gettoCompount(object, nBTCompound);
        try {
            Method method = object2.getClass().getMethod("get", String.class);
            return method.invoke(object2, string).toString();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void set(NBTCompound nBTCompound, String string, Object object) {
        if (object == null) {
            NBTReflectionUtil.remove(nBTCompound, string);
            return;
        }
        Object object2 = nBTCompound.getCompound();
        if (object2 == null) {
            object2 = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            new Throwable("InvalideCompound").printStackTrace();
            return;
        }
        Object object3 = NBTReflectionUtil.gettoCompount(object2, nBTCompound);
        try {
            Method method = object3.getClass().getMethod("set", String.class, ClassWrapper.NMS_NBTBASE.getClazz());
            method.invoke(object3, string, object);
            nBTCompound.setCompound(object2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static NBTList getList(NBTCompound nBTCompound, String string, NBTType nBTType) {
        Object object = nBTCompound.getCompound();
        if (object == null) {
            object = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            return null;
        }
        Object object2 = NBTReflectionUtil.gettoCompount(object, nBTCompound);
        try {
            Method method = object2.getClass().getMethod("getList", String.class, Integer.TYPE);
            return new NBTList(nBTCompound, string, nBTType, method.invoke(object2, string, nBTType.getId()));
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void setObject(NBTCompound nBTCompound, String string, Object object) {
        if (!MinecraftVersion.hasGsonSupport()) {
            return;
        }
        try {
            String string2 = GsonWrapper.getString(object);
            NBTReflectionUtil.setData(nBTCompound, ReflectionMethod.COMPOUND_SET_STRING, string, string2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static <T> T getObject(NBTCompound nBTCompound, String string, Class<T> class_) {
        if (!MinecraftVersion.hasGsonSupport()) {
            return null;
        }
        String string2 = (String)NBTReflectionUtil.getData(nBTCompound, ReflectionMethod.COMPOUND_GET_STRING, string);
        if (string2 == null) {
            return null;
        }
        return GsonWrapper.deserializeJson(string2, class_);
    }

    public static void remove(NBTCompound nBTCompound, String string) {
        Object object = nBTCompound.getCompound();
        if (object == null) {
            object = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            return;
        }
        Object object2 = NBTReflectionUtil.gettoCompount(object, nBTCompound);
        ReflectionMethod.COMPOUND_REMOVE_KEY.run(object2, string);
        nBTCompound.setCompound(object);
    }

    public static Set<String> getKeys(NBTCompound nBTCompound) {
        Object object = nBTCompound.getCompound();
        if (object == null) {
            object = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            return null;
        }
        Object object2 = NBTReflectionUtil.gettoCompount(object, nBTCompound);
        return (Set)ReflectionMethod.COMPOUND_GET_KEYS.run(object2, new Object[0]);
    }

    public static void setData(NBTCompound nBTCompound, ReflectionMethod reflectionMethod, String string, Object object) {
        if (object == null) {
            NBTReflectionUtil.remove(nBTCompound, string);
            return;
        }
        Object object2 = nBTCompound.getCompound();
        if (object2 == null) {
            object2 = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            return;
        }
        Object object3 = NBTReflectionUtil.gettoCompount(object2, nBTCompound);
        reflectionMethod.run(object3, string, object);
        nBTCompound.setCompound(object2);
    }

    public static Object getData(NBTCompound nBTCompound, ReflectionMethod reflectionMethod, String string) {
        Object object = nBTCompound.getCompound();
        if (object == null) {
            return null;
        }
        if (!NBTReflectionUtil.valideCompound(nBTCompound).booleanValue()) {
            return null;
        }
        Object object2 = NBTReflectionUtil.gettoCompount(object, nBTCompound);
        return reflectionMethod.run(object2, string);
    }
}


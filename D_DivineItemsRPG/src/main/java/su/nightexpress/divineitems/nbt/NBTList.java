/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.nbt;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import su.nightexpress.divineitems.nbt.ClassWrapper;
import su.nightexpress.divineitems.nbt.NBTCompound;
import su.nightexpress.divineitems.nbt.NBTListCompound;
import su.nightexpress.divineitems.nbt.NBTType;
import su.nightexpress.divineitems.nbt.utils.MethodNames;

public class NBTList {
    private String listName;
    private NBTCompound parent;
    private NBTType type;
    private Object listObject;

    protected NBTList(NBTCompound nBTCompound, String string, NBTType nBTType, Object object) {
        this.parent = nBTCompound;
        this.listName = string;
        this.type = nBTType;
        this.listObject = object;
        if (nBTType != NBTType.NBTTagString && nBTType != NBTType.NBTTagCompound) {
            System.err.println("List types != String/Compound are currently not implemented!");
        }
    }

    protected void save() {
        this.parent.set(this.listName, this.listObject);
    }

    public NBTListCompound addCompound() {
        if (this.type != NBTType.NBTTagCompound) {
            new Throwable("Using Compound method on a non Compound list!").printStackTrace();
            return null;
        }
        try {
            Method method = this.listObject.getClass().getMethod("add", ClassWrapper.NMS_NBTBASE.getClazz());
            Object obj = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            method.invoke(this.listObject, obj);
            return new NBTListCompound(this, obj);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public NBTListCompound getCompound(int n) {
        if (this.type != NBTType.NBTTagCompound) {
            new Throwable("Using Compound method on a non Compound list!").printStackTrace();
            return null;
        }
        try {
            Method method = this.listObject.getClass().getMethod("get", Integer.TYPE);
            Object object = method.invoke(this.listObject, n);
            return new NBTListCompound(this, object);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public String getString(int n) {
        if (this.type != NBTType.NBTTagString) {
            new Throwable("Using String method on a non String list!").printStackTrace();
            return null;
        }
        try {
            Method method = this.listObject.getClass().getMethod("getString", Integer.TYPE);
            return (String)method.invoke(this.listObject, n);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void addString(String string) {
        if (this.type != NBTType.NBTTagString) {
            new Throwable("Using String method on a non String list!").printStackTrace();
            return;
        }
        try {
            Method method = this.listObject.getClass().getMethod("add", ClassWrapper.NMS_NBTBASE.getClazz());
            method.invoke(this.listObject, ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(string));
            this.save();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setString(int n, String string) {
        if (this.type != NBTType.NBTTagString) {
            new Throwable("Using String method on a non String list!").printStackTrace();
            return;
        }
        try {
            Method method = this.listObject.getClass().getMethod("a", Integer.TYPE, ClassWrapper.NMS_NBTBASE.getClazz());
            method.invoke(this.listObject, n, ClassWrapper.NMS_NBTTAGSTRING.getClazz().getConstructor(String.class).newInstance(string));
            this.save();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void remove(int n) {
        try {
            Method method = this.listObject.getClass().getMethod(MethodNames.getRemoveMethodName(), Integer.TYPE);
            method.invoke(this.listObject, n);
            this.save();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public int size() {
        try {
            Method method = this.listObject.getClass().getMethod("size", new Class[0]);
            return (Integer)method.invoke(this.listObject, new Object[0]);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    public NBTType getType() {
        return this.type;
    }
}


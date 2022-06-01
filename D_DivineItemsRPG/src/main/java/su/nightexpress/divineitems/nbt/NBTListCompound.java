/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.nbt;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import su.nightexpress.divineitems.nbt.NBTList;
import su.nightexpress.divineitems.nbt.ReflectionMethod;

public class NBTListCompound {
    private NBTList owner;
    private Object compound;

    protected NBTListCompound(NBTList nBTList, Object object) {
        this.owner = nBTList;
        this.compound = object;
    }

    public void setString(String string, String string2) {
        if (string2 == null) {
            this.remove(string);
            return;
        }
        try {
            this.compound.getClass().getMethod("setString", String.class, String.class).invoke(this.compound, string, string2);
            this.owner.save();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setInteger(String string, int n) {
        try {
            this.compound.getClass().getMethod("setInt", String.class, Integer.TYPE).invoke(this.compound, string, n);
            this.owner.save();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public int getInteger(String string) {
        try {
            return (Integer)this.compound.getClass().getMethod("getInt", String.class).invoke(this.compound, string);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public void setDouble(String string, double d) {
        try {
            this.compound.getClass().getMethod("setDouble", String.class, Double.TYPE).invoke(this.compound, string, d);
            this.owner.save();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public double getDouble(String string) {
        try {
            return (Double)this.compound.getClass().getMethod("getDouble", String.class).invoke(this.compound, string);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return 0.0;
        }
    }

    public String getString(String string) {
        try {
            return (String)this.compound.getClass().getMethod("getString", String.class).invoke(this.compound, string);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public boolean hasKey(String string) {
        try {
            return (Boolean)this.compound.getClass().getMethod("hasKey", String.class).invoke(this.compound, string);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public Set<String> getKeys() {
        try {
            return (Set)ReflectionMethod.LISTCOMPOUND_GET_KEYS.run(this.compound, new Object[0]);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new HashSet<String>();
        }
    }

    public void remove(String string) {
        try {
            this.compound.getClass().getMethod("remove", String.class).invoke(this.compound, string);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}


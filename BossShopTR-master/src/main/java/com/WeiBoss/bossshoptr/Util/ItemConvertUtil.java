package com.WeiBoss.bossshoptr.Util;

import com.WeiBoss.bossshoptr.Main;
import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ItemConvertUtil {
    private static Main plugin = Main.instance;
    private final String nms;
    private final String cb;
    private Class craftItemStack;
    private Class nbtTagCompound;
    private Class nbtReadLimiter;
    private Class itemStack;
    private Constructor a;
    private Constructor b;
    private Constructor c;
    private Method save;
    private Method write;
    private Method load;
    private Method create;
    private Field handle;
    private Object object;

    public ItemConvertUtil(String version) {
        this.nms = ("net.minecraft.server." + version);
        this.cb = ("org.bukkit.craftbukkit." + version);
    }

    public String convert(ItemStack item) {
/*        try {
            if (this.craftItemStack == null) {
                load();
            }
            if (this.a == null) {
                this.a = this.craftItemStack.getDeclaredConstructor(new Class[]{ItemStack.class});
                this.a.setAccessible(true);
            }
            if (this.handle == null) {
                if (this.handle == null) {
                    this.handle = this.craftItemStack.getDeclaredField("handle");
                    this.handle.setAccessible(true);
                }
            }
            Object outPut = this.nbtTagCompound.newInstance();
            if (this.save == null) {
                this.save = this.itemStack.getMethod("save", new Class[]{this.nbtTagCompound});
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if (this.write == null) {
                this.write = this.nbtTagCompound.getDeclaredMethod("write", new Class[]{DataOutput.class});
                this.write.setAccessible(true);
            }
            Object objectA = this.craftItemStack.isInstance(item) ? item : this.a.newInstance(new Object[]{item});
            Object objectB = this.handle.get(objectA);
            this.save.invoke(objectB, new Object[]{outPut});
            this.write.invoke(outPut, new Object[]{new DataOutputStream(outputStream)});
            String base64 = new String(Base64Coder.encode(outputStream.toByteArray()));
            return base64;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/
        try {
            return StreamSerializer.getDefault().serializeItemStack(item);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ItemStack convert(String item) {/*
        try {
            if (this.craftItemStack == null) {
                load();
            }
            Object tag = this.nbtTagCompound.newInstance();
            if (this.load == null) {
                this.load = this.nbtTagCompound.getDeclaredMethod("load", new Class[]{DataInput.class, Integer.TYPE, this.nbtReadLimiter});
                this.load.setAccessible(true);
            }
            DataInput input = new DataInputStream(new ByteArrayInputStream(Base64Coder.decode(item)));
            this.load.invoke(tag, new Object[]{input, Integer.valueOf(0), object()});
            if (this.create == null && this.b == null) {
                try {
                    this.create = this.itemStack.getMethod("createStack", new Class[]{this.nbtTagCompound});
                } catch (NoSuchMethodException e) {
                    this.b = this.itemStack.getDeclaredConstructor(new Class[]{this.nbtTagCompound});
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
            Object objectA = this.b != null ? this.b.newInstance(new Object[]{tag}) : this.create.invoke(this.itemStack, new Object[]{tag});
            if (this.c == null) {
                this.c = this.craftItemStack.getDeclaredConstructor(new Class[]{this.itemStack});
                this.c.setAccessible(true);
            }
            ItemStack items = (ItemStack) this.c.newInstance(new Object[]{objectA});
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/

        try {
            return StreamSerializer.getDefault().deserializeItemStack(item);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Object object() throws Exception {
        if (this.object == null) {
            this.object = this.nbtReadLimiter.getField("a").get(this.nbtReadLimiter);
        }
        return this.object;
    }

    private void load() throws Exception {
        this.itemStack = find(this.nms + ".ItemStack");
        this.craftItemStack = find(this.cb + ".inventory.CraftItemStack");
        this.nbtTagCompound = find(this.nms + ".NBTTagCompound");
        this.nbtReadLimiter = find(this.nms + ".NBTReadLimiter");
    }

    private Class find(String path) throws Exception {
        return getClass().getClassLoader().loadClass(path);
    }
}
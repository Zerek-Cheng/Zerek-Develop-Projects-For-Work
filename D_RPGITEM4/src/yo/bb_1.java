/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.utility.MinecraftReflection
 *  com.comphenix.protocol.utility.StreamSerializer
 *  com.comphenix.protocol.wrappers.collection.ConvertedList
 *  com.comphenix.protocol.wrappers.nbt.NbtBase
 *  com.comphenix.protocol.wrappers.nbt.NbtCompound
 *  com.comphenix.protocol.wrappers.nbt.NbtFactory
 *  com.comphenix.protocol.wrappers.nbt.NbtType
 *  com.comphenix.protocol.wrappers.nbt.NbtWrapper
 *  com.comphenix.protocol.wrappers.nbt.io.NbtBinarySerializer
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.inventory.ItemStack
 *  org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
 */
package yo;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.utility.StreamSerializer;
import com.comphenix.protocol.wrappers.collection.ConvertedList;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtType;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import com.comphenix.protocol.wrappers.nbt.io.NbtBinarySerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import yo.be_0;

public class bb_1 {
    public static bb_1 a = new bb_1();
    private static Field b;
    private static Field c;
    private static Field d;

    public Map<String, ?> a(Block block) {
        return this.b(block).getValue();
    }

    public Map<String, ?> a(ItemStack item) {
        return this.b(item).getValue();
    }

    public Map<String, ?> a(Entity entity) {
        return this.b(entity).getValue();
    }

    public void a(Block block, String key, Object value) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        NbtCompound nbt = this.b(block);
        nbt = this.a(nbt, key, value);
        NbtFactory.writeBlockState((Block)block, (NbtCompound)nbt);
    }

    public void a(ItemStack item, String key, Object value) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        NbtCompound nbt = this.b(item);
        this.a(nbt, key, value);
        this.a(item, nbt);
    }

    public void a(Entity entity, String key, Object value) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        NbtCompound nbt = this.b(entity);
        this.a(nbt, key, value);
        Object zz = be_0.a((Object)entity);
        try {
            zz.getClass().getMethod("f", MinecraftReflection.getNBTCompoundClass()).invoke(zz, ((NbtWrapper)nbt).getHandle());
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(Entity entity, NbtCompound nbt) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Object zz = be_0.a((Object)entity);
        try {
            zz.getClass().getMethod("f", MinecraftReflection.getNBTCompoundClass()).invoke(zz, ((NbtWrapper)nbt).getHandle());
        }
        catch (Exception e2) {
            try {
                be_0.a(zz.getClass(), new Class[]{MinecraftReflection.getNBTCompoundClass()}).get(3).invoke(zz, ((NbtWrapper)nbt).getHandle());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void a(ItemStack item, NbtCompound nbt) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        try {
            Object stack = b.get((Object)item);
            c.set(stack, ((NbtWrapper)nbt).getHandle());
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public NbtCompound b(Block block) {
        return NbtFactory.readBlockState((Block)block);
    }

    public NbtCompound b(ItemStack item) {
        return (NbtCompound)NbtFactory.fromItemTag((ItemStack)item);
    }

    public NbtCompound b(Entity entity) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Object zz = be_0.a((Object)entity);
        NbtCompound nbt = NbtFactory.ofCompound((String)"tag");
        try {
            zz.getClass().getMethod("e", MinecraftReflection.getNBTCompoundClass()).invoke(zz, ((NbtWrapper)nbt).getHandle());
            return nbt;
        }
        catch (Exception e2) {
            try {
                be_0.a(zz.getClass(), new Class[]{MinecraftReflection.getNBTCompoundClass()}).get(4).invoke(zz, ((NbtWrapper)nbt).getHandle());
                return nbt;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    private <T> NbtCompound a(NbtCompound nbt, String key, T value) {
        nbt.putObject(key, value);
        return nbt;
    }

    public void a(NbtCompound nbt, String key) {
        nbt.remove(key);
    }

    private void c(NbtCompound nbt) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (nbt.getName() == null || nbt.getName().isEmpty()) {
            nbt.setName("tag");
        }
        HashMap map = new HashMap(nbt.getValue());
        for (Map.Entry entry : map.entrySet()) {
            if (((NbtBase)entry.getValue()).getName() == null || ((NbtBase)entry.getValue()).getName().isEmpty()) {
                ((NbtBase)entry.getValue()).setName("tag");
            }
            if (entry.getKey() == null || ((String)entry.getKey()).isEmpty()) {
                nbt.remove((String)entry.getKey());
                nbt.putObject("tag", entry.getValue());
            }
            if (entry.getValue() instanceof NbtCompound) {
                this.c((NbtCompound)entry.getValue());
                continue;
            }
            this.a((NbtBase)entry.getValue());
        }
    }

    private void a(ConvertedList nbt) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        for (int i = 0; i < nbt.size(); ++i) {
            NbtBase nb = (NbtBase)nbt.get(i);
            if (nb.getName() == null || nb.getName().isEmpty()) {
                nb.setName("tag");
            } else {
                System.out.println("notnullName: " + nb.getName());
            }
            this.a(nb);
            if (!nb.getName().equals(((NbtBase)nbt.get(i)).getName())) {
                System.out.println("!equal");
                nbt.set(i, (Object)nb);
            }
            System.out.println("name: " + nb.getName());
            System.out.println("value: " + nb.getValue());
            System.out.println("type: " + (Object)nb.getType());
            System.out.println("class: " + nb.getClass());
            System.out.println("name: " + ((NbtBase)nbt.get(i)).getName());
            System.out.println("value: " + ((NbtBase)nbt.get(i)).getValue());
            System.out.println("type: " + (Object)((NbtBase)nbt.get(i)).getType());
            System.out.println("class: " + ((NbtBase)nbt.get(i)).getClass());
        }
    }

    private void a(NbtBase nbt) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (nbt.getName() == null || nbt.getName().isEmpty()) {
            nbt.setName("tag");
        }
        if (nbt.getValue() instanceof ConvertedList) {
            this.a((ConvertedList)nbt.getValue());
        } else if (nbt.getValue() instanceof NbtCompound) {
            this.c((NbtCompound)nbt.getValue());
        } else if (nbt.getValue() instanceof NbtBase) {
            this.a((NbtBase)nbt.getValue());
        }
    }

    public String a(NbtCompound nbt) throws IOException {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);
        NbtBinarySerializer.DEFAULT.serialize((NbtBase)nbt, (DataOutput)dataOutput);
        return Base64Coder.encodeLines((byte[])outputStream.toByteArray());
    }

    public NbtCompound a(String input) throws IOException {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (input == null) {
            throw new IllegalArgumentException("Input text cannot be NULL.");
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines((String)input));
        return NbtBinarySerializer.DEFAULT.deserializeCompound((DataInput)new DataInputStream(inputStream));
    }

    public String c(ItemStack item) throws IOException {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return StreamSerializer.getDefault().serializeItemStack(item);
    }

    public ItemStack d(ItemStack item) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        item = MinecraftReflection.getBukkitItemStack((Object)this.e(item));
        NbtCompound nbt = this.b(item);
        nbt.remove("display");
        nbt.remove("ench");
        this.a(item, nbt);
        return item;
    }

    public Object e(ItemStack item) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Object nmsStack_Clone = null;
        try {
            nmsStack_Clone = MinecraftReflection.getCraftItemStackClass().getDeclaredMethod("asNMSCopy", ItemStack.class).invoke(null, new Object[]{item});
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        return nmsStack_Clone;
    }

    public ItemStack b(String input) throws IOException {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return StreamSerializer.getDefault().deserializeItemStack(input);
    }

    public NbtCompound a(NbtCompound origin, NbtCompound add) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        Iterator it = add.iterator();
        while (it.hasNext()) {
            origin.put((NbtBase)it.next());
        }
        return origin;
    }

    public String b(NbtCompound nbt) throws IOException {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        nbt.remove("display");
        nbt.remove("ench");
        return this.a(nbt);
    }

    public String c(String nbtString) throws IOException {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (nbtString == null || nbtString.isEmpty()) {
            return "";
        }
        NbtCompound nbt = this.a(nbtString);
        nbt.remove("display");
        nbt.remove("ench");
        return this.a(nbt);
    }

    static {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        try {
            Class CIS = MinecraftReflection.getCraftItemStackClass();
            b = CIS.getDeclaredField("handle");
            b.setAccessible(true);
            Class IS = MinecraftReflection.getItemStackClass();
            Class NBTTC = MinecraftReflection.getNBTCompoundClass();
            try {
                c = IS.getDeclaredField("tag");
            }
            catch (Exception e2) {
                c = be_0.b(IS, NBTTC).get(0);
            }
            c.setAccessible(true);
            try {
                d = NBTTC.getDeclaredField("map");
            }
            catch (Exception e3) {
                d = be_0.b(NBTTC, Map.class).get(0);
            }
            d.setAccessible(true);
        }
        catch (NoSuchFieldException e4) {
            e4.printStackTrace();
        }
    }
}


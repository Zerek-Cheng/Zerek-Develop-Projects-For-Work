// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import com.comphenix.protocol.utility.StreamSerializer;
import java.io.DataInput;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import java.io.DataOutput;
import com.comphenix.protocol.wrappers.nbt.io.NbtBinarySerializer;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import com.comphenix.protocol.wrappers.collection.ConvertedList;
import java.util.Iterator;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import java.util.HashMap;
import java.lang.reflect.Method;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import java.io.Closeable;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import org.bukkit.block.Block;
import java.lang.reflect.Field;

public class bb
{
    public static bb a;
    private static Field b;
    private static Field c;
    private static Field d;
    
    public Map<String, ?> a(final Block block) {
        return (Map<String, ?>)this.b(block).getValue();
    }
    
    public Map<String, ?> a(final ItemStack item) {
        return (Map<String, ?>)this.b(item).getValue();
    }
    
    public Map<String, ?> a(final Entity entity) {
        return (Map<String, ?>)this.b(entity).getValue();
    }
    
    public void a(final Block block, final String key, final Object value) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        NbtCompound nbt = this.b(block);
        nbt = this.a(nbt, key, value);
        NbtFactory.writeBlockState(block, nbt);
    }
    
    public void a(final ItemStack item, final String key, final Object value) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final NbtCompound nbt = this.b(item);
        this.a(nbt, key, value);
        this.a(item, nbt);
    }
    
    public void a(final Entity entity, final String key, final Object value) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final NbtCompound nbt = this.b(entity);
        this.a(nbt, key, value);
        final Object zz = be.a(entity);
        try {
            zz.getClass().getMethod("f", MinecraftReflection.getNBTCompoundClass()).invoke(zz, ((NbtWrapper)nbt).getHandle());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void a(final Entity entity, final NbtCompound nbt) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex2) {}
        final Object zz = be.a(entity);
        try {
            zz.getClass().getMethod("f", MinecraftReflection.getNBTCompoundClass()).invoke(zz, ((NbtWrapper)nbt).getHandle());
        }
        catch (Exception e) {
            try {
                be.a((Class)zz.getClass(), new Class[] { MinecraftReflection.getNBTCompoundClass() }).get(3).invoke(zz, ((NbtWrapper)nbt).getHandle());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void a(final ItemStack item, final NbtCompound nbt) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        try {
            final Object stack = bb.b.get(item);
            bb.c.set(stack, ((NbtWrapper)nbt).getHandle());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public NbtCompound b(final Block block) {
        return NbtFactory.readBlockState(block);
    }
    
    public NbtCompound b(final ItemStack item) {
        return (NbtCompound)NbtFactory.fromItemTag(item);
    }
    
    public NbtCompound b(final Entity entity) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex2) {}
        final Object zz = be.a(entity);
        final NbtCompound nbt = NbtFactory.ofCompound("tag");
        try {
            zz.getClass().getMethod("e", MinecraftReflection.getNBTCompoundClass()).invoke(zz, ((NbtWrapper)nbt).getHandle());
            return nbt;
        }
        catch (Exception e) {
            try {
                be.a((Class)zz.getClass(), new Class[] { MinecraftReflection.getNBTCompoundClass() }).get(4).invoke(zz, ((NbtWrapper)nbt).getHandle());
                return nbt;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
    
    private <T> NbtCompound a(final NbtCompound nbt, final String key, final T value) {
        nbt.putObject(key, (Object)value);
        return nbt;
    }
    
    public void a(final NbtCompound nbt, final String key) {
        nbt.remove(key);
    }
    
    private void c(final NbtCompound nbt) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (nbt.getName() == null || nbt.getName().isEmpty()) {
            nbt.setName("tag");
        }
        final Map<String, NbtBase<?>> map = new HashMap<String, NbtBase<?>>(nbt.getValue());
        for (final Map.Entry<String, NbtBase<?>> entry : map.entrySet()) {
            if (entry.getValue().getName() == null || entry.getValue().getName().isEmpty()) {
                entry.getValue().setName("tag");
            }
            if (entry.getKey() == null || entry.getKey().isEmpty()) {
                nbt.remove((String)entry.getKey());
                nbt.putObject("tag", (Object)entry.getValue());
            }
            if (entry.getValue() instanceof NbtCompound) {
                this.c((NbtCompound)entry.getValue());
            }
            else {
                this.a(entry.getValue());
            }
        }
    }
    
    private void a(final ConvertedList nbt) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        for (int i = 0; i < nbt.size(); ++i) {
            final NbtBase<?> nb = (NbtBase<?>)nbt.get(i);
            if (nb.getName() == null || nb.getName().isEmpty()) {
                nb.setName("tag");
            }
            else {
                System.out.println("notnullName: " + nb.getName());
            }
            this.a(nb);
            if (!nb.getName().equals(((NbtBase)nbt.get(i)).getName())) {
                System.out.println("!equal");
                nbt.set(i, (Object)nb);
            }
            System.out.println("name: " + nb.getName());
            System.out.println("value: " + nb.getValue());
            System.out.println("type: " + nb.getType());
            System.out.println("class: " + nb.getClass());
            System.out.println("name: " + ((NbtBase)nbt.get(i)).getName());
            System.out.println("value: " + ((NbtBase)nbt.get(i)).getValue());
            System.out.println("type: " + ((NbtBase)nbt.get(i)).getType());
            System.out.println("class: " + ((NbtBase)nbt.get(i)).getClass());
        }
    }
    
    private void a(final NbtBase nbt) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (nbt.getName() == null || nbt.getName().isEmpty()) {
            nbt.setName("tag");
        }
        if (nbt.getValue() instanceof ConvertedList) {
            this.a((ConvertedList)nbt.getValue());
        }
        else if (nbt.getValue() instanceof NbtCompound) {
            this.c((NbtCompound)nbt.getValue());
        }
        else if (nbt.getValue() instanceof NbtBase) {
            this.a((NbtBase)nbt.getValue());
        }
    }
    
    public String a(final NbtCompound nbt) throws IOException {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutput = new DataOutputStream(outputStream);
        NbtBinarySerializer.DEFAULT.serialize((NbtBase)nbt, (DataOutput)dataOutput);
        return Base64Coder.encodeLines(outputStream.toByteArray());
    }
    
    public NbtCompound a(final String input) throws IOException {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (input == null) {
            throw new IllegalArgumentException("Input text cannot be NULL.");
        }
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(input));
        return NbtBinarySerializer.DEFAULT.deserializeCompound((DataInput)new DataInputStream(inputStream));
    }
    
    public String c(final ItemStack item) throws IOException {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return StreamSerializer.getDefault().serializeItemStack(item);
    }
    
    public ItemStack d(ItemStack item) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        item = MinecraftReflection.getBukkitItemStack(this.e(item));
        final NbtCompound nbt = this.b(item);
        nbt.remove("display");
        nbt.remove("ench");
        this.a(item, nbt);
        return item;
    }
    
    public Object e(final ItemStack item) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        Object nmsStack_Clone = null;
        try {
            nmsStack_Clone = MinecraftReflection.getCraftItemStackClass().getDeclaredMethod("asNMSCopy", ItemStack.class).invoke(null, item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return nmsStack_Clone;
    }
    
    public ItemStack b(final String input) throws IOException {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return StreamSerializer.getDefault().deserializeItemStack(input);
    }
    
    public NbtCompound a(final NbtCompound origin, final NbtCompound add) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final Iterator<NbtBase<?>> it = (Iterator<NbtBase<?>>)add.iterator();
        while (it.hasNext()) {
            origin.put((NbtBase)it.next());
        }
        return origin;
    }
    
    public String b(final NbtCompound nbt) throws IOException {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        nbt.remove("display");
        nbt.remove("ench");
        return this.a(nbt);
    }
    
    public String c(final String nbtString) throws IOException {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (nbtString == null || nbtString.isEmpty()) {
            return "";
        }
        final NbtCompound nbt = this.a(nbtString);
        nbt.remove("display");
        nbt.remove("ench");
        return this.a(nbt);
    }
    
    static {
        bb.a = new bb();
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        try {
            final Class CIS = MinecraftReflection.getCraftItemStackClass();
            (bb.b = CIS.getDeclaredField("handle")).setAccessible(true);
            final Class IS = MinecraftReflection.getItemStackClass();
            final Class NBTTC = MinecraftReflection.getNBTCompoundClass();
            try {
                bb.c = IS.getDeclaredField("tag");
            }
            catch (Exception e2) {
                bb.c = be.b(IS, NBTTC).get(0);
            }
            bb.c.setAccessible(true);
            try {
                bb.d = NBTTC.getDeclaredField("map");
            }
            catch (Exception e2) {
                bb.d = be.b(NBTTC, (Class)Map.class).get(0);
            }
            bb.d.setAccessible(true);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

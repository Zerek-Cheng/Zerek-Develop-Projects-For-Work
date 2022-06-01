/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.nbt;

import java.lang.reflect.Constructor;
import su.nightexpress.divineitems.nbt.ClassWrapper;

public enum ObjectCreator {
    NMS_NBTTAGCOMPOUND(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), new Class[0]),
    NMS_BLOCKPOSITION(ClassWrapper.NMS_BLOCKPOSITION.getClazz(), new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE});
    
    private Constructor<?> construct;

    private ObjectCreator(Class<?> class_, Class<?>[] arrclass) {
        try {
            this.construct = class_.getConstructor(arrclass);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public /* varargs */ Object getInstance(Object ... arrobject) {
        try {
            return this.construct.newInstance(arrobject);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}


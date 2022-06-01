/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver.wrapper;

import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.WrapperAbstract;

public class ClassWrapper<R>
extends WrapperAbstract {
    private final Class<R> clazz;

    public ClassWrapper(Class<R> class_) {
        this.clazz = class_;
    }

    @Override
    public boolean exists() {
        if (this.clazz != null) {
            return true;
        }
        return false;
    }

    public Class<R> getClazz() {
        return this.clazz;
    }

    public String getName() {
        return this.clazz.getName();
    }

    public R newInstance() {
        try {
            return this.clazz.newInstance();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public R newInstanceSilent() {
        try {
            return this.clazz.newInstance();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        ClassWrapper classWrapper = (ClassWrapper)object;
        return this.clazz != null ? this.clazz.equals(classWrapper.clazz) : classWrapper.clazz == null;
    }

    public int hashCode() {
        return this.clazz != null ? this.clazz.hashCode() : 0;
    }
}


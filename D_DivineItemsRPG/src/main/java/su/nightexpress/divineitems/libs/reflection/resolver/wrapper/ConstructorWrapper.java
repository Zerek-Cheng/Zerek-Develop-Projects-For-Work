/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver.wrapper;

import java.lang.reflect.Constructor;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.WrapperAbstract;

public class ConstructorWrapper<R>
extends WrapperAbstract {
    private final Constructor<R> constructor;

    public ConstructorWrapper(Constructor<R> constructor) {
        this.constructor = constructor;
    }

    @Override
    public boolean exists() {
        if (this.constructor != null) {
            return true;
        }
        return false;
    }

    public /* varargs */ R newInstance(Object ... arrobject) {
        try {
            return this.constructor.newInstance(arrobject);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public /* varargs */ R newInstanceSilent(Object ... arrobject) {
        try {
            return this.constructor.newInstance(arrobject);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Class<?>[] getParameterTypes() {
        return this.constructor.getParameterTypes();
    }

    public Constructor<R> getConstructor() {
        return this.constructor;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        ConstructorWrapper constructorWrapper = (ConstructorWrapper)object;
        return this.constructor != null ? this.constructor.equals(constructorWrapper.constructor) : constructorWrapper.constructor == null;
    }

    public int hashCode() {
        return this.constructor != null ? this.constructor.hashCode() : 0;
    }
}


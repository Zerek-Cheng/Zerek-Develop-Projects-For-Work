/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver.wrapper;

import java.lang.reflect.Field;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.WrapperAbstract;

public class FieldWrapper<R>
extends WrapperAbstract {
    private final Field field;

    public FieldWrapper(Field field) {
        this.field = field;
    }

    @Override
    public boolean exists() {
        if (this.field != null) {
            return true;
        }
        return false;
    }

    public String getName() {
        return this.field.getName();
    }

    public R get(Object object) {
        try {
            return (R)this.field.get(object);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public R getSilent(Object object) {
        try {
            return (R)this.field.get(object);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public void set(Object object, R r) {
        try {
            this.field.set(object, r);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setSilent(Object object, R r) {
        try {
            this.field.set(object, r);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public Field getField() {
        return this.field;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        FieldWrapper fieldWrapper = (FieldWrapper)object;
        if (this.field != null ? !this.field.equals(fieldWrapper.field) : fieldWrapper.field != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.field != null ? this.field.hashCode() : 0;
    }
}


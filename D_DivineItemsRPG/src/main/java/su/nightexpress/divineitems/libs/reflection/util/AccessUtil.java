/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AccessUtil {
    public static Field setAccessible(Field field) {
        field.setAccessible(true);
        Field field2 = Field.class.getDeclaredField("modifiers");
        field2.setAccessible(true);
        field2.setInt(field, field.getModifiers() & -17);
        return field;
    }

    public static Method setAccessible(Method method) {
        method.setAccessible(true);
        return method;
    }

    public static Constructor<?> setAccessible(Constructor<?> constructor) {
        constructor.setAccessible(true);
        return constructor;
    }
}


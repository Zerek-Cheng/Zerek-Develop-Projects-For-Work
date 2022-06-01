/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 */
package su.nightexpress.divineitems.libs.reflection.minecraft;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import su.nightexpress.divineitems.libs.reflection.resolver.ConstructorResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.FieldResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.MethodResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.minecraft.NMSClassResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.minecraft.OBCClassResolver;
import su.nightexpress.divineitems.libs.reflection.util.AccessUtil;
import sun.reflect.ConstructorAccessor;

public class Minecraft {
    static final Pattern NUMERIC_VERSION_PATTERN = Pattern.compile("v([0-9])_([0-9]*)_R([0-9])");
    public static final Version VERSION;
    private static NMSClassResolver nmsClassResolver;
    private static OBCClassResolver obcClassResolver;
    private static Class<?> NmsEntity;
    private static Class<?> CraftEntity;

    static {
        nmsClassResolver = new NMSClassResolver();
        obcClassResolver = new OBCClassResolver();
        VERSION = Version.getVersion();
        System.out.println("[ReflectionHelper] Version is " + (Object)((Object)VERSION));
        try {
            NmsEntity = nmsClassResolver.resolve("Entity");
            CraftEntity = obcClassResolver.resolve("entity.CraftEntity");
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public static String getVersion() {
        return String.valueOf(VERSION.name()) + ".";
    }

    public static Object getHandle(Object object) {
        Method method;
        try {
            method = AccessUtil.setAccessible(object.getClass().getDeclaredMethod("getHandle", new Class[0]));
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            method = AccessUtil.setAccessible(CraftEntity.getDeclaredMethod("getHandle", new Class[0]));
        }
        return method.invoke(object, new Object[0]);
    }

    public static Entity getBukkitEntity(Object object) {
        Method method;
        try {
            method = AccessUtil.setAccessible(NmsEntity.getDeclaredMethod("getBukkitEntity", new Class[0]));
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            method = AccessUtil.setAccessible(CraftEntity.getDeclaredMethod("getHandle", new Class[0]));
        }
        return (Entity)method.invoke(object, new Object[0]);
    }

    public static Object getHandleSilent(Object object) {
        try {
            return Minecraft.getHandle(object);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Object newEnumInstance(Class class_, Class[] arrclass, Object[] arrobject) {
        Constructor constructor = new ConstructorResolver(class_).resolve(new Class[][]{arrclass});
        Field field = new FieldResolver(Constructor.class).resolve("constructorAccessor");
        ConstructorAccessor constructorAccessor = (ConstructorAccessor)field.get(constructor);
        if (constructorAccessor == null) {
            new MethodResolver(Constructor.class).resolve("acquireConstructorAccessor").invoke(constructor, new Object[0]);
            constructorAccessor = (ConstructorAccessor)field.get(constructor);
        }
        return constructorAccessor.newInstance(arrobject);
    }

}


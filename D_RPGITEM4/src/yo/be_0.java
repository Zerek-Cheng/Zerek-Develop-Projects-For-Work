/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package yo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.bukkit.plugin.Plugin;

public class be_0 {
    public static Object a(Object bukkitObj) {
        try {
            return bukkitObj.getClass().getMethod("getHandle", new Class[0]).invoke(bukkitObj, new Object[0]);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Field a(Class source, String name) {
        try {
            Field field = source.getField(name);
            field.setAccessible(true);
            return field;
        }
        catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Field b(Class source, String name) {
        try {
            Field field = source.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        }
        catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static List<Field> a(Class source, Class type) {
        ArrayList<Field> list = new ArrayList<Field>();
        for (Field field : source.getFields()) {
            if (field.getType() != type) continue;
            field.setAccessible(true);
            list.add(field);
        }
        return list;
    }

    public static List<Field> b(Class source, Class type) {
        ArrayList<Field> list = new ArrayList<Field>();
        for (Field field : source.getDeclaredFields()) {
            if (field.getType() != type) continue;
            field.setAccessible(true);
            list.add(field);
        }
        return list;
    }

    public static /* varargs */ Method a(Class source, String name, Class ... args) {
        for (Method method : be_0.a(source.getMethods(), args)) {
            if (!method.getName().equals(name)) continue;
            return method;
        }
        return null;
    }

    public static /* varargs */ Method b(Class source, String name, Class ... args) {
        for (Method method : be_0.a(source.getDeclaredMethods(), args)) {
            if (!method.getName().equals(name)) continue;
            return method;
        }
        return null;
    }

    public static /* varargs */ List<Method> a(Class source, Class ... args) {
        return be_0.a(source.getMethods(), args);
    }

    public static /* varargs */ List<Method> b(Class source, Class ... args) {
        return be_0.a(source.getDeclaredMethods(), args);
    }

    public static /* varargs */ List<Method> a(Class source, Class returnType, Class ... args) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : be_0.a(source.getMethods(), args)) {
            if (!method.getReturnType().equals((Object)returnType)) continue;
            methods.add(method);
        }
        return methods;
    }

    public static /* varargs */ List<Method> b(Class source, Class returnType, Class ... args) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : be_0.a(source.getDeclaredMethods(), args)) {
            if (!method.getReturnType().equals((Object)returnType)) continue;
            methods.add(method);
        }
        return methods;
    }

    public static List<Method> a(Class source, String name, Class returnType) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : source.getMethods()) {
            if (!method.getName().equals(name) || !method.getReturnType().equals((Object)returnType)) continue;
            methods.add(method);
        }
        return methods;
    }

    public static List<Method> b(Class source, String name, Class returnType) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : source.getDeclaredMethods()) {
            if (!method.getName().equals(name) || !method.getReturnType().equals((Object)returnType)) continue;
            methods.add(method);
        }
        return methods;
    }

    public static List<Method> c(Class source, Class returnType) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : source.getMethods()) {
            if (!method.getReturnType().equals((Object)returnType)) continue;
            methods.add(method);
        }
        return methods;
    }

    public static List<Method> d(Class source, Class returnType) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : source.getDeclaredMethods()) {
            if (!method.getReturnType().equals((Object)returnType)) continue;
            methods.add(method);
        }
        return methods;
    }

    private static /* varargs */ List<Method> a(Method[] methods, Class ... args) {
        ArrayList<Method> list = new ArrayList<Method>();
        block0 : for (Method method : methods) {
            if (method.getParameterTypes().length != args.length) continue;
            Class<?>[] array = method.getParameterTypes();
            for (int i = 0; i < args.length; ++i) {
                if (array[i] != args[i]) continue block0;
            }
            method.setAccessible(true);
            list.add(method);
        }
        return list;
    }

    public static /* varargs */ Method c(Class clzz, String methodName, Class ... args) {
        try {
            return clzz.getDeclaredMethod(methodName, args);
        }
        catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void a(Object object, String methodName, Class arg, Object value) {
        try {
            Method m = object.getClass().getDeclaredMethod(methodName, arg);
            m.invoke(object, value);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(Object object, String methodName, Object value) {
        try {
            Method m = object.getClass().getDeclaredMethod(methodName, value.getClass());
            m.invoke(object, value);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void a(Object object, String methodName, Class[] args, Object[] value) {
        try {
            Method m = object.getClass().getDeclaredMethod(methodName, args);
            m.invoke(object, value);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static Set<Class<?>> a(Plugin plugin) {
        return be_0.a(plugin.getDataFolder().getParentFile().getAbsolutePath() + File.separator + plugin.getName() + ".jar");
    }

    public static Set<Class<?>> a(String jarPath) {
        LinkedHashSet classes = new LinkedHashSet();
        HashMap classAnnotationMap = new HashMap();
        HashMap classMethodAnnoMap = new HashMap();
        try {
            JarFile jarFile = new JarFile(new File(jarPath));
            URL url = new URL("file:" + jarPath);
            URLClassLoader loader = new URLClassLoader(new URL[]{url});
            Enumeration<JarEntry> es = jarFile.entries();
            while (es.hasMoreElements()) {
                JarEntry jarEntry = es.nextElement();
                String name = jarEntry.getName();
                if (name == null || !name.endsWith(".class")) continue;
                Class<?> c2 = loader.loadClass(name.replace("/", ".").substring(0, name.length() - 6));
                classes.add(c2);
            }
        }
        catch (ClassNotFoundException jarFile) {
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return classes;
    }
}


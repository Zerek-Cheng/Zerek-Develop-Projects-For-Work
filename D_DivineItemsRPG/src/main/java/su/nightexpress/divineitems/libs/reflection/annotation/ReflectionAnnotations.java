/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import su.nightexpress.divineitems.libs.reflection.annotation.Class;
import su.nightexpress.divineitems.libs.reflection.annotation.Field;
import su.nightexpress.divineitems.libs.reflection.annotation.Method;
import su.nightexpress.divineitems.libs.reflection.minecraft.Minecraft;
import su.nightexpress.divineitems.libs.reflection.resolver.ClassResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.FieldResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.MethodResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.ClassWrapper;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.FieldWrapper;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.MethodWrapper;

public class ReflectionAnnotations {
    public static final ReflectionAnnotations INSTANCE = new ReflectionAnnotations();
    static final Pattern classRefPattern = Pattern.compile("@Class\\((.*)\\)");

    private ReflectionAnnotations() {
    }

    public void load(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("toLoad cannot be null");
        }
        ClassResolver classResolver = new ClassResolver();
        java.lang.reflect.Field[] arrfield = object.getClass().getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            block30 : {
                java.lang.reflect.Field field = arrfield[n2];
                Class class_ = field.getAnnotation(Class.class);
                Field field2 = field.getAnnotation(Field.class);
                Method method = field.getAnnotation(Method.class);
                if (class_ != null || field2 != null || method != null) {
                    String[] arrstring;
                    List<String> list;
                    field.setAccessible(true);
                    if (class_ != null) {
                        void reflectiveOperationException;
                        list = this.parseAnnotationVersions(Class.class, class_);
                        if (list.isEmpty()) {
                            throw new IllegalArgumentException("@Class names cannot be empty");
                        }
                        arrstring = list.toArray(new String[list.size()]);
                        boolean bl = false;
                        while (++reflectiveOperationException < arrstring.length) {
                            arrstring[reflectiveOperationException] = arrstring[reflectiveOperationException].replace("{nms}", "net.minecraft.server." + Minecraft.VERSION.name()).replace("{obc}", "org.bukkit.craftbukkit." + Minecraft.VERSION.name());
                        }
                        try {
                            if (ClassWrapper.class.isAssignableFrom(field.getType())) {
                                field.set(object, classResolver.resolveWrapper(arrstring));
                                break block30;
                            }
                            if (java.lang.Class.class.isAssignableFrom(field.getType())) {
                                field.set(object, classResolver.resolve(arrstring));
                                break block30;
                            }
                            this.throwInvalidFieldType(field, object, "Class or ClassWrapper");
                            return;
                        }
                        catch (ReflectiveOperationException reflectiveOperationException2) {
                            if (!class_.ignoreExceptions()) {
                                this.throwReflectionException("@Class", field, object, reflectiveOperationException2);
                                return;
                            }
                            break block30;
                        }
                    }
                    if (field2 != null) {
                        list = this.parseAnnotationVersions(Field.class, field2);
                        if (list.isEmpty()) {
                            throw new IllegalArgumentException("@Field names cannot be empty");
                        }
                        arrstring = list.toArray(new String[list.size()]);
                        try {
                            FieldResolver bl = new FieldResolver(this.parseClass(Field.class, field2, object));
                            if (FieldWrapper.class.isAssignableFrom(field.getType())) {
                                field.set(object, bl.resolveWrapper(arrstring));
                                break block30;
                            }
                            if (java.lang.reflect.Field.class.isAssignableFrom(field.getType())) {
                                field.set(object, bl.resolve(arrstring));
                                break block30;
                            }
                            this.throwInvalidFieldType(field, object, "Field or FieldWrapper");
                            return;
                        }
                        catch (ReflectiveOperationException reflectiveOperationException) {
                            if (!field2.ignoreExceptions()) {
                                this.throwReflectionException("@Field", field, object, reflectiveOperationException);
                                return;
                            }
                            break block30;
                        }
                    }
                    if (method != null) {
                        Object object2;
                        list = this.parseAnnotationVersions(Method.class, method);
                        if (list.isEmpty()) {
                            throw new IllegalArgumentException("@Method names cannot be empty");
                        }
                        arrstring = list.toArray(new String[list.size()]);
                        boolean bl = arrstring[0].contains(" ");
                        String[] arrstring2 = arrstring;
                        int n3 = arrstring2.length;
                        int n4 = 0;
                        while (n4 < n3) {
                            object2 = arrstring2[n4];
                            if (object2.contains(" ") != bl) {
                                throw new IllegalArgumentException("Inconsistent method names: Cannot have mixed signatures/names");
                            }
                            ++n4;
                        }
                        try {
                            object2 = new MethodResolver(this.parseClass(Method.class, method, object));
                            if (MethodWrapper.class.isAssignableFrom(field.getType())) {
                                if (bl) {
                                    field.set(object, object2.resolveSignatureWrapper(arrstring));
                                } else {
                                    field.set(object, object2.resolveWrapper(arrstring));
                                }
                                break block30;
                            }
                            if (java.lang.reflect.Method.class.isAssignableFrom(field.getType())) {
                                if (bl) {
                                    field.set(object, object2.resolveSignature(arrstring));
                                } else {
                                    field.set(object, object2.resolve(arrstring));
                                }
                                break block30;
                            }
                            this.throwInvalidFieldType(field, object, "Method or MethodWrapper");
                            return;
                        }
                        catch (ReflectiveOperationException reflectiveOperationException) {
                            if (method.ignoreExceptions()) break block30;
                            this.throwReflectionException("@Method", field, object, reflectiveOperationException);
                            return;
                        }
                    }
                }
            }
            ++n2;
        }
    }

    <A extends Annotation> List<String> parseAnnotationVersions(java.lang.Class<A> class_, A a) {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            String[] arrstring = (String[])class_.getMethod("value", new java.lang.Class[0]).invoke(a, new Object[0]);
            Minecraft.Version[] arrversion = (Minecraft.Version[])class_.getMethod("versions", new java.lang.Class[0]).invoke(a, new Object[0]);
            if (arrversion.length == 0) {
                String[] arrstring2 = arrstring;
                int n = arrstring2.length;
                int n2 = 0;
                while (n2 < n) {
                    String string = arrstring2[n2];
                    arrayList.add(string);
                    ++n2;
                }
            } else {
                if (arrversion.length > arrstring.length) {
                    throw new RuntimeException("versions array cannot have more elements than the names (" + class_ + ")");
                }
                int n = 0;
                while (n < arrversion.length) {
                    if (Minecraft.VERSION == arrversion[n]) {
                        arrayList.add(arrstring[n]);
                    } else if (arrstring[n].startsWith(">") && Minecraft.VERSION.newerThan(arrversion[n])) {
                        arrayList.add(arrstring[n].substring(1));
                    } else if (arrstring[n].startsWith("<") && Minecraft.VERSION.olderThan(arrversion[n])) {
                        arrayList.add(arrstring[n].substring(1));
                    }
                    ++n;
                }
            }
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
        return arrayList;
    }

    <A extends Annotation> String parseClass(java.lang.Class<A> class_, A a, Object object) {
        try {
            String string = (String)class_.getMethod("className", new java.lang.Class[0]).invoke(a, new Object[0]);
            Matcher matcher = classRefPattern.matcher(string);
            while (matcher.find()) {
                if (matcher.groupCount() != 1) continue;
                String string2 = matcher.group(1);
                java.lang.reflect.Field field = object.getClass().getField(string2);
                if (ClassWrapper.class.isAssignableFrom(field.getType())) {
                    return ((ClassWrapper)field.get(object)).getName();
                }
                if (!java.lang.Class.class.isAssignableFrom(field.getType())) continue;
                return ((java.lang.Class)field.get(object)).getName();
            }
            return string;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    void throwInvalidFieldType(java.lang.reflect.Field field, Object object, String string) {
        throw new IllegalArgumentException("Field " + field.getName() + " in " + object.getClass() + " is not of type " + string + ", it's " + field.getType());
    }

    void throwReflectionException(String string, java.lang.reflect.Field field, Object object, ReflectiveOperationException reflectiveOperationException) {
        throw new RuntimeException("Failed to set " + string + " field " + field.getName() + " in " + object.getClass(), reflectiveOperationException);
    }
}


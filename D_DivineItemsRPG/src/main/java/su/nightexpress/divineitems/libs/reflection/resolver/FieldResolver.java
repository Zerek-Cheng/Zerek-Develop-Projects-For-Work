/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import su.nightexpress.divineitems.libs.reflection.resolver.MemberResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.ResolverQuery;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.FieldWrapper;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.WrapperAbstract;
import su.nightexpress.divineitems.libs.reflection.util.AccessUtil;

public class FieldResolver
extends MemberResolver<Field> {
    public FieldResolver(Class<?> class_) {
        super(class_);
    }

    public FieldResolver(String string) {
        super(string);
    }

    @Override
    public Field resolveIndex(int n) {
        return AccessUtil.setAccessible(this.clazz.getDeclaredFields()[n]);
    }

    @Override
    public Field resolveIndexSilent(int n) {
        try {
            return this.resolveIndex(n);
        }
        catch (IndexOutOfBoundsException | ReflectiveOperationException exception) {
            return null;
        }
    }

    @Override
    public FieldWrapper resolveIndexWrapper(int n) {
        return new FieldWrapper(this.resolveIndexSilent(n));
    }

    public /* varargs */ FieldWrapper resolveWrapper(String ... arrstring) {
        return new FieldWrapper(this.resolveSilent(arrstring));
    }

    public /* varargs */ Field resolveSilent(String ... arrstring) {
        try {
            return this.resolve(arrstring);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public /* varargs */ Field resolve(String ... arrstring) {
        ResolverQuery.Builder builder = ResolverQuery.builder();
        String[] arrstring2 = arrstring;
        int n = arrstring2.length;
        int n2 = 0;
        while (n2 < n) {
            String string = arrstring2[n2];
            builder.with(string);
            ++n2;
        }
        try {
            return (Field)super.resolve(builder.build());
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw (NoSuchFieldException)reflectiveOperationException;
        }
    }

    @Override
    public /* varargs */ Field resolveSilent(ResolverQuery ... arrresolverQuery) {
        try {
            return this.resolve(arrresolverQuery);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public /* varargs */ Field resolve(ResolverQuery ... arrresolverQuery) {
        try {
            return (Field)super.resolve(arrresolverQuery);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw (NoSuchFieldException)reflectiveOperationException;
        }
    }

    @Override
    protected Field resolveObject(ResolverQuery resolverQuery) {
        if (resolverQuery.getTypes() == null || resolverQuery.getTypes().length == 0) {
            return AccessUtil.setAccessible(this.clazz.getDeclaredField(resolverQuery.getName()));
        }
        Field[] arrfield = this.clazz.getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            Field field = arrfield[n2];
            if (field.getName().equals(resolverQuery.getName())) {
                Class<?>[] arrclass = resolverQuery.getTypes();
                int n3 = arrclass.length;
                int n4 = 0;
                while (n4 < n3) {
                    Class<?> class_ = arrclass[n4];
                    if (field.getType().equals(class_)) {
                        return field;
                    }
                    ++n4;
                }
            }
            ++n2;
        }
        return null;
    }

    public Field resolveByFirstType(Class<?> class_) {
        Field[] arrfield = this.clazz.getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            Field field = arrfield[n2];
            if (field.getType().equals(class_)) {
                return AccessUtil.setAccessible(field);
            }
            ++n2;
        }
        throw new NoSuchFieldException("Could not resolve field of type '" + class_.toString() + "' in class " + this.clazz);
    }

    public Field resolveByFirstTypeSilent(Class<?> class_) {
        try {
            return this.resolveByFirstType(class_);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Field resolveByLastType(Class<?> class_) {
        Field field = null;
        Field[] arrfield = this.clazz.getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            Field field2 = arrfield[n2];
            if (field2.getType().equals(class_)) {
                field = field2;
            }
            ++n2;
        }
        if (field == null) {
            throw new NoSuchFieldException("Could not resolve field of type '" + class_.toString() + "' in class " + this.clazz);
        }
        return AccessUtil.setAccessible(field);
    }

    public Field resolveByLastTypeSilent(Class<?> class_) {
        try {
            return this.resolveByLastType(class_);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    protected NoSuchFieldException notFoundException(String string) {
        return new NoSuchFieldException("Could not resolve field for " + string + " in class " + this.clazz);
    }
}


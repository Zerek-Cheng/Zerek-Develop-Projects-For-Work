/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import su.nightexpress.divineitems.libs.reflection.resolver.MemberResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.ResolverQuery;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.MethodWrapper;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.WrapperAbstract;
import su.nightexpress.divineitems.libs.reflection.util.AccessUtil;

public class MethodResolver
extends MemberResolver<Method> {
    public MethodResolver(Class<?> class_) {
        super(class_);
    }

    public MethodResolver(String string) {
        super(string);
    }

    public /* varargs */ Method resolveSignature(String ... arrstring) {
        Method[] arrmethod = this.clazz.getDeclaredMethods();
        int n = arrmethod.length;
        int n2 = 0;
        while (n2 < n) {
            Method method = arrmethod[n2];
            String string = MethodWrapper.getMethodSignature(method);
            String[] arrstring2 = arrstring;
            int n3 = arrstring2.length;
            int n4 = 0;
            while (n4 < n3) {
                String string2 = arrstring2[n4];
                if (string2.equals(string)) {
                    return AccessUtil.setAccessible(method);
                }
                ++n4;
            }
            ++n2;
        }
        return null;
    }

    public /* varargs */ Method resolveSignatureSilent(String ... arrstring) {
        try {
            return this.resolveSignature(arrstring);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    public /* varargs */ MethodWrapper resolveSignatureWrapper(String ... arrstring) {
        return new MethodWrapper(this.resolveSignatureSilent(arrstring));
    }

    @Override
    public Method resolveIndex(int n) {
        return AccessUtil.setAccessible(this.clazz.getDeclaredMethods()[n]);
    }

    @Override
    public Method resolveIndexSilent(int n) {
        try {
            return this.resolveIndex(n);
        }
        catch (IndexOutOfBoundsException | ReflectiveOperationException exception) {
            return null;
        }
    }

    @Override
    public MethodWrapper resolveIndexWrapper(int n) {
        return new MethodWrapper(this.resolveIndexSilent(n));
    }

    public /* varargs */ MethodWrapper resolveWrapper(String ... arrstring) {
        return new MethodWrapper(this.resolveSilent(arrstring));
    }

    public /* varargs */ MethodWrapper resolveWrapper(ResolverQuery ... arrresolverQuery) {
        return new MethodWrapper(this.resolveSilent(arrresolverQuery));
    }

    public /* varargs */ Method resolveSilent(String ... arrstring) {
        try {
            return this.resolve(arrstring);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public /* varargs */ Method resolveSilent(ResolverQuery ... arrresolverQuery) {
        return (Method)super.resolveSilent(arrresolverQuery);
    }

    public /* varargs */ Method resolve(String ... arrstring) {
        ResolverQuery.Builder builder = ResolverQuery.builder();
        String[] arrstring2 = arrstring;
        int n = arrstring2.length;
        int n2 = 0;
        while (n2 < n) {
            String string = arrstring2[n2];
            builder.with(string);
            ++n2;
        }
        return this.resolve(builder.build());
    }

    @Override
    public /* varargs */ Method resolve(ResolverQuery ... arrresolverQuery) {
        try {
            return (Method)super.resolve(arrresolverQuery);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw (NoSuchMethodException)reflectiveOperationException;
        }
    }

    @Override
    protected Method resolveObject(ResolverQuery resolverQuery) {
        Method[] arrmethod = this.clazz.getDeclaredMethods();
        int n = arrmethod.length;
        int n2 = 0;
        while (n2 < n) {
            Method method = arrmethod[n2];
            if (method.getName().equals(resolverQuery.getName()) && (resolverQuery.getTypes().length == 0 || MethodResolver.ClassListEqual(resolverQuery.getTypes(), method.getParameterTypes()))) {
                return AccessUtil.setAccessible(method);
            }
            ++n2;
        }
        throw new NoSuchMethodException();
    }

    @Override
    protected NoSuchMethodException notFoundException(String string) {
        return new NoSuchMethodException("Could not resolve method for " + string + " in class " + this.clazz);
    }

    static boolean ClassListEqual(Class<?>[] arrclass, Class<?>[] arrclass2) {
        boolean bl = true;
        if (arrclass.length != arrclass2.length) {
            return false;
        }
        int n = 0;
        while (n < arrclass.length) {
            if (arrclass[n] != arrclass2[n]) {
                bl = false;
                break;
            }
            ++n;
        }
        return bl;
    }
}


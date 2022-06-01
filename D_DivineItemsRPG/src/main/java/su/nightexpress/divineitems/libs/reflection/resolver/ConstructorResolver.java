/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import su.nightexpress.divineitems.libs.reflection.resolver.MemberResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.ResolverQuery;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.ConstructorWrapper;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.WrapperAbstract;
import su.nightexpress.divineitems.libs.reflection.util.AccessUtil;

public class ConstructorResolver
extends MemberResolver<Constructor> {
    public ConstructorResolver(Class<?> class_) {
        super(class_);
    }

    public ConstructorResolver(String string) {
        super(string);
    }

    @Override
    public Constructor resolveIndex(int n) {
        return AccessUtil.setAccessible(this.clazz.getDeclaredConstructors()[n]);
    }

    @Override
    public Constructor resolveIndexSilent(int n) {
        try {
            return this.resolveIndex(n);
        }
        catch (IndexOutOfBoundsException | ReflectiveOperationException exception) {
            return null;
        }
    }

    @Override
    public ConstructorWrapper resolveIndexWrapper(int n) {
        return new ConstructorWrapper(this.resolveIndexSilent(n));
    }

    public /* varargs */ ConstructorWrapper resolveWrapper(Class<?>[] ... arrclass) {
        return new ConstructorWrapper(this.resolveSilent(arrclass));
    }

    public /* varargs */ Constructor resolveSilent(Class<?>[] ... arrclass) {
        try {
            return this.resolve(arrclass);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public /* varargs */ Constructor resolve(Class<?>[] ... arrclass) {
        ResolverQuery.Builder builder = ResolverQuery.builder();
        Class<?>[][] arrclass2 = arrclass;
        int n = arrclass2.length;
        int n2 = 0;
        while (n2 < n) {
            Class<?>[] arrclass3 = arrclass2[n2];
            builder.with(arrclass3);
            ++n2;
        }
        try {
            return (Constructor)super.resolve(builder.build());
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw (NoSuchMethodException)reflectiveOperationException;
        }
    }

    @Override
    protected Constructor resolveObject(ResolverQuery resolverQuery) {
        return AccessUtil.setAccessible(this.clazz.getDeclaredConstructor(resolverQuery.getTypes()));
    }

    public Constructor resolveFirstConstructor() {
        Constructor<?>[] arrconstructor = this.clazz.getDeclaredConstructors();
        if (arrconstructor.length != 0) {
            Constructor<?> constructor = arrconstructor[0];
            return AccessUtil.setAccessible(constructor);
        }
        return null;
    }

    public Constructor resolveFirstConstructorSilent() {
        try {
            return this.resolveFirstConstructor();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Constructor resolveLastConstructor() {
        Constructor<?> constructor = null;
        Constructor<?>[] arrconstructor = this.clazz.getDeclaredConstructors();
        int n = arrconstructor.length;
        int n2 = 0;
        while (n2 < n) {
            Constructor<?> constructor2;
            constructor = constructor2 = arrconstructor[n2];
            ++n2;
        }
        if (constructor != null) {
            return AccessUtil.setAccessible(constructor);
        }
        return null;
    }

    public Constructor resolveLastConstructorSilent() {
        try {
            return this.resolveLastConstructor();
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    protected NoSuchMethodException notFoundException(String string) {
        return new NoSuchMethodException("Could not resolve constructor for " + string + " in class " + this.clazz);
    }
}


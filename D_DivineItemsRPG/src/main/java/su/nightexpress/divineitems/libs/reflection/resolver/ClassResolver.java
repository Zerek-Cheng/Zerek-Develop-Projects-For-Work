/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver;

import su.nightexpress.divineitems.libs.reflection.resolver.ResolverAbstract;
import su.nightexpress.divineitems.libs.reflection.resolver.ResolverQuery;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.ClassWrapper;

public class ClassResolver
extends ResolverAbstract<Class> {
    public /* varargs */ ClassWrapper resolveWrapper(String ... arrstring) {
        return new ClassWrapper(this.resolveSilent(arrstring));
    }

    public /* varargs */ Class resolveSilent(String ... arrstring) {
        try {
            return this.resolve(arrstring);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public /* varargs */ Class resolve(String ... arrstring) {
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
            return (Class)super.resolve(builder.build());
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw (ClassNotFoundException)reflectiveOperationException;
        }
    }

    @Override
    protected Class resolveObject(ResolverQuery resolverQuery) {
        return Class.forName(resolverQuery.getName());
    }

    @Override
    protected ClassNotFoundException notFoundException(String string) {
        return new ClassNotFoundException("Could not resolve class for " + string);
    }
}


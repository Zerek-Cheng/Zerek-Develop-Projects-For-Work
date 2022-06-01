/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import su.nightexpress.divineitems.libs.reflection.resolver.ResolverQuery;

public abstract class ResolverAbstract<T> {
    protected final Map<ResolverQuery, T> resolvedObjects = new ConcurrentHashMap<ResolverQuery, T>();

    protected /* varargs */ T resolveSilent(ResolverQuery ... arrresolverQuery) {
        try {
            return this.resolve(arrresolverQuery);
        }
        catch (Exception exception) {
            return null;
        }
    }

    protected /* varargs */ T resolve(ResolverQuery ... arrresolverQuery) {
        if (arrresolverQuery == null || arrresolverQuery.length <= 0) {
            throw new IllegalArgumentException("Given possibilities are empty");
        }
        ResolverQuery[] arrresolverQuery2 = arrresolverQuery;
        int n = arrresolverQuery2.length;
        int n2 = 0;
        while (n2 < n) {
            ResolverQuery resolverQuery = arrresolverQuery2[n2];
            if (this.resolvedObjects.containsKey(resolverQuery)) {
                return this.resolvedObjects.get(resolverQuery);
            }
            try {
                T t = this.resolveObject(resolverQuery);
                this.resolvedObjects.put(resolverQuery, t);
                return t;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                ++n2;
            }
        }
        throw this.notFoundException(Arrays.asList(arrresolverQuery).toString());
    }

    protected abstract T resolveObject(ResolverQuery var1);

    protected ReflectiveOperationException notFoundException(String string) {
        return new ReflectiveOperationException("Objects could not be resolved: " + string);
    }
}


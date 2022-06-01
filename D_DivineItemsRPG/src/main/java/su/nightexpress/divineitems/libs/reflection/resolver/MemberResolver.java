/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver;

import java.lang.reflect.Member;
import su.nightexpress.divineitems.libs.reflection.resolver.ClassResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.ResolverAbstract;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.WrapperAbstract;

public abstract class MemberResolver<T extends Member>
extends ResolverAbstract<T> {
    protected Class<?> clazz;

    public MemberResolver(Class<?> class_) {
        if (class_ == null) {
            throw new IllegalArgumentException("class cannot be null");
        }
        this.clazz = class_;
    }

    public MemberResolver(String string) {
        this(new ClassResolver().resolve(string));
    }

    public abstract T resolveIndex(int var1);

    public abstract T resolveIndexSilent(int var1);

    public abstract WrapperAbstract resolveIndexWrapper(int var1);
}


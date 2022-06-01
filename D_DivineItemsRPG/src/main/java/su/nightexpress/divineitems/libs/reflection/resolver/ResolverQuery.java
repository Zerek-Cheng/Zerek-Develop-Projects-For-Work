/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResolverQuery {
    private String name;
    private Class<?>[] types;

    public /* varargs */ ResolverQuery(String string, Class<?> ... arrclass) {
        this.name = string;
        this.types = arrclass;
    }

    public ResolverQuery(String string) {
        this.name = string;
        this.types = new Class[0];
    }

    public /* varargs */ ResolverQuery(Class<?> ... arrclass) {
        this.types = arrclass;
    }

    public String getName() {
        return this.name;
    }

    public Class<?>[] getTypes() {
        return this.types;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        ResolverQuery resolverQuery = (ResolverQuery)object;
        if (this.name != null ? !this.name.equals(resolverQuery.name) : resolverQuery.name != null) {
            return false;
        }
        return Arrays.equals(this.types, resolverQuery.types);
    }

    public int hashCode() {
        int n = this.name != null ? this.name.hashCode() : 0;
        n = 31 * n + (this.types != null ? Arrays.hashCode(this.types) : 0);
        return n;
    }

    public String toString() {
        return "ResolverQuery{name='" + this.name + '\'' + ", types=" + Arrays.toString(this.types) + '}';
    }

    public static Builder builder() {
        return new Builder();
    }

}


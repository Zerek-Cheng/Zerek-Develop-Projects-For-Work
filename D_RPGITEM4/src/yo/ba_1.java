/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.LinkedHashMap;

public class ba_1<K, V>
extends LinkedHashMap<K, V> {
    private K a = null;
    private K b = null;
    private K c = null;
    private K d = null;
    private V e = null;
    private V f = null;
    private V g = null;
    private V h = null;

    @Override
    public V remove(Object key) {
        Object lastValue = super.remove(key);
        if (this.a != null && this.a.equals(key)) {
            this.a = this.c;
            this.e = this.g;
        } else if (this.c != null && this.c.equals(key)) {
            this.c = null;
            this.g = null;
        } else if (this.b != null && this.b.equals(key)) {
            this.b = this.d;
            this.f = this.h;
        } else if (this.d != null && this.d.equals(key)) {
            this.d = null;
            this.h = null;
        }
        return lastValue;
    }

    @Override
    public V put(K key, V value) {
        V lastValue = super.put(key, value);
        if (this.a != null) {
            if (!this.a(this.e, value)) {
                this.c = this.a;
                this.g = this.e;
                this.a = key;
                this.e = value;
            } else if (!this.a(this.f, value)) {
                this.d = this.b;
                this.h = this.f;
                this.b = key;
                this.f = value;
            }
        } else {
            this.a = key;
            this.c = key;
            this.b = key;
            this.d = key;
            this.e = value;
            this.g = value;
            this.f = value;
            this.h = value;
        }
        return lastValue;
    }

    @Override
    public void clear() {
        super.clear();
        this.a = null;
        this.c = null;
        this.b = null;
        this.d = null;
        this.e = null;
        this.g = null;
        this.f = null;
        this.h = null;
    }

    private boolean a(Object o1, Object o2) {
        if (o1 == null) {
            return false;
        }
        if (o2 == null) {
            return true;
        }
        if (o1 instanceof Number && o2 instanceof Number) {
            return ((Number)o1).doubleValue() >= ((Number)o2).doubleValue();
        }
        return o1.toString().compareTo(o2.toString()) >= 0;
    }

    public K a() {
        return this.a;
    }

    public K b() {
        return this.b;
    }

    public K c() {
        return this.c;
    }

    public K d() {
        return this.d;
    }

    public V e() {
        return this.e;
    }

    public V f() {
        return this.f;
    }

    public V g() {
        return this.g;
    }

    public V h() {
        return this.h;
    }
}


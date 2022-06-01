/*
 * Decompiled with CFR 0_133.
 */
package yo;

import yo.m_0;
import yo.p_1;

public abstract class w_1
extends p_1 {
    static final long p = 1L;
    public transient byte[] q;
    public static final byte r = 0;
    public static final byte s = 1;
    public static final byte t = 2;

    public w_1() {
    }

    public w_1(int initialCapacity) {
        this(initialCapacity, 0.5f);
    }

    public w_1(int initialCapacity, float loadFactor) {
        initialCapacity = Math.max(1, initialCapacity);
        this.j = loadFactor;
        this.a(m_0.b((float)initialCapacity / loadFactor));
    }

    @Override
    public int d() {
        return this.q.length;
    }

    @Override
    protected void b(int index) {
        this.q[index] = 2;
        super.b(index);
    }

    @Override
    protected int a(int initialCapacity) {
        int capacity = super.a(initialCapacity);
        this.q = new byte[capacity];
        return capacity;
    }
}


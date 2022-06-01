/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import yo.g_1;
import yo.p_1;
import yo.v_0;

public abstract class q_1<V>
implements Iterator<V>,
g_1 {
    private final v_0<V> d;
    protected final p_1 a;
    protected int b;
    protected int c;

    protected q_1(v_0<V> hash) {
        this.a = hash;
        this.b = this.a.c();
        this.c = this.a.d();
        this.d = hash;
    }

    @Override
    public V next() {
        this.z_();
        return this.a(this.c);
    }

    @Override
    public boolean hasNext() {
        return this.A_() >= 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void remove() {
        if (this.b != this.a.c()) {
            throw new ConcurrentModificationException();
        }
        try {
            this.a.f_();
            this.a.b(this.c);
        }
        finally {
            this.a.a(false);
        }
        --this.b;
    }

    protected final void z_() {
        this.c = this.A_();
        if (this.c < 0) {
            throw new NoSuchElementException();
        }
    }

    protected final int A_() {
        if (this.b != this.a.c()) {
            throw new ConcurrentModificationException();
        }
        Object[] set = this.d.b;
        int i = this.c;
        while (i-- > 0 && (set[i] == v_0.d || set[i] == v_0.c)) {
        }
        return i;
    }

    protected abstract V a(int var1);
}


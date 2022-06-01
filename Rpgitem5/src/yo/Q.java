// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public abstract class q<V> implements Iterator<V>, G
{
    private final v<V> d;
    protected final p a;
    protected int b;
    protected int c;
    
    protected q(final v<V> hash) {
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
        final int a_ = this.A_();
        this.c = a_;
        if (a_ < 0) {
            throw new NoSuchElementException();
        }
    }
    
    protected final int A_() {
        if (this.b != this.a.c()) {
            throw new ConcurrentModificationException();
        }
        final Object[] set = this.d.b;
        int i = this.c;
        while (i-- > 0) {
            if (set[i] != v.d) {
                if (set[i] == v.c) {
                    continue;
                }
                break;
            }
        }
        return i;
    }
    
    protected abstract V a(final int p0);
}

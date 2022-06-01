// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public abstract class r implements L
{
    protected final w a;
    protected int b;
    protected int c;
    
    public r(final w hash) {
        this.a = hash;
        this.b = this.a.c();
        this.c = this.a.d();
    }
    
    protected final int g_() {
        if (this.b != this.a.c()) {
            throw new ConcurrentModificationException();
        }
        final byte[] states = this.a.q;
        int i = this.c;
        while (i-- > 0 && states[i] != 1) {}
        return i;
    }
    
    @Override
    public boolean hasNext() {
        return this.g_() >= 0;
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
    
    protected final void h_() {
        final int g_ = this.g_();
        this.c = g_;
        if (g_ < 0) {
            throw new NoSuchElementException();
        }
    }
}

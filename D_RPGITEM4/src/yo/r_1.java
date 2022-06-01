/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import yo.l_1;
import yo.w_1;

public abstract class r_1
implements l_1 {
    protected final w_1 a;
    protected int b;
    protected int c;

    public r_1(w_1 hash) {
        this.a = hash;
        this.b = this.a.c();
        this.c = this.a.d();
    }

    protected final int g_() {
        if (this.b != this.a.c()) {
            throw new ConcurrentModificationException();
        }
        byte[] states = this.a.q;
        int i = this.c;
        while (i-- > 0 && states[i] != 1) {
        }
        return i;
    }

    @Override
    public boolean hasNext() {
        return this.g_() >= 0;
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

    protected final void h_() {
        this.c = this.g_();
        if (this.c < 0) {
            throw new NoSuchElementException();
        }
    }
}


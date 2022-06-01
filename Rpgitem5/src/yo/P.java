// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.Externalizable;

public abstract class p implements Externalizable
{
    static final long e = -1792948471915530295L;
    protected static final float f = 0.5f;
    protected static final int g = 10;
    protected transient int h;
    protected transient int i;
    protected float j;
    protected int k;
    protected int l;
    protected float m;
    protected transient boolean n;
    
    public p() {
        this(10, 0.5f);
    }
    
    public p(final int initialCapacity) {
        this(initialCapacity, 0.5f);
    }
    
    public p(final int initialCapacity, final float loadFactor) {
        this.n = false;
        this.j = loadFactor;
        this.m = loadFactor;
        this.a(yo.m.b(initialCapacity / loadFactor));
    }
    
    public boolean b() {
        return 0 == this.h;
    }
    
    public int c() {
        return this.h;
    }
    
    public abstract int d();
    
    public void l_(final int desiredCapacity) {
        if (desiredCapacity > this.k - this.c()) {
            this.d(yo.n.a(Math.max(this.c() + 1, yo.m.b((desiredCapacity + this.c()) / this.j) + 1)));
            this.e(this.d());
        }
    }
    
    public void e() {
        this.d(yo.n.a(Math.max(this.h + 1, yo.m.b(this.c() / this.j) + 1)));
        this.e(this.d());
        if (this.m != 0.0f) {
            this.f(this.c());
        }
    }
    
    public void a(final float factor) {
        if (factor < 0.0f) {
            throw new IllegalArgumentException("Factor must be >= 0: " + factor);
        }
        this.m = factor;
    }
    
    public float f() {
        return this.m;
    }
    
    public final void g() {
        this.e();
    }
    
    protected void b(final int index) {
        --this.h;
        if (this.m != 0.0f) {
            --this.l;
            if (!this.n && this.l <= 0) {
                this.e();
            }
        }
    }
    
    public void h() {
        this.h = 0;
        this.i = this.d();
    }
    
    protected int a(final int initialCapacity) {
        final int capacity = yo.n.a(initialCapacity);
        this.e(capacity);
        this.f(initialCapacity);
        return capacity;
    }
    
    protected abstract void d(final int p0);
    
    public void f_() {
        this.n = true;
    }
    
    public void a(final boolean check_for_compaction) {
        this.n = false;
        if (check_for_compaction && this.l <= 0 && this.m != 0.0f) {
            this.e();
        }
    }
    
    protected void e(final int capacity) {
        this.k = Math.min(capacity - 1, (int)(capacity * this.j));
        this.i = capacity - this.h;
    }
    
    protected void f(final int size) {
        if (this.m != 0.0f) {
            this.l = (int)(size * this.m + 0.5f);
        }
    }
    
    protected final void b(final boolean usedFreeSlot) {
        if (usedFreeSlot) {
            --this.i;
        }
        if (++this.h > this.k || this.i == 0) {
            final int newCapacity = (this.h > this.k) ? yo.n.a(this.d() << 1) : this.d();
            this.d(newCapacity);
            this.e(this.d());
        }
    }
    
    protected int j() {
        return this.d() << 1;
    }
    
    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeFloat(this.j);
        out.writeFloat(this.m);
    }
    
    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        final float old_factor = this.j;
        this.j = in.readFloat();
        this.m = in.readFloat();
        if (old_factor != this.j) {
            this.a((int)Math.ceil(10.0f / this.j));
        }
    }
}

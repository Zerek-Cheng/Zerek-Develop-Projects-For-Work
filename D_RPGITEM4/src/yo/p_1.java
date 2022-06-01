/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import yo.m_0;
import yo.n_1;

public abstract class p_1
implements Externalizable {
    static final long e = -1792948471915530295L;
    protected static final float f = 0.5f;
    protected static final int g = 10;
    protected transient int h;
    protected transient int i;
    protected float j;
    protected int k;
    protected int l;
    protected float m;
    protected transient boolean n = false;

    public p_1() {
        this(10, 0.5f);
    }

    public p_1(int initialCapacity) {
        this(initialCapacity, 0.5f);
    }

    public p_1(int initialCapacity, float loadFactor) {
        this.j = loadFactor;
        this.m = loadFactor;
        this.a(m_0.b((float)initialCapacity / loadFactor));
    }

    public boolean b() {
        return 0 == this.h;
    }

    public int c() {
        return this.h;
    }

    public abstract int d();

    public void l_(int desiredCapacity) {
        if (desiredCapacity > this.k - this.c()) {
            this.d(n_1.a(Math.max(this.c() + 1, m_0.b((float)(desiredCapacity + this.c()) / this.j) + 1)));
            this.e(this.d());
        }
    }

    public void e() {
        this.d(n_1.a(Math.max(this.h + 1, m_0.b((float)this.c() / this.j) + 1)));
        this.e(this.d());
        if (this.m != 0.0f) {
            this.f(this.c());
        }
    }

    public void a(float factor) {
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

    protected void b(int index) {
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

    protected int a(int initialCapacity) {
        int capacity = n_1.a(initialCapacity);
        this.e(capacity);
        this.f(initialCapacity);
        return capacity;
    }

    protected abstract void d(int var1);

    public void f_() {
        this.n = true;
    }

    public void a(boolean check_for_compaction) {
        this.n = false;
        if (check_for_compaction && this.l <= 0 && this.m != 0.0f) {
            this.e();
        }
    }

    protected void e(int capacity) {
        this.k = Math.min(capacity - 1, (int)((float)capacity * this.j));
        this.i = capacity - this.h;
    }

    protected void f(int size) {
        if (this.m != 0.0f) {
            this.l = (int)((float)size * this.m + 0.5f);
        }
    }

    protected final void b(boolean usedFreeSlot) {
        if (usedFreeSlot) {
            --this.i;
        }
        if (++this.h > this.k || this.i == 0) {
            int newCapacity = this.h > this.k ? n_1.a(this.d() << 1) : this.d();
            this.d(newCapacity);
            this.e(this.d());
        }
    }

    protected int j() {
        return this.d() << 1;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(0);
        out.writeFloat(this.j);
        out.writeFloat(this.m);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        float old_factor = this.j;
        this.j = in.readFloat();
        this.m = in.readFloat();
        if (old_factor != this.j) {
            this.a((int)Math.ceil(10.0f / this.j));
        }
    }
}


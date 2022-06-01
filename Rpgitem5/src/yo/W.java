// 
// Decompiled by Procyon v0.5.30
// 

package yo;

public abstract class w extends p
{
    static final long p = 1L;
    public transient byte[] q;
    public static final byte r = 0;
    public static final byte s = 1;
    public static final byte t = 2;
    
    public w() {
    }
    
    public w(final int initialCapacity) {
        this(initialCapacity, 0.5f);
    }
    
    public w(int initialCapacity, final float loadFactor) {
        initialCapacity = Math.max(1, initialCapacity);
        this.j = loadFactor;
        this.a(yo.m.b(initialCapacity / loadFactor));
    }
    
    @Override
    public int d() {
        return this.q.length;
    }
    
    @Override
    protected void b(final int index) {
        this.q[index] = 2;
        super.b(index);
    }
    
    @Override
    protected int a(final int initialCapacity) {
        final int capacity = super.a(initialCapacity);
        this.q = new byte[capacity];
        return capacity;
    }
}

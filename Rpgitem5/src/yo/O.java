// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.Arrays;

public abstract class o extends w
{
    static final long a = 1L;
    public transient char[] b;
    protected char c;
    protected boolean d;
    
    public o() {
        this.c = yo.l.e;
        if (this.c != '\0') {
            Arrays.fill(this.b, this.c);
        }
    }
    
    public o(final int initialCapacity) {
        super(initialCapacity);
        this.c = yo.l.e;
        if (this.c != '\0') {
            Arrays.fill(this.b, this.c);
        }
    }
    
    public o(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.c = yo.l.e;
        if (this.c != '\0') {
            Arrays.fill(this.b, this.c);
        }
    }
    
    public o(final int initialCapacity, final float loadFactor, final char no_entry_value) {
        super(initialCapacity, loadFactor);
        this.c = no_entry_value;
        if (no_entry_value != '\0') {
            Arrays.fill(this.b, no_entry_value);
        }
    }
    
    public char e_() {
        return this.c;
    }
    
    @Override
    protected int a(final int initialCapacity) {
        final int capacity = super.a(initialCapacity);
        this.b = new char[capacity];
        return capacity;
    }
    
    public boolean a(final char val) {
        return this.c_(val) >= 0;
    }
    
    public boolean a(final ad procedure) {
        final byte[] states = this.q;
        final char[] set = this.b;
        int i = set.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.a(set[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    protected void b(final int index) {
        this.b[index] = this.c;
        super.b(index);
    }
    
    protected int c_(final char val) {
        final byte[] states = this.q;
        final char[] set = this.b;
        final int length = states.length;
        final int hash = yo.m.a(val) & Integer.MAX_VALUE;
        final int index = hash % length;
        final byte state = states[index];
        if (state == 0) {
            return -1;
        }
        if (state == 1 && set[index] == val) {
            return index;
        }
        return this.a(val, index, hash, state);
    }
    
    int a(final char key, int index, final int hash, byte state) {
        final int length = this.b.length;
        final int probe = 1 + hash % (length - 2);
        final int loopIndex = index;
        do {
            index -= probe;
            if (index < 0) {
                index += length;
            }
            state = this.q[index];
            if (state == 0) {
                return -1;
            }
            if (key == this.b[index] && state != 2) {
                return index;
            }
        } while (index != loopIndex);
        return -1;
    }
    
    protected int c(final char val) {
        final int hash = yo.m.a(val) & Integer.MAX_VALUE;
        final int index = hash % this.q.length;
        final byte state = this.q[index];
        this.d = false;
        if (state == 0) {
            this.d = true;
            this.a(index, val);
            return index;
        }
        if (state == 1 && this.b[index] == val) {
            return -index - 1;
        }
        return this.b(val, index, hash, state);
    }
    
    int b(final char val, int index, final int hash, byte state) {
        final int length = this.b.length;
        final int probe = 1 + hash % (length - 2);
        final int loopIndex = index;
        int firstRemoved = -1;
        do {
            if (state == 2 && firstRemoved == -1) {
                firstRemoved = index;
            }
            index -= probe;
            if (index < 0) {
                index += length;
            }
            state = this.q[index];
            if (state == 0) {
                if (firstRemoved != -1) {
                    this.a(firstRemoved, val);
                    return firstRemoved;
                }
                this.d = true;
                this.a(index, val);
                return index;
            }
            else {
                if (state == 1 && this.b[index] == val) {
                    return -index - 1;
                }
                continue;
            }
        } while (index != loopIndex);
        if (firstRemoved != -1) {
            this.a(firstRemoved, val);
            return firstRemoved;
        }
        throw new IllegalStateException("No free or removed slots available. Key set full?!!");
    }
    
    void a(final int index, final char val) {
        this.b[index] = val;
        this.q[index] = 1;
    }
}

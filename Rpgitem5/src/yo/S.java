// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;

public abstract class s extends w
{
    static final long a = 1L;
    public transient int[] b;
    protected int c;
    protected byte d;
    protected boolean o;
    
    public s() {
        this.c = 0;
        this.d = 0;
    }
    
    public s(final int initialCapacity) {
        super(initialCapacity);
        this.c = 0;
        this.d = 0;
    }
    
    public s(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.c = 0;
        this.d = 0;
    }
    
    public s(final int initialCapacity, final float loadFactor, final int no_entry_key, final byte no_entry_value) {
        super(initialCapacity, loadFactor);
        this.c = no_entry_key;
        this.d = no_entry_value;
    }
    
    public int a() {
        return this.c;
    }
    
    public byte k() {
        return this.d;
    }
    
    @Override
    protected int a(final int initialCapacity) {
        final int capacity = super.a(initialCapacity);
        this.b = new int[capacity];
        return capacity;
    }
    
    public boolean g(final int val) {
        return this.h(val) >= 0;
    }
    
    public boolean a(final ai procedure) {
        final byte[] states = this.q;
        final int[] set = this.b;
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
    
    protected int h(final int key) {
        final byte[] states = this.q;
        final int[] set = this.b;
        final int length = states.length;
        final int hash = yo.m.a(key) & Integer.MAX_VALUE;
        final int index = hash % length;
        final byte state = states[index];
        if (state == 0) {
            return -1;
        }
        if (state == 1 && set[index] == key) {
            return index;
        }
        return this.a(key, index, hash, state);
    }
    
    int a(final int key, int index, final int hash, byte state) {
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
    
    protected int i(final int val) {
        final int hash = yo.m.a(val) & Integer.MAX_VALUE;
        final int index = hash % this.q.length;
        final byte state = this.q[index];
        this.o = false;
        if (state == 0) {
            this.o = true;
            this.a(index, val);
            return index;
        }
        if (state == 1 && this.b[index] == val) {
            return -index - 1;
        }
        return this.b(val, index, hash, state);
    }
    
    int b(final int val, int index, final int hash, byte state) {
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
                this.o = true;
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
    
    void a(final int index, final int val) {
        this.b[index] = val;
        this.q[index] = 1;
    }
    
    protected int j(final int key) {
        final byte[] states = this.q;
        final int[] set = this.b;
        final int length = states.length;
        final int hash = yo.m.a(key) & Integer.MAX_VALUE;
        int index = hash % length;
        byte state = states[index];
        this.o = false;
        if (state == 0) {
            this.o = true;
            set[index] = key;
            states[index] = 1;
            return index;
        }
        if (state == 1 && set[index] == key) {
            return -index - 1;
        }
        final int probe = 1 + hash % (length - 2);
        if (state != 2) {
            do {
                index -= probe;
                if (index < 0) {
                    index += length;
                }
                state = states[index];
            } while (state == 1 && set[index] != key);
        }
        if (state == 2) {
            final int firstRemoved = index;
            while (state != 0 && (state == 2 || set[index] != key)) {
                index -= probe;
                if (index < 0) {
                    index += length;
                }
                state = states[index];
            }
            if (state == 1) {
                return -index - 1;
            }
            set[index] = key;
            states[index] = 1;
            return firstRemoved;
        }
        else {
            if (state == 1) {
                return -index - 1;
            }
            this.o = true;
            set[index] = key;
            states[index] = 1;
            return index;
        }
    }
    
    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeInt(this.c);
        out.writeByte(this.d);
    }
    
    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.c = in.readInt();
        this.d = in.readByte();
    }
}

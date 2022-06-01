/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.Arrays;
import yo.ad_1;
import yo.l_0;
import yo.m_0;
import yo.w_1;

public abstract class o_1
extends w_1 {
    static final long a = 1L;
    public transient char[] b;
    protected char c;
    protected boolean d;

    public o_1() {
        this.c = l_0.e;
        if (this.c != '\u0000') {
            Arrays.fill(this.b, this.c);
        }
    }

    public o_1(int initialCapacity) {
        super(initialCapacity);
        this.c = l_0.e;
        if (this.c != '\u0000') {
            Arrays.fill(this.b, this.c);
        }
    }

    public o_1(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.c = l_0.e;
        if (this.c != '\u0000') {
            Arrays.fill(this.b, this.c);
        }
    }

    public o_1(int initialCapacity, float loadFactor, char no_entry_value) {
        super(initialCapacity, loadFactor);
        this.c = no_entry_value;
        if (no_entry_value != '\u0000') {
            Arrays.fill(this.b, no_entry_value);
        }
    }

    public char e_() {
        return this.c;
    }

    @Override
    protected int a(int initialCapacity) {
        int capacity = super.a(initialCapacity);
        this.b = new char[capacity];
        return capacity;
    }

    public boolean a(char val) {
        return this.c_(val) >= 0;
    }

    public boolean a(ad_1 procedure) {
        byte[] states = this.q;
        char[] set = this.b;
        int i = set.length;
        while (i-- > 0) {
            if (states[i] != 1 || procedure.a(set[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    protected void b(int index) {
        this.b[index] = this.c;
        super.b(index);
    }

    protected int c_(char val) {
        byte[] states = this.q;
        char[] set = this.b;
        int length = states.length;
        int hash = m_0.a(val) & Integer.MAX_VALUE;
        int index = hash % length;
        byte state = states[index];
        if (state == 0) {
            return -1;
        }
        if (state == 1 && set[index] == val) {
            return index;
        }
        return this.a(val, index, hash, state);
    }

    int a(char key, int index, int hash, byte state) {
        int length = this.b.length;
        int probe = 1 + hash % (length - 2);
        int loopIndex = index;
        do {
            if ((index -= probe) < 0) {
                index += length;
            }
            if ((state = this.q[index]) == 0) {
                return -1;
            }
            if (key != this.b[index] || state == 2) continue;
            return index;
        } while (index != loopIndex);
        return -1;
    }

    protected int c(char val) {
        int hash = m_0.a(val) & Integer.MAX_VALUE;
        int index = hash % this.q.length;
        byte state = this.q[index];
        this.d = false;
        if (state == 0) {
            this.d = true;
            this.a(index, val);
            return index;
        }
        if (state == 1 && this.b[index] == val) {
            return - index - 1;
        }
        return this.b(val, index, hash, state);
    }

    int b(char val, int index, int hash, byte state) {
        int length = this.b.length;
        int probe = 1 + hash % (length - 2);
        int loopIndex = index;
        int firstRemoved = -1;
        do {
            if (state == 2 && firstRemoved == -1) {
                firstRemoved = index;
            }
            if ((index -= probe) < 0) {
                index += length;
            }
            if ((state = this.q[index]) == 0) {
                if (firstRemoved != -1) {
                    this.a(firstRemoved, val);
                    return firstRemoved;
                }
                this.d = true;
                this.a(index, val);
                return index;
            }
            if (state != 1 || this.b[index] != val) continue;
            return - index - 1;
        } while (index != loopIndex);
        if (firstRemoved != -1) {
            this.a(firstRemoved, val);
            return firstRemoved;
        }
        throw new IllegalStateException("No free or removed slots available. Key set full?!!");
    }

    void a(int index, char val) {
        this.b[index] = val;
        this.q[index] = 1;
    }
}


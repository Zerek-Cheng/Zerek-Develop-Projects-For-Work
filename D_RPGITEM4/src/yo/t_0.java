/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.Arrays;
import yo.ai_0;
import yo.l_0;
import yo.m_0;
import yo.w_1;

public abstract class t_0
extends w_1 {
    static final long a = 1L;
    public transient int[] b;
    protected int c;
    protected boolean d;

    public t_0() {
        this.c = l_0.f;
        if (this.c != 0) {
            Arrays.fill(this.b, this.c);
        }
    }

    public t_0(int initialCapacity) {
        super(initialCapacity);
        this.c = l_0.f;
        if (this.c != 0) {
            Arrays.fill(this.b, this.c);
        }
    }

    public t_0(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.c = l_0.f;
        if (this.c != 0) {
            Arrays.fill(this.b, this.c);
        }
    }

    public t_0(int initialCapacity, float loadFactor, int no_entry_value) {
        super(initialCapacity, loadFactor);
        this.c = no_entry_value;
        if (no_entry_value != 0) {
            Arrays.fill(this.b, no_entry_value);
        }
    }

    public int u_() {
        return this.c;
    }

    @Override
    protected int a(int initialCapacity) {
        int capacity = super.a(initialCapacity);
        this.b = new int[capacity];
        return capacity;
    }

    public boolean g(int val) {
        return this.h(val) >= 0;
    }

    public boolean a(ai_0 procedure) {
        byte[] states = this.q;
        int[] set = this.b;
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

    protected int h(int val) {
        byte[] states = this.q;
        int[] set = this.b;
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

    int a(int key, int index, int hash, byte state) {
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

    protected int i(int val) {
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

    int b(int val, int index, int hash, byte state) {
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

    void a(int index, int val) {
        this.b[index] = val;
        this.q[index] = 1;
    }
}


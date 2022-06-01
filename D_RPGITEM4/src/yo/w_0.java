/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  yo.W
 */
package yo;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Set;
import yo.W;
import yo.ag_1;
import yo.ai_0;
import yo.ap_0;
import yo.d_0;
import yo.e_0;
import yo.e_1;
import yo.i_0;
import yo.m_0;
import yo.p_0;
import yo.r_1;
import yo.u_0;
import yo.w_1;

public class w_0
extends u_0
implements Externalizable,
p_0 {
    static final long u = 1L;
    protected transient int[] v;

    public w_0() {
    }

    public w_0(int initialCapacity) {
        super(initialCapacity);
    }

    public w_0(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public w_0(int initialCapacity, float loadFactor, int noEntryKey, int noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }

    public w_0(int[] keys, int[] values) {
        super(Math.max(keys.length, values.length));
        int size = Math.min(keys.length, values.length);
        for (int i = 0; i < size; ++i) {
            this.a_(keys[i], values[i]);
        }
    }

    public w_0(p_0 map) {
        super(map.c());
        if (map instanceof w_0) {
            w_0 hashmap = (w_0)map;
            this.j = hashmap.j;
            this.c = hashmap.c;
            this.d = hashmap.d;
            if (this.c != 0) {
                Arrays.fill(this.b, this.c);
            }
            if (this.d != 0) {
                Arrays.fill(this.v, this.d);
            }
            this.a((int)Math.ceil(10.0f / this.j));
        }
        this.a(map);
    }

    @Override
    protected int a(int initialCapacity) {
        int capacity = super.a(initialCapacity);
        this.v = new int[capacity];
        return capacity;
    }

    @Override
    protected void d(int newCapacity) {
        int oldCapacity = this.b.length;
        int[] oldKeys = this.b;
        int[] oldVals = this.v;
        byte[] oldStates = this.q;
        this.b = new int[newCapacity];
        this.v = new int[newCapacity];
        this.q = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] != 1) continue;
            int o = oldKeys[i];
            int index = this.i(o);
            this.v[index] = oldVals[i];
        }
    }

    @Override
    public int a_(int key, int value) {
        int index = this.i(key);
        return this.b(key, value, index);
    }

    @Override
    public int b(int key, int value) {
        int index = this.i(key);
        if (index < 0) {
            return this.v[- index - 1];
        }
        return this.b(key, value, index);
    }

    private int b(int key, int value, int index) {
        int previous = this.d;
        boolean isNewMapping = true;
        if (index < 0) {
            index = - index - 1;
            previous = this.v[index];
            isNewMapping = false;
        }
        this.v[index] = value;
        if (isNewMapping) {
            this.b(this.o);
        }
        return previous;
    }

    @Override
    public void a(Map<? extends Integer, ? extends Integer> map) {
        this.l_(map.size());
        for (Map.Entry<? extends Integer, ? extends Integer> entry : map.entrySet()) {
            this.a_(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void a(p_0 map) {
        this.l_(map.c());
        d_0 iter = map.i();
        while (iter.hasNext()) {
            iter.a();
            this.a_(iter.b(), iter.c());
        }
    }

    @Override
    public int e_(int key) {
        int index = this.i_(key);
        return index < 0 ? this.d : this.v[index];
    }

    @Override
    public void h() {
        super.h();
        Arrays.fill(this.b, 0, this.b.length, this.c);
        Arrays.fill(this.v, 0, this.v.length, this.d);
        Arrays.fill(this.q, 0, this.q.length, (byte)0);
    }

    @Override
    public boolean b() {
        return 0 == this.h;
    }

    @Override
    public int f_(int key) {
        int prev = this.d;
        int index = this.i_(key);
        if (index >= 0) {
            prev = this.v[index];
            this.b(index);
        }
        return prev;
    }

    @Override
    protected void b(int index) {
        this.v[index] = this.d;
        super.b(index);
    }

    @Override
    public ap_0 m_() {
        return new d();
    }

    @Override
    public int[] n_() {
        int[] keys = new int[this.c()];
        int[] k = this.b;
        byte[] states = this.q;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            keys[j++] = k[i];
        }
        return keys;
    }

    @Override
    public int[] a(int[] array) {
        int size = this.c();
        if (array.length < size) {
            array = new int[size];
        }
        int[] keys = this.b;
        byte[] states = this.q;
        int i = keys.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            array[j++] = keys[i];
        }
        return array;
    }

    @Override
    public e_1 o_() {
        return new e();
    }

    @Override
    public int[] p_() {
        int[] vals = new int[this.c()];
        int[] v = this.v;
        byte[] states = this.q;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            vals[j++] = v[i];
        }
        return vals;
    }

    @Override
    public int[] b(int[] array) {
        int size = this.c();
        if (array.length < size) {
            array = new int[size];
        }
        int[] v = this.v;
        byte[] states = this.q;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            array[j++] = v[i];
        }
        return array;
    }

    @Override
    public boolean g_(int val) {
        byte[] states = this.q;
        int[] vals = this.v;
        int i = vals.length;
        while (i-- > 0) {
            if (states[i] != 1 || val != vals[i]) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean h_(int key) {
        return this.g(key);
    }

    @Override
    public d_0 i() {
        return new a(this);
    }

    @Override
    public boolean b_(ai_0 procedure) {
        return this.a(procedure);
    }

    @Override
    public boolean b(ai_0 procedure) {
        byte[] states = this.q;
        int[] values = this.v;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] != 1 || procedure.a(values[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean a(ag_1 procedure) {
        byte[] states = this.q;
        int[] keys = this.b;
        int[] values = this.v;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] != 1 || procedure.a(keys[i], values[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public void a(i_0 function) {
        byte[] states = this.q;
        int[] values = this.v;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            values[i] = function.a(values[i]);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean b(ag_1 procedure) {
        boolean modified;
        modified = false;
        byte[] states = this.q;
        int[] keys = this.b;
        int[] values = this.v;
        this.f_();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (states[i] != 1 || procedure.a(keys[i], values[i])) continue;
                this.b(i);
                modified = true;
            }
        }
        finally {
            this.a(true);
        }
        return modified;
    }

    @Override
    public boolean h(int key) {
        return this.c(key, 1);
    }

    @Override
    public boolean c(int key, int amount) {
        int index = this.i_(key);
        if (index < 0) {
            return false;
        }
        int[] arrn = this.v;
        int n = index;
        arrn[n] = arrn[n] + amount;
        return true;
    }

    @Override
    public int a(int key, int adjust_amount, int put_amount) {
        int newValue;
        boolean isNewMapping;
        int index = this.i(key);
        if (index < 0) {
            index = - index - 1;
            int[] arrn = this.v;
            int n = index;
            int n2 = arrn[n] + adjust_amount;
            arrn[n] = n2;
            newValue = n2;
            isNewMapping = false;
        } else {
            newValue = this.v[index] = put_amount;
            isNewMapping = true;
        }
        byte previousState = this.q[index];
        if (isNewMapping) {
            this.b(this.o);
        }
        return newValue;
    }

    public boolean equals(Object other) {
        if (!(other instanceof p_0)) {
            return false;
        }
        p_0 that = (p_0)other;
        if (that.c() != this.c()) {
            return false;
        }
        int[] values = this.v;
        byte[] states = this.q;
        int this_no_entry_value = this.k();
        int that_no_entry_value = that.k();
        int i = values.length;
        while (i-- > 0) {
            int key;
            int that_value;
            int this_value;
            if (states[i] != 1 || (this_value = values[i]) == (that_value = that.e_(key = this.b[i])) || this_value == this_no_entry_value || that_value == that_no_entry_value) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hashcode = 0;
        byte[] states = this.q;
        int i = this.v.length;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            hashcode += m_0.a(this.b[i]) ^ m_0.a(this.v[i]);
        }
        return hashcode;
    }

    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.a(new ag_1(){
            private boolean c = true;

            @Override
            public boolean a(int key, int value) {
                if (this.c) {
                    this.c = false;
                } else {
                    buf.append(", ");
                }
                buf.append(key);
                buf.append("=");
                buf.append(value);
                return true;
            }
        });
        buf.append("}");
        return buf.toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeInt(this.h);
        int i = this.q.length;
        while (i-- > 0) {
            if (this.q[i] != 1) continue;
            out.writeInt(this.b[i]);
            out.writeInt(this.v[i]);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        int size = in.readInt();
        this.a(size);
        while (size-- > 0) {
            int key = in.readInt();
            int val = in.readInt();
            this.a_(key, val);
        }
    }

    static /* synthetic */ int a(w_0 x0) {
        return x0.c;
    }

    static /* synthetic */ int b(w_0 x0) {
        return x0.h;
    }

    static /* synthetic */ int c(w_0 x0) {
        return x0.h;
    }

    static /* synthetic */ int d(w_0 x0) {
        return x0.d;
    }

    static /* synthetic */ int e(w_0 x0) {
        return x0.d;
    }

    static /* synthetic */ int f(w_0 x0) {
        return x0.h;
    }

    static /* synthetic */ int g(w_0 x0) {
        return x0.h;
    }

    class a
    extends r_1
    implements d_0 {
        a(w_0 map) {
            super(map);
        }

        @Override
        public void a() {
            this.h_();
        }

        @Override
        public int b() {
            return w_0.this.b[this.c];
        }

        @Override
        public int c() {
            return w_0.this.v[this.c];
        }

        @Override
        public int a(int val) {
            int old = this.c();
            w_0.this.v[this.c] = val;
            return old;
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
                w_0.this.b(this.c);
            }
            finally {
                this.a.a(false);
            }
            --this.b;
        }
    }

    class c
    extends r_1
    implements e_0 {
        c(w_1 hash) {
            super(hash);
        }

        @Override
        public int a() {
            this.h_();
            return w_0.this.v[this.c];
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
                w_0.this.b(this.c);
            }
            finally {
                this.a.a(false);
            }
            --this.b;
        }
    }

    class b
    extends r_1
    implements e_0 {
        b(w_1 hash) {
            super(hash);
        }

        @Override
        public int a() {
            this.h_();
            return w_0.this.b[this.c];
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
                w_0.this.b(this.c);
            }
            finally {
                this.a.a(false);
            }
            --this.b;
        }
    }

    public class e
    implements e_1 {
        protected e() {
        }

        @Override
        public e_0 d() {
            return new c(w_0.this);
        }

        @Override
        public int a() {
            return W.e((w_0)w_0.this);
        }

        @Override
        public int b() {
            return W.f((w_0)w_0.this);
        }

        @Override
        public boolean c() {
            return 0 == W.g((w_0)w_0.this);
        }

        @Override
        public boolean a(int entry) {
            return w_0.this.g_(entry);
        }

        @Override
        public int[] e() {
            return w_0.this.p_();
        }

        @Override
        public int[] a(int[] dest) {
            return w_0.this.b(dest);
        }

        @Override
        public boolean b(int entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(int entry) {
            int[] values = w_0.this.v;
            int[] set = w_0.this.b;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] == 0 || set[i] == 2 || entry != values[i]) continue;
                w_0.this.b(i);
                return true;
            }
            return false;
        }

        @Override
        public boolean a(Collection<?> collection) {
            for (Object element : collection) {
                if (element instanceof Integer) {
                    int ele = (Integer)element;
                    if (w_0.this.g_(ele)) continue;
                    return false;
                }
                return false;
            }
            return true;
        }

        @Override
        public boolean a(e_1 collection) {
            e_0 iter = collection.d();
            while (iter.hasNext()) {
                if (w_0.this.g_(iter.a())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(int[] array) {
            for (int element : array) {
                if (w_0.this.g_(element)) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean b(e_1 collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(int[] array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(Collection<?> collection) {
            boolean modified = false;
            e_0 iter = this.d();
            while (iter.hasNext()) {
                if (collection.contains(iter.a())) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean c(e_1 collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            e_0 iter = this.d();
            while (iter.hasNext()) {
                if (collection.a(iter.a())) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean d(int[] array) {
            boolean changed = false;
            Arrays.sort(array);
            int[] values = w_0.this.v;
            byte[] states = w_0.this.q;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] != 1 || Arrays.binarySearch(array, values[i]) >= 0) continue;
                w_0.this.b(i);
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(Collection<?> collection) {
            boolean changed = false;
            for (Object element : collection) {
                int c2;
                if (!(element instanceof Integer) || !this.c(c2 = ((Integer)element).intValue())) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(e_1 collection) {
            if (this == collection) {
                this.f();
                return true;
            }
            boolean changed = false;
            e_0 iter = collection.d();
            while (iter.hasNext()) {
                int element = iter.a();
                if (!this.c(element)) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean e(int[] array) {
            boolean changed = false;
            int i = array.length;
            while (i-- > 0) {
                if (!this.c(array[i])) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public void f() {
            w_0.this.h();
        }

        @Override
        public boolean a(ai_0 procedure) {
            return w_0.this.b(procedure);
        }

        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            w_0.this.b(new ai_0(){
                private boolean c = true;

                @Override
                public boolean a(int value) {
                    if (this.c) {
                        this.c = false;
                    } else {
                        buf.append(", ");
                    }
                    buf.append(value);
                    return true;
                }
            });
            buf.append("}");
            return buf.toString();
        }

    }

    public class d
    implements ap_0 {
        protected d() {
        }

        @Override
        public e_0 d() {
            return new b(w_0.this);
        }

        @Override
        public int a() {
            return W.a((w_0)w_0.this);
        }

        @Override
        public int b() {
            return W.b((w_0)w_0.this);
        }

        @Override
        public boolean c() {
            return 0 == W.c((w_0)w_0.this);
        }

        @Override
        public boolean a(int entry) {
            return w_0.this.g(entry);
        }

        @Override
        public int[] e() {
            return w_0.this.n_();
        }

        @Override
        public int[] a(int[] dest) {
            return w_0.this.a(dest);
        }

        @Override
        public boolean b(int entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(int entry) {
            return W.d((w_0)w_0.this) != w_0.this.f_(entry);
        }

        @Override
        public boolean a(Collection<?> collection) {
            for (Object element : collection) {
                if (element instanceof Integer) {
                    int ele = (Integer)element;
                    if (w_0.this.h_(ele)) continue;
                    return false;
                }
                return false;
            }
            return true;
        }

        @Override
        public boolean a(e_1 collection) {
            e_0 iter = collection.d();
            while (iter.hasNext()) {
                if (w_0.this.h_(iter.a())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(int[] array) {
            for (int element : array) {
                if (w_0.this.g(element)) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean b(e_1 collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(int[] array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(Collection<?> collection) {
            boolean modified = false;
            e_0 iter = this.d();
            while (iter.hasNext()) {
                if (collection.contains(iter.a())) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean c(e_1 collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            e_0 iter = this.d();
            while (iter.hasNext()) {
                if (collection.a(iter.a())) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean d(int[] array) {
            boolean changed = false;
            Arrays.sort(array);
            int[] set = w_0.this.b;
            byte[] states = w_0.this.q;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] != 1 || Arrays.binarySearch(array, set[i]) >= 0) continue;
                w_0.this.b(i);
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(Collection<?> collection) {
            boolean changed = false;
            for (Object element : collection) {
                int c2;
                if (!(element instanceof Integer) || !this.c(c2 = ((Integer)element).intValue())) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(e_1 collection) {
            if (this == collection) {
                this.f();
                return true;
            }
            boolean changed = false;
            e_0 iter = collection.d();
            while (iter.hasNext()) {
                int element = iter.a();
                if (!this.c(element)) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean e(int[] array) {
            boolean changed = false;
            int i = array.length;
            while (i-- > 0) {
                if (!this.c(array[i])) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public void f() {
            w_0.this.h();
        }

        @Override
        public boolean a(ai_0 procedure) {
            return w_0.this.b_(procedure);
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof ap_0)) {
                return false;
            }
            ap_0 that = (ap_0)other;
            if (that.b() != this.b()) {
                return false;
            }
            int i = w_0.this.q.length;
            while (i-- > 0) {
                if (w_0.this.q[i] != 1 || that.a(w_0.this.b[i])) continue;
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hashcode = 0;
            int i = w_0.this.q.length;
            while (i-- > 0) {
                if (w_0.this.q[i] != 1) continue;
                hashcode += m_0.a(w_0.this.b[i]);
            }
            return hashcode;
        }

        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            w_0.this.b_(new ai_0(){
                private boolean c = true;

                @Override
                public boolean a(int key) {
                    if (this.c) {
                        this.c = false;
                    } else {
                        buf.append(", ");
                    }
                    buf.append(key);
                    return true;
                }
            });
            buf.append("}");
            return buf.toString();
        }

    }

}


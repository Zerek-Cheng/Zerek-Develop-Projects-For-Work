/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  yo.V
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
import yo.V;
import yo.ab_0;
import yo.af_1;
import yo.ai_0;
import yo.ap_0;
import yo.b_1;
import yo.c_0;
import yo.e_0;
import yo.e_1;
import yo.g_0;
import yo.m_0;
import yo.o_0;
import yo.r_1;
import yo.s_1;
import yo.w_1;
import yo.y_1;

public class v_1
extends s_1
implements Externalizable,
o_0 {
    static final long u = 1L;
    protected transient byte[] v;

    public v_1() {
    }

    public v_1(int initialCapacity) {
        super(initialCapacity);
    }

    public v_1(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public v_1(int initialCapacity, float loadFactor, int noEntryKey, byte noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }

    public v_1(int[] keys, byte[] values) {
        super(Math.max(keys.length, values.length));
        int size = Math.min(keys.length, values.length);
        for (int i = 0; i < size; ++i) {
            this.a(keys[i], values[i]);
        }
    }

    public v_1(o_0 map) {
        super(map.c());
        if (map instanceof v_1) {
            v_1 hashmap = (v_1)map;
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
        this.v = new byte[capacity];
        return capacity;
    }

    @Override
    protected void d(int newCapacity) {
        int oldCapacity = this.b.length;
        int[] oldKeys = this.b;
        byte[] oldVals = this.v;
        byte[] oldStates = this.q;
        this.b = new int[newCapacity];
        this.v = new byte[newCapacity];
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
    public byte a(int key, byte value) {
        int index = this.i(key);
        return this.a(key, value, index);
    }

    @Override
    public byte b(int key, byte value) {
        int index = this.i(key);
        if (index < 0) {
            return this.v[- index - 1];
        }
        return this.a(key, value, index);
    }

    private byte a(int key, byte value, int index) {
        byte previous = this.d;
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
    public void a(Map<? extends Integer, ? extends Byte> map) {
        this.l_(map.size());
        for (Map.Entry<? extends Integer, ? extends Byte> entry : map.entrySet()) {
            this.a(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void a(o_0 map) {
        this.l_(map.c());
        c_0 iter = map.i();
        while (iter.hasNext()) {
            iter.a();
            this.a(iter.b(), iter.c());
        }
    }

    @Override
    public byte a_(int key) {
        int index = this.h(key);
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
    public byte b_(int key) {
        byte prev = this.d;
        int index = this.h(key);
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
    public ap_0 i_() {
        return new d();
    }

    @Override
    public int[] j_() {
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
    public b_1 k_() {
        return new e();
    }

    @Override
    public byte[] l_() {
        byte[] vals = new byte[this.c()];
        byte[] v = this.v;
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
    public byte[] a(byte[] array) {
        int size = this.c();
        if (array.length < size) {
            array = new byte[size];
        }
        byte[] v = this.v;
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
    public boolean a(byte val) {
        byte[] states = this.q;
        byte[] vals = this.v;
        int i = vals.length;
        while (i-- > 0) {
            if (states[i] != 1 || val != vals[i]) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean c_(int key) {
        return this.g(key);
    }

    @Override
    public c_0 i() {
        return new a(this);
    }

    @Override
    public boolean a_(ai_0 procedure) {
        return this.a(procedure);
    }

    @Override
    public boolean a(ab_0 procedure) {
        byte[] states = this.q;
        byte[] values = this.v;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] != 1 || procedure.a(values[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean a(af_1 procedure) {
        byte[] states = this.q;
        int[] keys = this.b;
        byte[] values = this.v;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] != 1 || procedure.a(keys[i], values[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public void a(g_0 function) {
        byte[] states = this.q;
        byte[] values = this.v;
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
    public boolean b(af_1 procedure) {
        boolean modified;
        modified = false;
        byte[] states = this.q;
        int[] keys = this.b;
        byte[] values = this.v;
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
    public boolean d_(int key) {
        return this.c(key, (byte)1);
    }

    @Override
    public boolean c(int key, byte amount) {
        int index = this.h(key);
        if (index < 0) {
            return false;
        }
        byte[] arrby = this.v;
        int n = index;
        arrby[n] = (byte)(arrby[n] + amount);
        return true;
    }

    @Override
    public byte a(int key, byte adjust_amount, byte put_amount) {
        boolean isNewMapping;
        byte newValue;
        int index = this.i(key);
        if (index < 0) {
            index = - index - 1;
            byte[] arrby = this.v;
            int n = index;
            byte by = (byte)(arrby[n] + adjust_amount);
            arrby[n] = by;
            newValue = by;
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
        if (!(other instanceof o_0)) {
            return false;
        }
        o_0 that = (o_0)other;
        if (that.c() != this.c()) {
            return false;
        }
        byte[] values = this.v;
        byte[] states = this.q;
        byte this_no_entry_value = this.k();
        byte that_no_entry_value = that.k();
        int i = values.length;
        while (i-- > 0) {
            int key;
            byte this_value;
            byte that_value;
            if (states[i] != 1 || (this_value = values[i]) == (that_value = that.a_(key = this.b[i])) || this_value == this_no_entry_value || that_value == that_no_entry_value) continue;
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
        this.a(new af_1(){
            private boolean c = true;

            @Override
            public boolean a(int key, byte value) {
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
            out.writeByte(this.v[i]);
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
            byte val = in.readByte();
            this.a(key, val);
        }
    }

    static /* synthetic */ int a(v_1 x0) {
        return x0.c;
    }

    static /* synthetic */ int b(v_1 x0) {
        return x0.h;
    }

    static /* synthetic */ int c(v_1 x0) {
        return x0.h;
    }

    static /* synthetic */ byte d(v_1 x0) {
        return x0.d;
    }

    static /* synthetic */ byte e(v_1 x0) {
        return x0.d;
    }

    static /* synthetic */ int f(v_1 x0) {
        return x0.h;
    }

    static /* synthetic */ int g(v_1 x0) {
        return x0.h;
    }

    class a
    extends r_1
    implements c_0 {
        a(v_1 map) {
            super(map);
        }

        @Override
        public void a() {
            this.h_();
        }

        @Override
        public int b() {
            return v_1.this.b[this.c];
        }

        @Override
        public byte c() {
            return v_1.this.v[this.c];
        }

        @Override
        public byte a(byte val) {
            byte old = this.c();
            v_1.this.v[this.c] = val;
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
                v_1.this.b(this.c);
            }
            finally {
                this.a.a(false);
            }
            --this.b;
        }
    }

    class c
    extends r_1
    implements y_1 {
        c(w_1 hash) {
            super(hash);
        }

        @Override
        public byte a() {
            this.h_();
            return v_1.this.v[this.c];
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
                v_1.this.b(this.c);
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
            return v_1.this.b[this.c];
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
                v_1.this.b(this.c);
            }
            finally {
                this.a.a(false);
            }
            --this.b;
        }
    }

    public class e
    implements b_1 {
        protected e() {
        }

        @Override
        public y_1 d() {
            return new c(v_1.this);
        }

        @Override
        public byte a() {
            return V.e((v_1)v_1.this);
        }

        @Override
        public int b() {
            return V.f((v_1)v_1.this);
        }

        @Override
        public boolean c() {
            return 0 == V.g((v_1)v_1.this);
        }

        @Override
        public boolean a(byte entry) {
            return v_1.this.a(entry);
        }

        @Override
        public byte[] e() {
            return v_1.this.l_();
        }

        @Override
        public byte[] a(byte[] dest) {
            return v_1.this.a(dest);
        }

        @Override
        public boolean b(byte entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(byte entry) {
            byte[] values = v_1.this.v;
            int[] set = v_1.this.b;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] == 0 || set[i] == 2 || entry != values[i]) continue;
                v_1.this.b(i);
                return true;
            }
            return false;
        }

        @Override
        public boolean a(Collection<?> collection) {
            for (Object element : collection) {
                if (element instanceof Byte) {
                    byte ele = (Byte)element;
                    if (v_1.this.a(ele)) continue;
                    return false;
                }
                return false;
            }
            return true;
        }

        @Override
        public boolean a(b_1 collection) {
            y_1 iter = collection.d();
            while (iter.hasNext()) {
                if (v_1.this.a(iter.a())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(byte[] array) {
            for (byte element : array) {
                if (v_1.this.a(element)) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(Collection<? extends Byte> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean b(b_1 collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(byte[] array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(Collection<?> collection) {
            boolean modified = false;
            y_1 iter = this.d();
            while (iter.hasNext()) {
                if (collection.contains(iter.a())) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean c(b_1 collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            y_1 iter = this.d();
            while (iter.hasNext()) {
                if (collection.a(iter.a())) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean d(byte[] array) {
            boolean changed = false;
            Arrays.sort(array);
            byte[] values = v_1.this.v;
            byte[] states = v_1.this.q;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] != 1 || Arrays.binarySearch(array, values[i]) >= 0) continue;
                v_1.this.b(i);
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(Collection<?> collection) {
            boolean changed = false;
            for (Object element : collection) {
                byte c2;
                if (!(element instanceof Byte) || !this.c(c2 = ((Byte)element).byteValue())) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(b_1 collection) {
            if (this == collection) {
                this.f();
                return true;
            }
            boolean changed = false;
            y_1 iter = collection.d();
            while (iter.hasNext()) {
                byte element = iter.a();
                if (!this.c(element)) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean e(byte[] array) {
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
            v_1.this.h();
        }

        @Override
        public boolean a(ab_0 procedure) {
            return v_1.this.a(procedure);
        }

        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            v_1.this.a(new ab_0(){
                private boolean c = true;

                @Override
                public boolean a(byte value) {
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
            return new b(v_1.this);
        }

        @Override
        public int a() {
            return V.a((v_1)v_1.this);
        }

        @Override
        public int b() {
            return V.b((v_1)v_1.this);
        }

        @Override
        public boolean c() {
            return 0 == V.c((v_1)v_1.this);
        }

        @Override
        public boolean a(int entry) {
            return v_1.this.g(entry);
        }

        @Override
        public int[] e() {
            return v_1.this.j_();
        }

        @Override
        public int[] a(int[] dest) {
            return v_1.this.a(dest);
        }

        @Override
        public boolean b(int entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(int entry) {
            return V.d((v_1)v_1.this) != v_1.this.b_(entry);
        }

        @Override
        public boolean a(Collection<?> collection) {
            for (Object element : collection) {
                if (element instanceof Integer) {
                    int ele = (Integer)element;
                    if (v_1.this.c_(ele)) continue;
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
                if (v_1.this.c_(iter.a())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(int[] array) {
            for (int element : array) {
                if (v_1.this.g(element)) continue;
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
            int[] set = v_1.this.b;
            byte[] states = v_1.this.q;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] != 1 || Arrays.binarySearch(array, set[i]) >= 0) continue;
                v_1.this.b(i);
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
            v_1.this.h();
        }

        @Override
        public boolean a(ai_0 procedure) {
            return v_1.this.a_(procedure);
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
            int i = v_1.this.q.length;
            while (i-- > 0) {
                if (v_1.this.q[i] != 1 || that.a(v_1.this.b[i])) continue;
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hashcode = 0;
            int i = v_1.this.q.length;
            while (i-- > 0) {
                if (v_1.this.q[i] != 1) continue;
                hashcode += m_0.a(v_1.this.b[i]);
            }
            return hashcode;
        }

        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            v_1.this.a_(new ai_0(){
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


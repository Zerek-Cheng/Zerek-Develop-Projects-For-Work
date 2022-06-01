/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  yo.X
 *  yo.X.b
 */
package yo;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import yo.X;
import yo.ah_0;
import yo.ai_0;
import yo.an_1;
import yo.ap_0;
import yo.e_0;
import yo.e_1;
import yo.f_0;
import yo.k_0;
import yo.l_0;
import yo.m_0;
import yo.q_0;
import yo.r_1;
import yo.t_0;
import yo.w_1;

public class x_0<V>
extends t_0
implements Externalizable,
q_0<V> {
    static final long o = 1L;
    private final ah_0<V> w = new ah_0<V>(){

        @Override
        public boolean a(int key, V value) {
            x_0.this.a(key, value);
            return true;
        }
    };
    protected transient V[] u;
    protected int v;

    public x_0() {
    }

    public x_0(int initialCapacity) {
        super(initialCapacity);
        this.v = l_0.f;
    }

    public x_0(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.v = l_0.f;
    }

    public x_0(int initialCapacity, float loadFactor, int noEntryKey) {
        super(initialCapacity, loadFactor);
        this.v = noEntryKey;
    }

    public x_0(q_0<? extends V> map) {
        this(map.c(), 0.5f, map.a());
        this.a(map);
    }

    @Override
    protected int a(int initialCapacity) {
        int capacity = super.a(initialCapacity);
        this.u = new Object[capacity];
        return capacity;
    }

    @Override
    protected void d(int newCapacity) {
        int oldCapacity = this.b.length;
        int[] oldKeys = this.b;
        V[] oldVals = this.u;
        byte[] oldStates = this.q;
        this.b = new int[newCapacity];
        this.u = new Object[newCapacity];
        this.q = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] != 1) continue;
            int o = oldKeys[i];
            int index = this.i(o);
            this.u[index] = oldVals[i];
        }
    }

    @Override
    public int a() {
        return this.v;
    }

    @Override
    public boolean c(int key) {
        return this.g(key);
    }

    @Override
    public boolean a(Object val) {
        byte[] states = this.q;
        V[] vals = this.u;
        if (null == val) {
            int i = vals.length;
            while (i-- > 0) {
                if (states[i] != 1 || null != vals[i]) continue;
                return true;
            }
        } else {
            int i = vals.length;
            while (i-- > 0) {
                if (states[i] != 1 || val != vals[i] && !val.equals(vals[i])) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public V j_(int key) {
        int index = this.h(key);
        return index < 0 ? null : (V)this.u[index];
    }

    @Override
    public V a(int key, V value) {
        int index = this.i(key);
        return this.a(value, index);
    }

    @Override
    public V b(int key, V value) {
        int index = this.i(key);
        if (index < 0) {
            return this.u[- index - 1];
        }
        return this.a(value, index);
    }

    private V a(V value, int index) {
        V previous = null;
        boolean isNewMapping = true;
        if (index < 0) {
            index = - index - 1;
            previous = this.u[index];
            isNewMapping = false;
        }
        this.u[index] = value;
        if (isNewMapping) {
            this.b(this.d);
        }
        return previous;
    }

    @Override
    public V k_(int key) {
        V prev = null;
        int index = this.h(key);
        if (index >= 0) {
            prev = this.u[index];
            this.b(index);
        }
        return prev;
    }

    @Override
    protected void b(int index) {
        this.u[index] = null;
        super.b(index);
    }

    @Override
    public void a(Map<? extends Integer, ? extends V> map) {
        Set<Map.Entry<Integer, V>> set = map.entrySet();
        for (Map.Entry<Integer, V> entry : set) {
            this.a(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void a(q_0<? extends V> map) {
        map.a(this.w);
    }

    @Override
    public void h() {
        super.h();
        Arrays.fill(this.b, 0, this.b.length, this.v);
        Arrays.fill(this.q, 0, this.q.length, (byte)0);
        Arrays.fill(this.u, 0, this.u.length, null);
    }

    @Override
    public ap_0 q_() {
        return new a();
    }

    @Override
    public int[] r_() {
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
    public int[] a(int[] dest) {
        if (dest.length < this.h) {
            dest = new int[this.h];
        }
        int[] k = this.b;
        byte[] states = this.q;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            dest[j++] = k[i];
        }
        return dest;
    }

    @Override
    public Collection<V> s_() {
        return new d();
    }

    @Override
    public Object[] t_() {
        Object[] vals = new Object[this.c()];
        V[] v = this.u;
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
    public V[] a(V[] dest) {
        if (dest.length < this.h) {
            dest = (Object[])Array.newInstance(dest.getClass().getComponentType(), this.h);
        }
        V[] v = this.u;
        byte[] states = this.q;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            dest[j++] = v[i];
        }
        return dest;
    }

    @Override
    public f_0<V> i() {
        return new c(this);
    }

    @Override
    public boolean c_(ai_0 procedure) {
        return this.a(procedure);
    }

    @Override
    public boolean a(an_1<? super V> procedure) {
        byte[] states = this.q;
        V[] values = this.u;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] != 1 || procedure.a(values[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean a(ah_0<? super V> procedure) {
        byte[] states = this.q;
        int[] keys = this.b;
        V[] values = this.u;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] != 1 || procedure.a(keys[i], values[i])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean b(ah_0<? super V> procedure) {
        boolean modified;
        modified = false;
        byte[] states = this.q;
        int[] keys = this.b;
        V[] values = this.u;
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
    public void a(k_0<V, V> function) {
        byte[] states = this.q;
        V[] values = this.u;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            values[i] = function.a(values[i]);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof q_0)) {
            return false;
        }
        q_0 that = (q_0)other;
        if (that.c() != this.c()) {
            return false;
        }
        try {
            f_0<V> iter = this.i();
            while (iter.hasNext()) {
                iter.a();
                int key = iter.b();
                V value = iter.c();
                if (!(value == null ? that.j_(key) != null || !that.c(key) : !value.equals(that.j_(key)))) continue;
                return false;
            }
        }
        catch (ClassCastException iter) {
            // empty catch block
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashcode = 0;
        V[] values = this.u;
        byte[] states = this.q;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] != 1) continue;
            hashcode += m_0.a(this.b[i]) ^ (values[i] == null ? 0 : values[i].hashCode());
        }
        return hashcode;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeInt(this.v);
        out.writeInt(this.h);
        int i = this.q.length;
        while (i-- > 0) {
            if (this.q[i] != 1) continue;
            out.writeInt(this.b[i]);
            out.writeObject(this.u[i]);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.v = in.readInt();
        int size = in.readInt();
        this.a(size);
        while (size-- > 0) {
            int key = in.readInt();
            Object val = in.readObject();
            this.a(key, val);
        }
    }

    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.a(new ah_0<V>(){
            private boolean c = true;

            @Override
            public boolean a(int key, Object value) {
                if (this.c) {
                    this.c = false;
                } else {
                    buf.append(",");
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

    static /* synthetic */ int a(x_0 x0) {
        return x0.h;
    }

    static /* synthetic */ int b(x_0 x0) {
        return x0.h;
    }

    class c<V>
    extends r_1
    implements f_0<V> {
        private final x_0<V> e;

        public c(x_0<V> map) {
            super(map);
            this.e = map;
        }

        @Override
        public void a() {
            this.h_();
        }

        @Override
        public int b() {
            return this.e.b[this.c];
        }

        @Override
        public V c() {
            return (V)this.e.u[this.c];
        }

        @Override
        public V a(V val) {
            V old = this.c();
            this.e.u[this.c] = val;
            return old;
        }
    }

    abstract class b<E>
    extends AbstractSet<E>
    implements Iterable<E>,
    Set<E> {
        private b() {
        }

        @Override
        public abstract Iterator<E> iterator();

        public abstract boolean a(E var1);

        public abstract boolean b(E var1);

        @Override
        public boolean contains(Object key) {
            return this.b(key);
        }

        @Override
        public boolean remove(Object o) {
            return this.a(o);
        }

        @Override
        public void clear() {
            x_0.this.h();
        }

        @Override
        public boolean add(E obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return x_0.this.c();
        }

        @Override
        public Object[] toArray() {
            Object[] result = new Object[this.size()];
            Iterator<E> e2 = this.iterator();
            int i = 0;
            while (e2.hasNext()) {
                result[i] = e2.next();
                ++i;
            }
            return result;
        }

        @Override
        public <T> T[] toArray(T[] a2) {
            int size = this.size();
            if (a2.length < size) {
                a2 = (Object[])Array.newInstance(a2.getClass().getComponentType(), size);
            }
            Iterator<E> it = this.iterator();
            T[] result = a2;
            for (int i = 0; i < size; ++i) {
                result[i] = it.next();
            }
            if (a2.length > size) {
                a2[size] = null;
            }
            return a2;
        }

        @Override
        public boolean isEmpty() {
            return x_0.this.b();
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            boolean changed = false;
            Iterator<E> i = this.iterator();
            while (i.hasNext()) {
                if (collection.contains(i.next())) continue;
                i.remove();
                changed = true;
            }
            return changed;
        }
    }

    public class d
    extends yo.X.b<V> {
        protected d() {
        }

        public Iterator<V> iterator() {
            return new x_0<V>(x_0.this){

                protected V a(int index) {
                    return (V)x_0.this.u[index];
                }
            };
        }

        public boolean b(V value) {
            return x_0.this.a(value);
        }

        public boolean a(V value) {
            Object[] values = x_0.this.u;
            byte[] states = x_0.this.q;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] != 1 || value != values[i] && (null == values[i] || !values[i].equals(value))) continue;
                x_0.this.b(i);
                return true;
            }
            return false;
        }

        class a
        extends r_1
        implements Iterator<V> {
            protected final x_0 e;

            public a(x_0 map) {
                super(map);
                this.e = map;
            }

            protected V a(int index) {
                byte[] states = x_0.this.q;
                Object value = this.e.u[index];
                if (states[index] != 1) {
                    return null;
                }
                return (V)value;
            }

            @Override
            public V next() {
                this.h_();
                return (V)this.e.u[this.c];
            }
        }

    }

    class yo.X$a
    implements ap_0 {
        yo.X$a() {
        }

        @Override
        public int a() {
            return x_0.this.v;
        }

        @Override
        public int b() {
            return X.a((x_0)x_0.this);
        }

        @Override
        public boolean c() {
            return X.b((x_0)x_0.this) == 0;
        }

        @Override
        public boolean a(int entry) {
            return x_0.this.c(entry);
        }

        @Override
        public e_0 d() {
            return new a(x_0.this);
        }

        @Override
        public int[] e() {
            return x_0.this.r_();
        }

        @Override
        public int[] a(int[] dest) {
            return x_0.this.a(dest);
        }

        @Override
        public boolean b(int entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(int entry) {
            return null != x_0.this.k_(entry);
        }

        @Override
        public boolean a(Collection<?> collection) {
            for (Object element : collection) {
                if (x_0.this.c(((Integer)element).intValue())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean a(e_1 collection) {
            if (collection == this) {
                return true;
            }
            e_0 iter = collection.d();
            while (iter.hasNext()) {
                if (x_0.this.c(iter.a())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(int[] array) {
            for (int element : array) {
                if (x_0.this.c(element)) continue;
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
            int[] set = x_0.this.b;
            byte[] states = x_0.this.q;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] != 1 || Arrays.binarySearch(array, set[i]) >= 0) continue;
                x_0.this.b(i);
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
            if (collection == this) {
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
            x_0.this.h();
        }

        @Override
        public boolean a(ai_0 procedure) {
            return x_0.this.c_(procedure);
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
            int i = x_0.this.q.length;
            while (i-- > 0) {
                if (x_0.this.q[i] != 1 || that.a(x_0.this.b[i])) continue;
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hashcode = 0;
            int i = x_0.this.q.length;
            while (i-- > 0) {
                if (x_0.this.q[i] != 1) continue;
                hashcode += m_0.a(x_0.this.b[i]);
            }
            return hashcode;
        }

        public String toString() {
            StringBuilder buf = new StringBuilder("{");
            boolean first = true;
            int i = x_0.this.q.length;
            while (i-- > 0) {
                if (x_0.this.q[i] != 1) continue;
                if (first) {
                    first = false;
                } else {
                    buf.append(",");
                }
                buf.append(x_0.this.b[i]);
            }
            return buf.toString();
        }

        class a
        extends r_1
        implements e_0 {
            private final t_0 e;

            public a(t_0 hash) {
                super(hash);
                this.e = hash;
            }

            @Override
            public int a() {
                this.h_();
                return this.e.b[this.c];
            }
        }

    }

}


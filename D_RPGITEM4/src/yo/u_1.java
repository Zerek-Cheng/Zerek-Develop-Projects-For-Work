/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  yo.U
 *  yo.U.b
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
import yo.U;
import yo.a_1;
import yo.ac_0;
import yo.ad_1;
import yo.an_1;
import yo.ao_1;
import yo.c_1;
import yo.k_0;
import yo.l_0;
import yo.m_0;
import yo.n_0;
import yo.o_1;
import yo.r_1;
import yo.w_1;
import yo.z_1;

public class u_1<V>
extends o_1
implements Externalizable,
n_0<V> {
    static final long o = 1L;
    private final ac_0<V> w = new ac_0<V>(){

        @Override
        public boolean a(char key, V value) {
            u_1.this.a(key, value);
            return true;
        }
    };
    protected transient V[] u;
    protected char v;

    public u_1() {
    }

    public u_1(int initialCapacity) {
        super(initialCapacity);
        this.v = l_0.e;
    }

    public u_1(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.v = l_0.e;
    }

    public u_1(int initialCapacity, float loadFactor, char noEntryKey) {
        super(initialCapacity, loadFactor);
        this.v = noEntryKey;
    }

    public u_1(n_0<? extends V> map) {
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
        char[] oldKeys = this.b;
        V[] oldVals = this.u;
        byte[] oldStates = this.q;
        this.b = new char[newCapacity];
        this.u = new Object[newCapacity];
        this.q = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] != 1) continue;
            char o = oldKeys[i];
            int index = this.c(o);
            this.u[index] = oldVals[i];
        }
    }

    @Override
    public char a() {
        return this.v;
    }

    @Override
    public boolean a_(char key) {
        return this.a(key);
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
    public V b(char key) {
        int index = this.c_(key);
        return index < 0 ? null : (V)this.u[index];
    }

    @Override
    public V a(char key, V value) {
        int index = this.c(key);
        return this.a(value, index);
    }

    @Override
    public V b(char key, V value) {
        int index = this.c(key);
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
    public V b_(char key) {
        V prev = null;
        int index = this.c_(key);
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
    public void a(Map<? extends Character, ? extends V> map) {
        Set<Map.Entry<Character, V>> set = map.entrySet();
        for (Map.Entry<Character, V> entry : set) {
            this.a(entry.getKey().charValue(), entry.getValue());
        }
    }

    @Override
    public void a(n_0<? extends V> map) {
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
    public ao_1 a_() {
        return new a();
    }

    @Override
    public char[] b_() {
        char[] keys = new char[this.c()];
        char[] k = this.b;
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
    public char[] a(char[] dest) {
        if (dest.length < this.h) {
            dest = new char[this.h];
        }
        char[] k = this.b;
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
    public Collection<V> c_() {
        return new d();
    }

    @Override
    public Object[] d_() {
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
    public a_1<V> i() {
        return new c(this);
    }

    @Override
    public boolean a_(ad_1 procedure) {
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
    public boolean a(ac_0<? super V> procedure) {
        byte[] states = this.q;
        char[] keys = this.b;
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
    public boolean b(ac_0<? super V> procedure) {
        boolean modified;
        modified = false;
        byte[] states = this.q;
        char[] keys = this.b;
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
        if (!(other instanceof n_0)) {
            return false;
        }
        n_0 that = (n_0)other;
        if (that.c() != this.c()) {
            return false;
        }
        try {
            a_1<V> iter = this.i();
            while (iter.hasNext()) {
                iter.a();
                char key = iter.b();
                V value = iter.c();
                if (!(value == null ? that.b(key) != null || !that.a_(key) : !value.equals(that.b(key)))) continue;
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
        out.writeChar(this.v);
        out.writeInt(this.h);
        int i = this.q.length;
        while (i-- > 0) {
            if (this.q[i] != 1) continue;
            out.writeChar(this.b[i]);
            out.writeObject(this.u[i]);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.v = in.readChar();
        int size = in.readInt();
        this.a(size);
        while (size-- > 0) {
            char key = in.readChar();
            Object val = in.readObject();
            this.a(key, val);
        }
    }

    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.a(new ac_0<V>(){
            private boolean c = true;

            @Override
            public boolean a(char key, Object value) {
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

    static /* synthetic */ int a(u_1 x0) {
        return x0.h;
    }

    static /* synthetic */ int b(u_1 x0) {
        return x0.h;
    }

    class c<V>
    extends r_1
    implements a_1<V> {
        private final u_1<V> e;

        public c(u_1<V> map) {
            super(map);
            this.e = map;
        }

        @Override
        public void a() {
            this.h_();
        }

        @Override
        public char b() {
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
            u_1.this.h();
        }

        @Override
        public boolean add(E obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return u_1.this.c();
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
            return u_1.this.b();
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
    extends yo.U.b<V> {
        protected d() {
        }

        public Iterator<V> iterator() {
            return new u_1<V>(u_1.this){

                protected V a(int index) {
                    return (V)u_1.this.u[index];
                }
            };
        }

        public boolean b(V value) {
            return u_1.this.a(value);
        }

        public boolean a(V value) {
            Object[] values = u_1.this.u;
            byte[] states = u_1.this.q;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] != 1 || value != values[i] && (null == values[i] || !values[i].equals(value))) continue;
                u_1.this.b(i);
                return true;
            }
            return false;
        }

        class a
        extends r_1
        implements Iterator<V> {
            protected final u_1 e;

            public a(u_1 map) {
                super(map);
                this.e = map;
            }

            protected V a(int index) {
                byte[] states = u_1.this.q;
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

    class yo.U$a
    implements ao_1 {
        yo.U$a() {
        }

        @Override
        public char a() {
            return u_1.this.v;
        }

        @Override
        public int b() {
            return U.a((u_1)u_1.this);
        }

        @Override
        public boolean c() {
            return U.b((u_1)u_1.this) == 0;
        }

        @Override
        public boolean a(char entry) {
            return u_1.this.a_(entry);
        }

        @Override
        public z_1 d() {
            return new a(u_1.this);
        }

        @Override
        public char[] e() {
            return u_1.this.b_();
        }

        @Override
        public char[] a(char[] dest) {
            return u_1.this.a(dest);
        }

        @Override
        public boolean b(char entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(char entry) {
            return null != u_1.this.b_(entry);
        }

        @Override
        public boolean a(Collection<?> collection) {
            for (Object element : collection) {
                if (u_1.this.a_(((Character)element).charValue())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean a(c_1 collection) {
            if (collection == this) {
                return true;
            }
            z_1 iter = collection.d();
            while (iter.hasNext()) {
                if (u_1.this.a_(iter.a())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(char[] array) {
            for (char element : array) {
                if (u_1.this.a_(element)) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(Collection<? extends Character> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean b(c_1 collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(char[] array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(Collection<?> collection) {
            boolean modified = false;
            z_1 iter = this.d();
            while (iter.hasNext()) {
                if (collection.contains(Character.valueOf(iter.a()))) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean c(c_1 collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            z_1 iter = this.d();
            while (iter.hasNext()) {
                if (collection.a(iter.a())) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean d(char[] array) {
            boolean changed = false;
            Arrays.sort(array);
            char[] set = u_1.this.b;
            byte[] states = u_1.this.q;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] != 1 || Arrays.binarySearch(array, set[i]) >= 0) continue;
                u_1.this.b(i);
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(Collection<?> collection) {
            boolean changed = false;
            for (Object element : collection) {
                char c2;
                if (!(element instanceof Character) || !this.c(c2 = ((Character)element).charValue())) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(c_1 collection) {
            if (collection == this) {
                this.f();
                return true;
            }
            boolean changed = false;
            z_1 iter = collection.d();
            while (iter.hasNext()) {
                char element = iter.a();
                if (!this.c(element)) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean e(char[] array) {
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
            u_1.this.h();
        }

        @Override
        public boolean a(ad_1 procedure) {
            return u_1.this.a_(procedure);
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof ao_1)) {
                return false;
            }
            ao_1 that = (ao_1)other;
            if (that.b() != this.b()) {
                return false;
            }
            int i = u_1.this.q.length;
            while (i-- > 0) {
                if (u_1.this.q[i] != 1 || that.a(u_1.this.b[i])) continue;
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hashcode = 0;
            int i = u_1.this.q.length;
            while (i-- > 0) {
                if (u_1.this.q[i] != 1) continue;
                hashcode += m_0.a(u_1.this.b[i]);
            }
            return hashcode;
        }

        public String toString() {
            StringBuilder buf = new StringBuilder("{");
            boolean first = true;
            int i = u_1.this.q.length;
            while (i-- > 0) {
                if (u_1.this.q[i] != 1) continue;
                if (first) {
                    first = false;
                } else {
                    buf.append(",");
                }
                buf.append(u_1.this.b[i]);
            }
            return buf.toString();
        }

        class a
        extends r_1
        implements z_1 {
            private final o_1 e;

            public a(o_1 hash) {
                super(hash);
                this.e = hash;
            }

            @Override
            public char a() {
                this.h_();
                return this.e.b[this.c];
            }
        }

    }

}


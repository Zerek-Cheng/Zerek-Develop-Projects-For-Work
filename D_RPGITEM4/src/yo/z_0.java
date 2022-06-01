/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  yo.Z
 *  yo.Z.b
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
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import yo.Z;
import yo.ai_0;
import yo.al_1;
import yo.an_1;
import yo.e_0;
import yo.e_1;
import yo.i_0;
import yo.j_1;
import yo.l_0;
import yo.m_0;
import yo.m_1;
import yo.p_1;
import yo.s_0;
import yo.v_0;

public class z_0<K>
extends v_0<K>
implements Externalizable,
s_0<K> {
    static final long p = 1L;
    private final al_1<K> s = new al_1<K>(){

        @Override
        public boolean a(K key, int value) {
            z_0.this.a(key, value);
            return true;
        }
    };
    protected transient int[] q;
    protected int r;

    public z_0() {
        this.r = l_0.f;
    }

    public z_0(int initialCapacity) {
        super(initialCapacity);
        this.r = l_0.f;
    }

    public z_0(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.r = l_0.f;
    }

    public z_0(int initialCapacity, float loadFactor, int noEntryValue) {
        super(initialCapacity, loadFactor);
        this.r = noEntryValue;
        if (this.r != 0) {
            Arrays.fill(this.q, this.r);
        }
    }

    public z_0(s_0<? extends K> map) {
        this(map.c(), 0.5f, map.a());
        if (map instanceof z_0) {
            z_0 hashmap = (z_0)map;
            this.j = hashmap.j;
            this.r = hashmap.r;
            if (this.r != 0) {
                Arrays.fill(this.q, this.r);
            }
            this.a((int)Math.ceil(10.0f / this.j));
        }
        this.a(map);
    }

    @Override
    public int a(int initialCapacity) {
        int capacity = super.a(initialCapacity);
        this.q = new int[capacity];
        return capacity;
    }

    @Override
    protected void d(int newCapacity) {
        int oldCapacity = this.b.length;
        Object[] oldKeys = this.b;
        int[] oldVals = this.q;
        this.b = new Object[newCapacity];
        Arrays.fill(this.b, d);
        this.q = new int[newCapacity];
        Arrays.fill(this.q, this.r);
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldKeys[i] == d || oldKeys[i] == c) continue;
            Object o = oldKeys[i];
            int index = this.d_(o);
            if (index < 0) {
                this.a(this.b[- index - 1], o);
            }
            this.b[index] = o;
            this.q[index] = oldVals[i];
        }
    }

    @Override
    public int a() {
        return this.r;
    }

    @Override
    public boolean e_(Object key) {
        return this.a(key);
    }

    @Override
    public boolean c(int val) {
        Object[] keys = this.b;
        int[] vals = this.q;
        int i = vals.length;
        while (i-- > 0) {
            if (keys[i] == d || keys[i] == c || val != vals[i]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int b(Object key) {
        int index = this.c_(key);
        return index < 0 ? this.r : this.q[index];
    }

    @Override
    public int a(K key, int value) {
        int index = this.d_(key);
        return this.b(value, index);
    }

    @Override
    public int b(K key, int value) {
        int index = this.d_(key);
        if (index < 0) {
            return this.q[- index - 1];
        }
        return this.b(value, index);
    }

    @Override
    private int b(int value, int index) {
        int previous = this.r;
        boolean isNewMapping = true;
        if (index < 0) {
            index = - index - 1;
            previous = this.q[index];
            isNewMapping = false;
        }
        this.q[index] = value;
        if (isNewMapping) {
            this.b(this.o);
        }
        return previous;
    }

    @Override
    public int f_(Object key) {
        int prev = this.r;
        int index = this.c_(key);
        if (index >= 0) {
            prev = this.q[index];
            this.b(index);
        }
        return prev;
    }

    @Override
    protected void b(int index) {
        this.q[index] = this.r;
        super.b(index);
    }

    @Override
    public void a(Map<? extends K, ? extends Integer> map) {
        Set<Map.Entry<K, Integer>> set = map.entrySet();
        for (Map.Entry<K, Integer> entry : set) {
            this.a(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void a(s_0<? extends K> map) {
        map.a(this.s);
    }

    @Override
    public void h() {
        super.h();
        Arrays.fill(this.b, 0, this.b.length, d);
        Arrays.fill(this.q, 0, this.q.length, this.r);
    }

    @Override
    public Set<K> B_() {
        return new a();
    }

    @Override
    public Object[] C_() {
        Object[] keys = new Object[this.c()];
        Object[] k = this.b;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (k[i] == d || k[i] == c) continue;
            keys[j++] = k[i];
        }
        return keys;
    }

    @Override
    public K[] a(K[] a2) {
        int size = this.c();
        if (a2.length < size) {
            a2 = (Object[])Array.newInstance(a2.getClass().getComponentType(), size);
        }
        Object[] k = this.b;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (k[i] == d || k[i] == c) continue;
            a2[j++] = k[i];
        }
        return a2;
    }

    @Override
    public e_1 D_() {
        return new c();
    }

    @Override
    public int[] E_() {
        int[] vals = new int[this.c()];
        int[] v = this.q;
        Object[] keys = this.b;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (keys[i] == d || keys[i] == c) continue;
            vals[j++] = v[i];
        }
        return vals;
    }

    @Override
    public int[] a(int[] array) {
        int size = this.c();
        if (array.length < size) {
            array = new int[size];
        }
        int[] v = this.q;
        Object[] keys = this.b;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (keys[i] == d || keys[i] == c) continue;
            array[j++] = v[i];
        }
        if (array.length > size) {
            array[size] = this.r;
        }
        return array;
    }

    @Override
    public j_1<K> i() {
        return new d(this);
    }

    @Override
    public boolean d(K key) {
        return this.c(key, 1);
    }

    @Override
    public boolean c(K key, int amount) {
        int index = this.c_(key);
        if (index < 0) {
            return false;
        }
        int[] arrn = this.q;
        int n = index;
        arrn[n] = arrn[n] + amount;
        return true;
    }

    @Override
    public int a(K key, int adjust_amount, int put_amount) {
        int newValue;
        boolean isNewMapping;
        int index = this.d_(key);
        if (index < 0) {
            index = - index - 1;
            int[] arrn = this.q;
            int n = index;
            int n2 = arrn[n] + adjust_amount;
            arrn[n] = n2;
            newValue = n2;
            isNewMapping = false;
        } else {
            newValue = this.q[index] = put_amount;
            isNewMapping = true;
        }
        if (isNewMapping) {
            this.b(this.o);
        }
        return newValue;
    }

    @Override
    public boolean b_(an_1<? super K> procedure) {
        return this.a(procedure);
    }

    @Override
    public boolean a(ai_0 procedure) {
        Object[] keys = this.b;
        int[] values = this.q;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] == d || keys[i] == c || procedure.a(values[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean a(al_1<? super K> procedure) {
        Object[] keys = this.b;
        int[] values = this.q;
        int i = keys.length;
        while (i-- > 0) {
            if (keys[i] == d || keys[i] == c || procedure.a(keys[i], values[i])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean b(al_1<? super K> procedure) {
        boolean modified;
        modified = false;
        Object[] keys = this.b;
        int[] values = this.q;
        this.f_();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (keys[i] == d || keys[i] == c || procedure.a(keys[i], values[i])) continue;
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
    public void a(i_0 function) {
        Object[] keys = this.b;
        int[] values = this.q;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] == null || keys[i] == c) continue;
            values[i] = function.a(values[i]);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof s_0)) {
            return false;
        }
        s_0 that = (s_0)other;
        if (that.c() != this.c()) {
            return false;
        }
        try {
            j_1<K> iter = this.i();
            while (iter.hasNext()) {
                iter.a();
                K key = iter.b();
                int value = iter.c();
                if (!(value == this.r ? that.b(key) != that.a() || !that.e_(key) : value != that.b(key))) continue;
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
        Object[] keys = this.b;
        int[] values = this.q;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] == d || keys[i] == c) continue;
            hashcode += m_0.a(values[i]) ^ (keys[i] == null ? 0 : keys[i].hashCode());
        }
        return hashcode;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeInt(this.r);
        out.writeInt(this.h);
        int i = this.b.length;
        while (i-- > 0) {
            if (this.b[i] == c || this.b[i] == d) continue;
            out.writeObject(this.b[i]);
            out.writeInt(this.q[i]);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.r = in.readInt();
        int size = in.readInt();
        this.a(size);
        while (size-- > 0) {
            Object key = in.readObject();
            int val = in.readInt();
            this.a(key, val);
        }
    }

    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.a(new al_1<K>(){
            private boolean c = true;

            @Override
            public boolean a(K key, int value) {
                if (this.c) {
                    this.c = false;
                } else {
                    buf.append(",");
                }
                buf.append(key).append("=").append(value);
                return true;
            }
        });
        buf.append("}");
        return buf.toString();
    }

    static /* synthetic */ int a(z_0 x0) {
        return x0.h;
    }

    static /* synthetic */ int b(z_0 x0) {
        return x0.h;
    }

    class d<K>
    extends m_1<K>
    implements j_1<K> {
        private final z_0<K> f;

        public d(z_0<K> map) {
            super(map);
            this.f = map;
        }

        @Override
        public void a() {
            this.z_();
        }

        @Override
        public K b() {
            return (K)this.f.b[this.c];
        }

        @Override
        public int c() {
            return this.f.q[this.c];
        }

        @Override
        public int m_(int val) {
            int old = this.c();
            this.f.q[this.c] = val;
            return old;
        }
    }

    class c
    implements e_1 {
        c() {
        }

        @Override
        public e_0 d() {
            return new a();
        }

        @Override
        public int a() {
            return z_0.this.r;
        }

        @Override
        public int b() {
            return Z.a((z_0)z_0.this);
        }

        @Override
        public boolean c() {
            return 0 == Z.b((z_0)z_0.this);
        }

        @Override
        public boolean a(int entry) {
            return z_0.this.c(entry);
        }

        @Override
        public int[] e() {
            return z_0.this.E_();
        }

        @Override
        public int[] a(int[] dest) {
            return z_0.this.a(dest);
        }

        @Override
        public boolean b(int entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(int entry) {
            int[] values = z_0.this.q;
            Object[] set = z_0.this.b;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] == v_0.d || set[i] == v_0.c || entry != values[i]) continue;
                z_0.this.b(i);
                return true;
            }
            return false;
        }

        @Override
        public boolean a(Collection<?> collection) {
            for (Object element : collection) {
                if (element instanceof Integer) {
                    int ele = (Integer)element;
                    if (z_0.this.c(ele)) continue;
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
                if (z_0.this.c(iter.a())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(int[] array) {
            for (int element : array) {
                if (z_0.this.c(element)) continue;
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
            int[] values = z_0.this.q;
            Object[] set = z_0.this.b;
            int i = set.length;
            while (i-- > 0) {
                if (set[i] == v_0.d || set[i] == v_0.c || Arrays.binarySearch(array, values[i]) >= 0) continue;
                z_0.this.b(i);
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
            z_0.this.h();
        }

        @Override
        public boolean a(ai_0 procedure) {
            return z_0.this.a(procedure);
        }

        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            z_0.this.a(new ai_0(){
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

        class a
        implements e_0 {
            protected p_1 a;
            protected int b;
            protected int c;

            a() {
                this.a = z_0.this;
                this.b = this.a.c();
                this.c = this.a.d();
            }

            @Override
            public boolean hasNext() {
                return this.c() >= 0;
            }

            @Override
            public int a() {
                this.b();
                return z_0.this.q[this.c];
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
                    z_0.this.b(this.c);
                }
                finally {
                    this.a.a(false);
                }
                --this.b;
            }

            protected final void b() {
                this.c = this.c();
                if (this.c < 0) {
                    throw new NoSuchElementException();
                }
            }

            protected final int c() {
                if (this.b != this.a.c()) {
                    throw new ConcurrentModificationException();
                }
                Object[] set = z_0.this.b;
                int i = this.c;
                while (i-- > 0 && (set[i] == v_0.d || set[i] == v_0.c)) {
                }
                return i;
            }
        }

    }

    abstract class b<E>
    extends AbstractSet<E>
    implements Iterable<E>,
    Set<E> {
        private b() {
        }

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
            z_0.this.h();
        }

        @Override
        public boolean add(E obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return z_0.this.c();
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
            return z_0.this.b();
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

    public class a
    extends yo.Z.b<K> {
        protected a() {
        }

        public Iterator<K> iterator() {
            return new m_1(z_0.this);
        }

        public boolean a(K key) {
            return z_0.this.r != z_0.this.f_(key);
        }

        public boolean b(K key) {
            return z_0.this.a(key);
        }
    }

}


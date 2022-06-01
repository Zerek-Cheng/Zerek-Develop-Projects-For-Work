/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  yo.Y
 *  yo.Y.b
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
import yo.Y;
import yo.ae_0;
import yo.ak_0;
import yo.an_1;
import yo.b_0;
import yo.d_1;
import yo.h_0;
import yo.i_1;
import yo.l_0;
import yo.m_0;
import yo.m_1;
import yo.p_1;
import yo.r_0;
import yo.v_0;

public class y_0<K>
extends v_0<K>
implements Externalizable,
r_0<K> {
    static final long p = 1L;
    private final ak_0<K> s = new ak_0<K>(){

        @Override
        public boolean a(K key, double value) {
            y_0.this.a(key, value);
            return true;
        }
    };
    protected transient double[] q;
    protected double r;

    public y_0() {
        this.r = l_0.i;
    }

    public y_0(int initialCapacity) {
        super(initialCapacity);
        this.r = l_0.i;
    }

    public y_0(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.r = l_0.i;
    }

    public y_0(int initialCapacity, float loadFactor, double noEntryValue) {
        super(initialCapacity, loadFactor);
        this.r = noEntryValue;
        if (this.r != 0.0) {
            Arrays.fill(this.q, this.r);
        }
    }

    public y_0(r_0<? extends K> map) {
        this(map.c(), 0.5f, map.a());
        if (map instanceof y_0) {
            y_0 hashmap = (y_0)map;
            this.j = hashmap.j;
            this.r = hashmap.r;
            if (this.r != 0.0) {
                Arrays.fill(this.q, this.r);
            }
            this.a((int)Math.ceil(10.0f / this.j));
        }
        this.a(map);
    }

    @Override
    public int a(int initialCapacity) {
        int capacity = super.a(initialCapacity);
        this.q = new double[capacity];
        return capacity;
    }

    @Override
    protected void d(int newCapacity) {
        int oldCapacity = this.b.length;
        Object[] oldKeys = this.b;
        double[] oldVals = this.q;
        this.b = new Object[newCapacity];
        Arrays.fill(this.b, d);
        this.q = new double[newCapacity];
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
    public double a() {
        return this.r;
    }

    @Override
    public boolean a_(Object key) {
        return this.a(key);
    }

    @Override
    public boolean a(double val) {
        Object[] keys = this.b;
        double[] vals = this.q;
        int i = vals.length;
        while (i-- > 0) {
            if (keys[i] == d || keys[i] == c || val != vals[i]) continue;
            return true;
        }
        return false;
    }

    @Override
    public double b(Object key) {
        int index = this.c_(key);
        return index < 0 ? this.r : this.q[index];
    }

    @Override
    public double a(K key, double value) {
        int index = this.d_(key);
        return this.a(value, index);
    }

    @Override
    public double b(K key, double value) {
        int index = this.d_(key);
        if (index < 0) {
            return this.q[- index - 1];
        }
        return this.a(value, index);
    }

    private double a(double value, int index) {
        double previous = this.r;
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
    public double b_(Object key) {
        double prev = this.r;
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
    public void a(Map<? extends K, ? extends Double> map) {
        Set<Map.Entry<K, Double>> set = map.entrySet();
        for (Map.Entry<K, Double> entry : set) {
            this.a(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void a(r_0<? extends K> map) {
        map.a(this.s);
    }

    @Override
    public void h() {
        super.h();
        Arrays.fill(this.b, 0, this.b.length, d);
        Arrays.fill(this.q, 0, this.q.length, this.r);
    }

    @Override
    public Set<K> v_() {
        return new a();
    }

    @Override
    public Object[] w_() {
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
    public d_1 x_() {
        return new c();
    }

    @Override
    public double[] y_() {
        double[] vals = new double[this.c()];
        double[] v = this.q;
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
    public double[] a(double[] array) {
        int size = this.c();
        if (array.length < size) {
            array = new double[size];
        }
        double[] v = this.q;
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
    public i_1<K> i() {
        return new d(this);
    }

    @Override
    public boolean d(K key) {
        return this.c(key, 1.0);
    }

    @Override
    public boolean c(K key, double amount) {
        int index = this.c_(key);
        if (index < 0) {
            return false;
        }
        double[] arrd = this.q;
        int n = index;
        arrd[n] = arrd[n] + amount;
        return true;
    }

    @Override
    public double a(K key, double adjust_amount, double put_amount) {
        double newValue;
        boolean isNewMapping;
        int index = this.d_(key);
        if (index < 0) {
            index = - index - 1;
            double[] arrd = this.q;
            int n = index;
            double d2 = arrd[n] + adjust_amount;
            arrd[n] = d2;
            newValue = d2;
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
    public boolean a_(an_1<? super K> procedure) {
        return this.a(procedure);
    }

    @Override
    public boolean a(ae_0 procedure) {
        Object[] keys = this.b;
        double[] values = this.q;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] == d || keys[i] == c || procedure.a(values[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean a(ak_0<? super K> procedure) {
        Object[] keys = this.b;
        double[] values = this.q;
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
    public boolean b(ak_0<? super K> procedure) {
        boolean modified;
        modified = false;
        Object[] keys = this.b;
        double[] values = this.q;
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
    public void a(h_0 function) {
        Object[] keys = this.b;
        double[] values = this.q;
        int i = values.length;
        while (i-- > 0) {
            if (keys[i] == null || keys[i] == c) continue;
            values[i] = function.a(values[i]);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof r_0)) {
            return false;
        }
        r_0 that = (r_0)other;
        if (that.c() != this.c()) {
            return false;
        }
        try {
            i_1<K> iter = this.i();
            while (iter.hasNext()) {
                iter.a();
                K key = iter.b();
                double value = iter.c();
                if (!(value == this.r ? that.b(key) != that.a() || !that.a_(key) : value != that.b(key))) continue;
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
        double[] values = this.q;
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
        out.writeDouble(this.r);
        out.writeInt(this.h);
        int i = this.b.length;
        while (i-- > 0) {
            if (this.b[i] == c || this.b[i] == d) continue;
            out.writeObject(this.b[i]);
            out.writeDouble(this.q[i]);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.r = in.readDouble();
        int size = in.readInt();
        this.a(size);
        while (size-- > 0) {
            Object key = in.readObject();
            double val = in.readDouble();
            this.a(key, val);
        }
    }

    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.a(new ak_0<K>(){
            private boolean c = true;

            @Override
            public boolean a(K key, double value) {
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

    static /* synthetic */ int a(y_0 x0) {
        return x0.h;
    }

    static /* synthetic */ int b(y_0 x0) {
        return x0.h;
    }

    class d<K>
    extends m_1<K>
    implements i_1<K> {
        private final y_0<K> f;

        public d(y_0<K> map) {
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
        public double c() {
            return this.f.q[this.c];
        }

        @Override
        public double a(double val) {
            double old = this.c();
            this.f.q[this.c] = val;
            return old;
        }
    }

    class c
    implements d_1 {
        c() {
        }

        @Override
        public b_0 d() {
            return new a();
        }

        @Override
        public double a() {
            return y_0.this.r;
        }

        @Override
        public int b() {
            return Y.a((y_0)y_0.this);
        }

        @Override
        public boolean c() {
            return 0 == Y.b((y_0)y_0.this);
        }

        @Override
        public boolean a(double entry) {
            return y_0.this.a(entry);
        }

        @Override
        public double[] e() {
            return y_0.this.y_();
        }

        @Override
        public double[] a(double[] dest) {
            return y_0.this.a(dest);
        }

        @Override
        public boolean b(double entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(double entry) {
            double[] values = y_0.this.q;
            Object[] set = y_0.this.b;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] == v_0.d || set[i] == v_0.c || entry != values[i]) continue;
                y_0.this.b(i);
                return true;
            }
            return false;
        }

        @Override
        public boolean a(Collection<?> collection) {
            for (Object element : collection) {
                if (element instanceof Double) {
                    double ele = (Double)element;
                    if (y_0.this.a(ele)) continue;
                    return false;
                }
                return false;
            }
            return true;
        }

        @Override
        public boolean a(d_1 collection) {
            b_0 iter = collection.d();
            while (iter.hasNext()) {
                if (y_0.this.a(iter.a())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(double[] array) {
            for (double element : array) {
                if (y_0.this.a(element)) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean b(Collection<? extends Double> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean b(d_1 collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(double[] array) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean c(Collection<?> collection) {
            boolean modified = false;
            b_0 iter = this.d();
            while (iter.hasNext()) {
                if (collection.contains(iter.a())) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean c(d_1 collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            b_0 iter = this.d();
            while (iter.hasNext()) {
                if (collection.a(iter.a())) continue;
                iter.remove();
                modified = true;
            }
            return modified;
        }

        @Override
        public boolean d(double[] array) {
            boolean changed = false;
            Arrays.sort(array);
            double[] values = y_0.this.q;
            Object[] set = y_0.this.b;
            int i = set.length;
            while (i-- > 0) {
                if (set[i] == v_0.d || set[i] == v_0.c || Arrays.binarySearch(array, values[i]) >= 0) continue;
                y_0.this.b(i);
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(Collection<?> collection) {
            boolean changed = false;
            for (Object element : collection) {
                double c2;
                if (!(element instanceof Double) || !this.c(c2 = ((Double)element).doubleValue())) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean d(d_1 collection) {
            if (this == collection) {
                this.f();
                return true;
            }
            boolean changed = false;
            b_0 iter = collection.d();
            while (iter.hasNext()) {
                double element = iter.a();
                if (!this.c(element)) continue;
                changed = true;
            }
            return changed;
        }

        @Override
        public boolean e(double[] array) {
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
            y_0.this.h();
        }

        @Override
        public boolean a(ae_0 procedure) {
            return y_0.this.a(procedure);
        }

        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            y_0.this.a(new ae_0(){
                private boolean c = true;

                @Override
                public boolean a(double value) {
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
        implements b_0 {
            protected p_1 a;
            protected int b;
            protected int c;

            a() {
                this.a = y_0.this;
                this.b = this.a.c();
                this.c = this.a.d();
            }

            @Override
            public boolean hasNext() {
                return this.c() >= 0;
            }

            @Override
            public double a() {
                this.b();
                return y_0.this.q[this.c];
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
                    y_0.this.b(this.c);
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
                Object[] set = y_0.this.b;
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
            y_0.this.h();
        }

        @Override
        public boolean add(E obj) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return y_0.this.c();
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
            return y_0.this.b();
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
    extends yo.Y.b<K> {
        protected a() {
        }

        public Iterator<K> iterator() {
            return new m_1(y_0.this);
        }

        public boolean a(K key) {
            return y_0.this.r != y_0.this.b_(key);
        }

        public boolean b(K key) {
            return y_0.this.a(key);
        }
    }

}


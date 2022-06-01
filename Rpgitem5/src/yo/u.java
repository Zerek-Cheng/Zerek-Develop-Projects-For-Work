// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.AbstractSet;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.io.Externalizable;

public class U<V> extends o implements Externalizable, N<V>
{
    static final long o = 1L;
    private final ac<V> w;
    protected transient V[] u;
    protected char v;
    
    public U() {
        this.w = new ac<V>() {
            @Override
            public boolean a(final char key, final V value) {
                U.this.a(key, value);
                return true;
            }
        };
    }
    
    public U(final int initialCapacity) {
        super(initialCapacity);
        this.w = new ac<V>() {
            @Override
            public boolean a(final char key, final V value) {
                U.this.a(key, value);
                return true;
            }
        };
        this.v = yo.l.e;
    }
    
    public U(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
        this.w = new ac<V>() {
            @Override
            public boolean a(final char key, final V value) {
                U.this.a(key, value);
                return true;
            }
        };
        this.v = yo.l.e;
    }
    
    public U(final int initialCapacity, final float loadFactor, final char noEntryKey) {
        super(initialCapacity, loadFactor);
        this.w = new ac<V>() {
            @Override
            public boolean a(final char key, final V value) {
                U.this.a(key, value);
                return true;
            }
        };
        this.v = noEntryKey;
    }
    
    public U(final N<? extends V> map) {
        this(map.c(), 0.5f, map.a());
        this.a(map);
    }
    
    @Override
    protected int a(final int initialCapacity) {
        final int capacity = super.a(initialCapacity);
        this.u = new Object[capacity];
        return capacity;
    }
    
    @Override
    protected void d(final int newCapacity) {
        final int oldCapacity = this.b.length;
        final char[] oldKeys = this.b;
        final V[] oldVals = (V[])this.u;
        final byte[] oldStates = this.q;
        this.b = new char[newCapacity];
        this.u = new Object[newCapacity];
        this.q = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] == 1) {
                final char o = oldKeys[i];
                final int index = this.c(o);
                this.u[index] = oldVals[i];
            }
        }
    }
    
    @Override
    public char a() {
        return this.v;
    }
    
    @Override
    public boolean a_(final char key) {
        return this.a(key);
    }
    
    @Override
    public boolean a(final Object val) {
        final byte[] states = this.q;
        final V[] vals = (V[])this.u;
        if (null == val) {
            int i = vals.length;
            while (i-- > 0) {
                if (states[i] == 1 && null == vals[i]) {
                    return true;
                }
            }
        }
        else {
            int i = vals.length;
            while (i-- > 0) {
                if (states[i] == 1 && (val == vals[i] || val.equals(vals[i]))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public V b(final char key) {
        final int index = this.c_(key);
        return (V)((index < 0) ? null : this.u[index]);
    }
    
    @Override
    public V a(final char key, final V value) {
        final int index = this.c(key);
        return this.a(value, index);
    }
    
    @Override
    public V b(final char key, final V value) {
        final int index = this.c(key);
        if (index < 0) {
            return (V)this.u[-index - 1];
        }
        return this.a(value, index);
    }
    
    private V a(final V value, int index) {
        V previous = null;
        boolean isNewMapping = true;
        if (index < 0) {
            index = -index - 1;
            previous = (V)this.u[index];
            isNewMapping = false;
        }
        this.u[index] = value;
        if (isNewMapping) {
            this.b(this.d);
        }
        return previous;
    }
    
    @Override
    public V b_(final char key) {
        V prev = null;
        final int index = this.c_(key);
        if (index >= 0) {
            prev = (V)this.u[index];
            this.b(index);
        }
        return prev;
    }
    
    @Override
    protected void b(final int index) {
        this.u[index] = null;
        super.b(index);
    }
    
    @Override
    public void a(final Map<? extends Character, ? extends V> map) {
        final Set<? extends Map.Entry<? extends Character, ? extends V>> set = map.entrySet();
        for (final Map.Entry<? extends Character, ? extends V> entry : set) {
            this.a((char)entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void a(final N<? extends V> map) {
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
    public ao a_() {
        return new a();
    }
    
    @Override
    public char[] b_() {
        final char[] keys = new char[this.c()];
        final char[] k = this.b;
        final byte[] states = this.q;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                keys[j++] = k[i];
            }
        }
        return keys;
    }
    
    @Override
    public char[] a(char[] dest) {
        if (dest.length < this.h) {
            dest = new char[this.h];
        }
        final char[] k = this.b;
        final byte[] states = this.q;
        int i = k.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                dest[j++] = k[i];
            }
        }
        return dest;
    }
    
    @Override
    public Collection<V> c_() {
        return new d();
    }
    
    @Override
    public Object[] d_() {
        final Object[] vals = new Object[this.c()];
        final V[] v = (V[])this.u;
        final byte[] states = this.q;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                vals[j++] = v[i];
            }
        }
        return vals;
    }
    
    @Override
    public V[] a(V[] dest) {
        if (dest.length < this.h) {
            dest = (V[])Array.newInstance(dest.getClass().getComponentType(), this.h);
        }
        final V[] v = (V[])this.u;
        final byte[] states = this.q;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                dest[j++] = v[i];
            }
        }
        return dest;
    }
    
    @Override
    public A<V> i() {
        return new c<V>(this);
    }
    
    @Override
    public boolean a_(final ad procedure) {
        return this.a(procedure);
    }
    
    @Override
    public boolean a(final an<? super V> procedure) {
        final byte[] states = this.q;
        final V[] values = (V[])this.u;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.a((Object)values[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean a(final ac<? super V> procedure) {
        final byte[] states = this.q;
        final char[] keys = this.b;
        final V[] values = (V[])this.u;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.a(keys[i], (Object)values[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean b(final ac<? super V> procedure) {
        boolean modified = false;
        final byte[] states = this.q;
        final char[] keys = this.b;
        final V[] values = (V[])this.u;
        this.f_();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (states[i] == 1 && !procedure.a(keys[i], (Object)values[i])) {
                    this.b(i);
                    modified = true;
                }
            }
        }
        finally {
            this.a(true);
        }
        return modified;
    }
    
    @Override
    public void a(final k<V, V> function) {
        final byte[] states = this.q;
        final V[] values = (V[])this.u;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.a(values[i]);
            }
        }
    }
    
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof N)) {
            return false;
        }
        final N that = (N)other;
        if (that.c() != this.c()) {
            return false;
        }
        try {
            final A iter = this.i();
            while (iter.hasNext()) {
                iter.a();
                final char key = iter.b();
                final Object value = iter.c();
                if (value == null) {
                    if (that.b(key) != null || !that.a_(key)) {
                        return false;
                    }
                    continue;
                }
                else {
                    if (!value.equals(that.b(key))) {
                        return false;
                    }
                    continue;
                }
            }
        }
        catch (ClassCastException ex) {}
        return true;
    }
    
    @Override
    public int hashCode() {
        int hashcode = 0;
        final V[] values = (V[])this.u;
        final byte[] states = this.q;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                hashcode += (yo.m.a(this.b[i]) ^ ((values[i] == null) ? 0 : values[i].hashCode()));
            }
        }
        return hashcode;
    }
    
    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeChar(this.v);
        out.writeInt(this.h);
        int i = this.q.length;
        while (i-- > 0) {
            if (this.q[i] == 1) {
                out.writeChar(this.b[i]);
                out.writeObject(this.u[i]);
            }
        }
    }
    
    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        this.v = in.readChar();
        int size = in.readInt();
        this.a(size);
        while (size-- > 0) {
            final char key = in.readChar();
            final V val = (V)in.readObject();
            this.a(key, val);
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.a(new ac<V>() {
            private boolean c = true;
            
            @Override
            public boolean a(final char key, final Object value) {
                if (this.c) {
                    this.c = false;
                }
                else {
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
    
    class a implements ao
    {
        @Override
        public char a() {
            return U.this.v;
        }
        
        @Override
        public int b() {
            return U.this.h;
        }
        
        @Override
        public boolean c() {
            return U.this.h == 0;
        }
        
        @Override
        public boolean a(final char entry) {
            return U.this.a_(entry);
        }
        
        @Override
        public z d() {
            return new a(U.this);
        }
        
        @Override
        public char[] e() {
            return U.this.b_();
        }
        
        @Override
        public char[] a(final char[] dest) {
            return U.this.a(dest);
        }
        
        @Override
        public boolean b(final char entry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean c(final char entry) {
            return null != U.this.b_(entry);
        }
        
        @Override
        public boolean a(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!U.this.a_((char)element)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean a(final c collection) {
            if (collection == this) {
                return true;
            }
            final z iter = collection.d();
            while (iter.hasNext()) {
                if (!U.this.a_(iter.a())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean b(final char[] array) {
            for (final char element : array) {
                if (!U.this.a_(element)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean b(final Collection<? extends Character> collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean b(final c collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean c(final char[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean c(final Collection<?> collection) {
            boolean modified = false;
            final z iter = this.d();
            while (iter.hasNext()) {
                if (!collection.contains(iter.a())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        @Override
        public boolean c(final c collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final z iter = this.d();
            while (iter.hasNext()) {
                if (!collection.a(iter.a())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        @Override
        public boolean d(final char[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final char[] set = U.this.b;
            final byte[] states = U.this.q;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    U.this.b(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public boolean d(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Character) {
                    final char c = (char)element;
                    if (!this.c(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public boolean d(final c collection) {
            if (collection == this) {
                this.f();
                return true;
            }
            boolean changed = false;
            final z iter = collection.d();
            while (iter.hasNext()) {
                final char element = iter.a();
                if (this.c(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public boolean e(final char[] array) {
            boolean changed = false;
            int i = array.length;
            while (i-- > 0) {
                if (this.c(array[i])) {
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public void f() {
            U.this.h();
        }
        
        @Override
        public boolean a(final ad procedure) {
            return U.this.a_(procedure);
        }
        
        @Override
        public boolean equals(final Object other) {
            if (!(other instanceof ao)) {
                return false;
            }
            final ao that = (ao)other;
            if (that.b() != this.b()) {
                return false;
            }
            int i = U.this.q.length;
            while (i-- > 0) {
                if (U.this.q[i] == 1 && !that.a(U.this.b[i])) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            int hashcode = 0;
            int i = U.this.q.length;
            while (i-- > 0) {
                if (U.this.q[i] == 1) {
                    hashcode += yo.m.a(U.this.b[i]);
                }
            }
            return hashcode;
        }
        
        @Override
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            boolean first = true;
            int i = U.this.q.length;
            while (i-- > 0) {
                if (U.this.q[i] == 1) {
                    if (first) {
                        first = false;
                    }
                    else {
                        buf.append(",");
                    }
                    buf.append(U.this.b[i]);
                }
            }
            return buf.toString();
        }
        
        class a extends r implements z
        {
            private final o e;
            
            public a(final o hash) {
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
    
    public class d extends b<V>
    {
        @Override
        public Iterator<V> iterator() {
            return new a(U.this) {
                @Override
                protected V a(final int index) {
                    return (V)U.this.u[index];
                }
            };
        }
        
        @Override
        public boolean b(final V value) {
            return U.this.a(value);
        }
        
        @Override
        public boolean a(final V value) {
            final V[] values = (V[])U.this.u;
            final byte[] states = U.this.q;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && (value == values[i] || (null != values[i] && values[i].equals(value)))) {
                    U.this.b(i);
                    return true;
                }
            }
            return false;
        }
        
        class a extends r implements Iterator<V>
        {
            protected final U e;
            
            public a(final U map) {
                super(map);
                this.e = map;
            }
            
            protected V a(final int index) {
                final byte[] states = U.this.q;
                final Object value = this.e.u[index];
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
    
    abstract class b<E> extends AbstractSet<E> implements Iterable<E>, Set<E>
    {
        @Override
        public abstract Iterator<E> iterator();
        
        public abstract boolean a(final E p0);
        
        public abstract boolean b(final E p0);
        
        @Override
        public boolean contains(final Object key) {
            return this.b(key);
        }
        
        @Override
        public boolean remove(final Object o) {
            return this.a(o);
        }
        
        @Override
        public void clear() {
            U.this.h();
        }
        
        @Override
        public boolean add(final E obj) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public int size() {
            return U.this.c();
        }
        
        @Override
        public Object[] toArray() {
            final Object[] result = new Object[this.size()];
            final Iterator<E> e = this.iterator();
            int i = 0;
            while (e.hasNext()) {
                result[i] = e.next();
                ++i;
            }
            return result;
        }
        
        @Override
        public <T> T[] toArray(T[] a) {
            final int size = this.size();
            if (a.length < size) {
                a = (T[])Array.newInstance(a.getClass().getComponentType(), size);
            }
            final Iterator<E> it = this.iterator();
            final Object[] result = a;
            for (int i = 0; i < size; ++i) {
                result[i] = it.next();
            }
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }
        
        @Override
        public boolean isEmpty() {
            return U.this.b();
        }
        
        @Override
        public boolean addAll(final Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean retainAll(final Collection<?> collection) {
            boolean changed = false;
            final Iterator<E> i = this.iterator();
            while (i.hasNext()) {
                if (!collection.contains(i.next())) {
                    i.remove();
                    changed = true;
                }
            }
            return changed;
        }
    }
    
    class c<V> extends r implements A<V>
    {
        private final U<V> e;
        
        public c(final U<V> map) {
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
        public V a(final V val) {
            final V old = this.c();
            this.e.u[this.c] = val;
            return old;
        }
    }
}

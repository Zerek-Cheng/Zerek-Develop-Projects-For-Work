// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.ConcurrentModificationException;
import java.util.Collection;
import java.io.ObjectInput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.io.Externalizable;

public class V extends s implements Externalizable, O
{
    static final long u = 1L;
    protected transient byte[] v;
    
    public V() {
    }
    
    public V(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public V(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public V(final int initialCapacity, final float loadFactor, final int noEntryKey, final byte noEntryValue) {
        super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
    }
    
    public V(final int[] keys, final byte[] values) {
        super(Math.max(keys.length, values.length));
        for (int size = Math.min(keys.length, values.length), i = 0; i < size; ++i) {
            this.a(keys[i], values[i]);
        }
    }
    
    public V(final O map) {
        super(map.c());
        if (map instanceof V) {
            final V hashmap = (V)map;
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
    protected int a(final int initialCapacity) {
        final int capacity = super.a(initialCapacity);
        this.v = new byte[capacity];
        return capacity;
    }
    
    @Override
    protected void d(final int newCapacity) {
        final int oldCapacity = this.b.length;
        final int[] oldKeys = this.b;
        final byte[] oldVals = this.v;
        final byte[] oldStates = this.q;
        this.b = new int[newCapacity];
        this.v = new byte[newCapacity];
        this.q = new byte[newCapacity];
        int i = oldCapacity;
        while (i-- > 0) {
            if (oldStates[i] == 1) {
                final int o = oldKeys[i];
                final int index = this.i(o);
                this.v[index] = oldVals[i];
            }
        }
    }
    
    @Override
    public byte a(final int key, final byte value) {
        final int index = this.i(key);
        return this.a(key, value, index);
    }
    
    @Override
    public byte b(final int key, final byte value) {
        final int index = this.i(key);
        if (index < 0) {
            return this.v[-index - 1];
        }
        return this.a(key, value, index);
    }
    
    private byte a(final int key, final byte value, int index) {
        byte previous = this.d;
        boolean isNewMapping = true;
        if (index < 0) {
            index = -index - 1;
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
    public void a(final Map<? extends Integer, ? extends Byte> map) {
        this.l_(map.size());
        for (final Map.Entry<? extends Integer, ? extends Byte> entry : map.entrySet()) {
            this.a((int)entry.getKey(), (byte)entry.getValue());
        }
    }
    
    @Override
    public void a(final O map) {
        this.l_(map.c());
        final C iter = map.i();
        while (iter.hasNext()) {
            iter.a();
            this.a(iter.b(), iter.c());
        }
    }
    
    @Override
    public byte a_(final int key) {
        final int index = this.h(key);
        return (index < 0) ? this.d : this.v[index];
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
    public byte b_(final int key) {
        byte prev = this.d;
        final int index = this.h(key);
        if (index >= 0) {
            prev = this.v[index];
            this.b(index);
        }
        return prev;
    }
    
    @Override
    protected void b(final int index) {
        this.v[index] = this.d;
        super.b(index);
    }
    
    @Override
    public ap i_() {
        return new d();
    }
    
    @Override
    public int[] j_() {
        final int[] keys = new int[this.c()];
        final int[] k = this.b;
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
    public int[] a(int[] array) {
        final int size = this.c();
        if (array.length < size) {
            array = new int[size];
        }
        final int[] keys = this.b;
        final byte[] states = this.q;
        int i = keys.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                array[j++] = keys[i];
            }
        }
        return array;
    }
    
    @Override
    public yo.b k_() {
        return new e();
    }
    
    @Override
    public byte[] l_() {
        final byte[] vals = new byte[this.c()];
        final byte[] v = this.v;
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
    public byte[] a(byte[] array) {
        final int size = this.c();
        if (array.length < size) {
            array = new byte[size];
        }
        final byte[] v = this.v;
        final byte[] states = this.q;
        int i = v.length;
        int j = 0;
        while (i-- > 0) {
            if (states[i] == 1) {
                array[j++] = v[i];
            }
        }
        return array;
    }
    
    @Override
    public boolean a(final byte val) {
        final byte[] states = this.q;
        final byte[] vals = this.v;
        int i = vals.length;
        while (i-- > 0) {
            if (states[i] == 1 && val == vals[i]) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean c_(final int key) {
        return this.g(key);
    }
    
    @Override
    public C i() {
        return new a(this);
    }
    
    @Override
    public boolean a_(final ai procedure) {
        return this.a(procedure);
    }
    
    @Override
    public boolean a(final ab procedure) {
        final byte[] states = this.q;
        final byte[] values = this.v;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.a(values[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean a(final af procedure) {
        final byte[] states = this.q;
        final int[] keys = this.b;
        final byte[] values = this.v;
        int i = keys.length;
        while (i-- > 0) {
            if (states[i] == 1 && !procedure.a(keys[i], values[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void a(final g function) {
        final byte[] states = this.q;
        final byte[] values = this.v;
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                values[i] = function.a(values[i]);
            }
        }
    }
    
    @Override
    public boolean b(final af procedure) {
        boolean modified = false;
        final byte[] states = this.q;
        final int[] keys = this.b;
        final byte[] values = this.v;
        this.f_();
        try {
            int i = keys.length;
            while (i-- > 0) {
                if (states[i] == 1 && !procedure.a(keys[i], values[i])) {
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
    public boolean d_(final int key) {
        return this.c(key, (byte)1);
    }
    
    @Override
    public boolean c(final int key, final byte amount) {
        final int index = this.h(key);
        if (index < 0) {
            return false;
        }
        final byte[] v = this.v;
        final int n = index;
        v[n] += amount;
        return true;
    }
    
    @Override
    public byte a(final int key, final byte adjust_amount, final byte put_amount) {
        int index = this.i(key);
        byte newValue;
        boolean isNewMapping;
        if (index < 0) {
            index = -index - 1;
            final byte[] v = this.v;
            final int n = index;
            final byte b = (byte)(v[n] + adjust_amount);
            v[n] = b;
            newValue = b;
            isNewMapping = false;
        }
        else {
            this.v[index] = put_amount;
            newValue = put_amount;
            isNewMapping = true;
        }
        final byte previousState = this.q[index];
        if (isNewMapping) {
            this.b(this.o);
        }
        return newValue;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof O)) {
            return false;
        }
        final O that = (O)other;
        if (that.c() != this.c()) {
            return false;
        }
        final byte[] values = this.v;
        final byte[] states = this.q;
        final byte this_no_entry_value = this.k();
        final byte that_no_entry_value = that.k();
        int i = values.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                final int key = this.b[i];
                final byte that_value = that.a_(key);
                final byte this_value = values[i];
                if (this_value != that_value && this_value != this_no_entry_value && that_value != that_no_entry_value) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hashcode = 0;
        final byte[] states = this.q;
        int i = this.v.length;
        while (i-- > 0) {
            if (states[i] == 1) {
                hashcode += (yo.m.a(this.b[i]) ^ yo.m.a(this.v[i]));
            }
        }
        return hashcode;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder("{");
        this.a(new af() {
            private boolean c = true;
            
            @Override
            public boolean a(final int key, final byte value) {
                if (this.c) {
                    this.c = false;
                }
                else {
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
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
        out.writeInt(this.h);
        int i = this.q.length;
        while (i-- > 0) {
            if (this.q[i] == 1) {
                out.writeInt(this.b[i]);
                out.writeByte(this.v[i]);
            }
        }
    }
    
    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
        int size = in.readInt();
        this.a(size);
        while (size-- > 0) {
            final int key = in.readInt();
            final byte val = in.readByte();
            this.a(key, val);
        }
    }
    
    public class d implements ap
    {
        @Override
        public E d() {
            return new b(V.this);
        }
        
        @Override
        public int a() {
            return V.this.c;
        }
        
        @Override
        public int b() {
            return V.this.h;
        }
        
        @Override
        public boolean c() {
            return 0 == V.this.h;
        }
        
        @Override
        public boolean a(final int entry) {
            return V.this.g(entry);
        }
        
        @Override
        public int[] e() {
            return V.this.j_();
        }
        
        @Override
        public int[] a(final int[] dest) {
            return V.this.a(dest);
        }
        
        @Override
        public boolean b(final int entry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean c(final int entry) {
            return V.this.d != V.this.b_(entry);
        }
        
        @Override
        public boolean a(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Integer)) {
                    return false;
                }
                final int ele = (int)element;
                if (!V.this.c_(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean a(final e collection) {
            final E iter = collection.d();
            while (iter.hasNext()) {
                if (!V.this.c_(iter.a())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean b(final int[] array) {
            for (final int element : array) {
                if (!V.this.g(element)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean b(final Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean b(final e collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean c(final int[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean c(final Collection<?> collection) {
            boolean modified = false;
            final E iter = this.d();
            while (iter.hasNext()) {
                if (!collection.contains(iter.a())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        @Override
        public boolean c(final e collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final E iter = this.d();
            while (iter.hasNext()) {
                if (!collection.a(iter.a())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        @Override
        public boolean d(final int[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final int[] set = V.this.b;
            final byte[] states = V.this.q;
            int i = set.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, set[i]) < 0) {
                    V.this.b(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public boolean d(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Integer) {
                    final int c = (int)element;
                    if (!this.c(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public boolean d(final e collection) {
            if (this == collection) {
                this.f();
                return true;
            }
            boolean changed = false;
            final E iter = collection.d();
            while (iter.hasNext()) {
                final int element = iter.a();
                if (this.c(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public boolean e(final int[] array) {
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
            V.this.h();
        }
        
        @Override
        public boolean a(final ai procedure) {
            return V.this.a_(procedure);
        }
        
        @Override
        public boolean equals(final Object other) {
            if (!(other instanceof ap)) {
                return false;
            }
            final ap that = (ap)other;
            if (that.b() != this.b()) {
                return false;
            }
            int i = V.this.q.length;
            while (i-- > 0) {
                if (V.this.q[i] == 1 && !that.a(V.this.b[i])) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            int hashcode = 0;
            int i = V.this.q.length;
            while (i-- > 0) {
                if (V.this.q[i] == 1) {
                    hashcode += yo.m.a(V.this.b[i]);
                }
            }
            return hashcode;
        }
        
        @Override
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            V.this.a_(new ai() {
                private boolean c = true;
                
                @Override
                public boolean a(final int key) {
                    if (this.c) {
                        this.c = false;
                    }
                    else {
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
    
    public class e implements b
    {
        @Override
        public y d() {
            return new c(V.this);
        }
        
        @Override
        public byte a() {
            return V.this.d;
        }
        
        @Override
        public int b() {
            return V.this.h;
        }
        
        @Override
        public boolean c() {
            return 0 == V.this.h;
        }
        
        @Override
        public boolean a(final byte entry) {
            return V.this.a(entry);
        }
        
        @Override
        public byte[] e() {
            return V.this.l_();
        }
        
        @Override
        public byte[] a(final byte[] dest) {
            return V.this.a(dest);
        }
        
        @Override
        public boolean b(final byte entry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean c(final byte entry) {
            final byte[] values = V.this.v;
            final int[] set = V.this.b;
            int i = values.length;
            while (i-- > 0) {
                if (set[i] != 0 && set[i] != 2 && entry == values[i]) {
                    V.this.b(i);
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean a(final Collection<?> collection) {
            for (final Object element : collection) {
                if (!(element instanceof Byte)) {
                    return false;
                }
                final byte ele = (byte)element;
                if (!V.this.a(ele)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean a(final b collection) {
            final y iter = collection.d();
            while (iter.hasNext()) {
                if (!V.this.a(iter.a())) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean b(final byte[] array) {
            for (final byte element : array) {
                if (!V.this.a(element)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean b(final Collection<? extends Byte> collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean b(final b collection) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean c(final byte[] array) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean c(final Collection<?> collection) {
            boolean modified = false;
            final y iter = this.d();
            while (iter.hasNext()) {
                if (!collection.contains(iter.a())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        @Override
        public boolean c(final b collection) {
            if (this == collection) {
                return false;
            }
            boolean modified = false;
            final y iter = this.d();
            while (iter.hasNext()) {
                if (!collection.a(iter.a())) {
                    iter.remove();
                    modified = true;
                }
            }
            return modified;
        }
        
        @Override
        public boolean d(final byte[] array) {
            boolean changed = false;
            Arrays.sort(array);
            final byte[] values = V.this.v;
            final byte[] states = V.this.q;
            int i = values.length;
            while (i-- > 0) {
                if (states[i] == 1 && Arrays.binarySearch(array, values[i]) < 0) {
                    V.this.b(i);
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public boolean d(final Collection<?> collection) {
            boolean changed = false;
            for (final Object element : collection) {
                if (element instanceof Byte) {
                    final byte c = (byte)element;
                    if (!this.c(c)) {
                        continue;
                    }
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public boolean d(final b collection) {
            if (this == collection) {
                this.f();
                return true;
            }
            boolean changed = false;
            final y iter = collection.d();
            while (iter.hasNext()) {
                final byte element = iter.a();
                if (this.c(element)) {
                    changed = true;
                }
            }
            return changed;
        }
        
        @Override
        public boolean e(final byte[] array) {
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
            V.this.h();
        }
        
        @Override
        public boolean a(final ab procedure) {
            return V.this.a(procedure);
        }
        
        @Override
        public String toString() {
            final StringBuilder buf = new StringBuilder("{");
            V.this.a(new ab() {
                private boolean c = true;
                
                @Override
                public boolean a(final byte value) {
                    if (this.c) {
                        this.c = false;
                    }
                    else {
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
    
    class b extends r implements E
    {
        b(final w hash) {
            super(hash);
        }
        
        @Override
        public int a() {
            this.h_();
            return V.this.b[this.c];
        }
        
        @Override
        public void remove() {
            if (this.b != this.a.c()) {
                throw new ConcurrentModificationException();
            }
            try {
                this.a.f_();
                V.this.b(this.c);
            }
            finally {
                this.a.a(false);
            }
            --this.b;
        }
    }
    
    class c extends r implements y
    {
        c(final w hash) {
            super(hash);
        }
        
        @Override
        public byte a() {
            this.h_();
            return V.this.v[this.c];
        }
        
        @Override
        public void remove() {
            if (this.b != this.a.c()) {
                throw new ConcurrentModificationException();
            }
            try {
                this.a.f_();
                V.this.b(this.c);
            }
            finally {
                this.a.a(false);
            }
            --this.b;
        }
    }
    
    class a extends r implements C
    {
        a(final V map) {
            super(map);
        }
        
        @Override
        public void a() {
            this.h_();
        }
        
        @Override
        public int b() {
            return V.this.b[this.c];
        }
        
        @Override
        public byte c() {
            return V.this.v[this.c];
        }
        
        @Override
        public byte a(final byte val) {
            final byte old = this.c();
            V.this.v[this.c] = val;
            return old;
        }
        
        @Override
        public void remove() {
            if (this.b != this.a.c()) {
                throw new ConcurrentModificationException();
            }
            try {
                this.a.f_();
                V.this.b(this.c);
            }
            finally {
                this.a.a(false);
            }
            --this.b;
        }
    }
}

/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import yo.an_1;
import yo.p_1;

public abstract class v_0<T>
extends p_1 {
    static final long a = -3461112548087185871L;
    public transient Object[] b;
    public static final Object c = new Object();
    public static final Object d = new Object();
    protected boolean o;

    public v_0() {
    }

    public v_0(int initialCapacity) {
        super(initialCapacity);
    }

    public v_0(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public int d() {
        return this.b.length;
    }

    @Override
    protected void b(int index) {
        this.b[index] = c;
        super.b(index);
    }

    @Override
    public int a(int initialCapacity) {
        int capacity = super.a(initialCapacity);
        this.b = new Object[capacity];
        Arrays.fill(this.b, d);
        return capacity;
    }

    public boolean a(an_1<? super T> procedure) {
        Object[] set = this.b;
        int i = set.length;
        while (i-- > 0) {
            if (set[i] == d || set[i] == c || procedure.a(set[i])) continue;
            return false;
        }
        return true;
    }

    public boolean a(Object obj) {
        return this.c_(obj) >= 0;
    }

    protected int c_(Object obj) {
        if (obj == null) {
            return this.a();
        }
        int hash = this.e(obj) & Integer.MAX_VALUE;
        int index = hash % this.b.length;
        Object cur = this.b[index];
        if (cur == d) {
            return -1;
        }
        if (cur == obj || this.b(obj, cur)) {
            return index;
        }
        return this.a(obj, index, hash, cur);
    }

    private int a(Object obj, int index, int hash, Object cur) {
        Object[] set = this.b;
        int length = set.length;
        int probe = 1 + hash % (length - 2);
        int loopIndex = index;
        do {
            if ((index -= probe) < 0) {
                index += length;
            }
            if ((cur = set[index]) == d) {
                return -1;
            }
            if (cur != obj && !this.b(obj, cur)) continue;
            return index;
        } while (index != loopIndex);
        return -1;
    }

    private int a() {
        int index = 0;
        for (Object o : this.b) {
            if (o == null) {
                return index;
            }
            if (o == d) {
                return -1;
            }
            ++index;
        }
        return -1;
    }

    @Deprecated
    protected int c(T obj) {
        return this.d_(obj);
    }

    protected int d_(T key) {
        this.o = false;
        if (key == null) {
            return this.k();
        }
        int hash = this.e(key) & Integer.MAX_VALUE;
        int index = hash % this.b.length;
        Object cur = this.b[index];
        if (cur == d) {
            this.o = true;
            this.b[index] = key;
            return index;
        }
        if (cur == key || this.b(key, cur)) {
            return - index - 1;
        }
        return this.b(key, index, hash, cur);
    }

    private int b(T key, int index, int hash, Object cur) {
        Object[] set = this.b;
        int length = set.length;
        int probe = 1 + hash % (length - 2);
        int loopIndex = index;
        int firstRemoved = -1;
        do {
            if (cur == c && firstRemoved == -1) {
                firstRemoved = index;
            }
            if ((index -= probe) < 0) {
                index += length;
            }
            if ((cur = set[index]) == d) {
                if (firstRemoved != -1) {
                    this.b[firstRemoved] = key;
                    return firstRemoved;
                }
                this.o = true;
                this.b[index] = key;
                return index;
            }
            if (cur != key && !this.b(key, cur)) continue;
            return - index - 1;
        } while (index != loopIndex);
        if (firstRemoved != -1) {
            this.b[firstRemoved] = key;
            return firstRemoved;
        }
        throw new IllegalStateException("No free or removed slots available. Key set full?!!");
    }

    private int k() {
        int index = 0;
        int firstRemoved = -1;
        for (Object o : this.b) {
            if (o == c && firstRemoved == -1) {
                firstRemoved = index;
            }
            if (o == d) {
                if (firstRemoved != -1) {
                    this.b[firstRemoved] = null;
                    return firstRemoved;
                }
                this.o = true;
                this.b[index] = null;
                return index;
            }
            if (o == null) {
                return - index - 1;
            }
            ++index;
        }
        if (firstRemoved != -1) {
            this.b[firstRemoved] = null;
            return firstRemoved;
        }
        throw new IllegalStateException("Could not find insertion index for null key. Key set full!?!!");
    }

    protected final void a(Object o1, Object o2) throws IllegalArgumentException {
        throw this.a(o1, o2, "");
    }

    protected final void a(Object o1, Object o2, int size, int oldSize, Object[] oldKeys) throws IllegalArgumentException {
        String extra = this.b(o1, o2, this.c(), oldSize, oldKeys);
        throw this.a(o1, o2, extra);
    }

    protected final IllegalArgumentException a(Object o1, Object o2, String extra) {
        return new IllegalArgumentException("Equal objects must have equal hashcodes. During rehashing, Trove discovered that the following two objects claim to be equal (as in java.lang.Object.equals()) but their hashCodes (or those calculated by your TObjectHashingStrategy) are not equal.This violates the general contract of java.lang.Object.hashCode().  See bullet point two in that method's documentation. object #1 =" + v_0.f(o1) + "; object #2 =" + v_0.f(o2) + "\n" + extra);
    }

    protected boolean b(Object notnull, Object two) {
        if (two == null || two == c) {
            return false;
        }
        return notnull.equals(two);
    }

    protected int e(Object notnull) {
        return notnull.hashCode();
    }

    protected static String a(int newSize, int oldSize) {
        if (newSize != oldSize) {
            return "[Warning] apparent concurrent modification of the key set. Size before and after rehash() do not match " + oldSize + " vs " + newSize;
        }
        return "";
    }

    protected String b(Object newVal, Object oldVal, int currentSize, int oldSize, Object[] oldKeys) {
        StringBuilder b2 = new StringBuilder();
        b2.append(this.d(newVal, oldVal));
        b2.append(v_0.a(currentSize, oldSize));
        b2.append(v_0.a(oldKeys, oldSize));
        if (newVal == oldVal) {
            b2.append("Inserting same object twice, rehashing bug. Object= ").append(oldVal);
        }
        return b2.toString();
    }

    private static String a(Object[] keys, int oldSize) {
        StringBuilder buf = new StringBuilder();
        Set<Object> k = v_0.a(keys);
        if (k.size() != oldSize) {
            buf.append("\nhashCode() and/or equals() have inconsistent implementation");
            buf.append("\nKey set lost entries, now got ").append(k.size()).append(" instead of ").append(oldSize);
            buf.append(". This can manifest itself as an apparent duplicate key.");
        }
        return buf.toString();
    }

    private static Set<Object> a(Object[] keys) {
        HashSet<Object> types = new HashSet<Object>();
        for (Object o : keys) {
            if (o == d || o == c) continue;
            types.add(o);
        }
        return types;
    }

    private static String c(Object a2, Object b2) {
        StringBuilder buf = new StringBuilder();
        if (a2 == b2) {
            return "a == b";
        }
        if (a2.getClass() != b2.getClass()) {
            buf.append("Class of objects differ a=").append(a2.getClass()).append(" vs b=").append(b2.getClass());
            boolean aEb = a2.equals(b2);
            boolean bEa = b2.equals(a2);
            if (aEb != bEa) {
                buf.append("\nequals() of a or b object are asymmetric");
                buf.append("\na.equals(b) =").append(aEb);
                buf.append("\nb.equals(a) =").append(bEa);
            }
        }
        return buf.toString();
    }

    protected static String f(Object o) {
        return (o == null ? "class null" : o.getClass()) + " id= " + System.identityHashCode(o) + " hashCode= " + (o == null ? 0 : o.hashCode()) + " toString= " + String.valueOf(o);
    }

    private String d(Object newVal, Object oldVal) {
        StringBuilder buf = new StringBuilder();
        HashSet types = new HashSet();
        for (Object o : this.b) {
            if (o == d || o == c) continue;
            if (o != null) {
                types.add(o.getClass());
                continue;
            }
            types.add(null);
        }
        if (types.size() > 1) {
            buf.append("\nMore than one type used for keys. Watch out for asymmetric equals(). Read about the 'Liskov substitution principle' and the implications for equals() in java.");
            buf.append("\nKey types: ").append(types);
            buf.append(v_0.c(newVal, oldVal));
        }
        return buf.toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(0);
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readByte();
        super.readExternal(in);
    }
}


/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;

public class bq_0<T>
implements Comparator<T> {
    boolean a;
    Collator b = Collator.getInstance();

    public bq_0() {
        this(false);
    }

    public bq_0(boolean ignoreCase) {
        this.a = ignoreCase;
    }

    @Override
    public int compare(Object element1, Object element2) {
        CollationKey key1;
        CollationKey key2;
        if (this.a) {
            key1 = this.b.getCollationKey(element1.toString().toLowerCase());
            key2 = this.b.getCollationKey(element2.toString().toLowerCase());
        } else {
            key1 = this.b.getCollationKey(element1.toString());
            key2 = this.b.getCollationKey(element2.toString());
        }
        return key1.compareTo(key2);
    }
}


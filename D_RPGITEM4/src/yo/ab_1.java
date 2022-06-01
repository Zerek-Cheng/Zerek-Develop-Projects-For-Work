/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.lang.reflect.Method;
import yo.aa_1;
import yo.ad_0;
import yo.ai_1;

class ab_1
implements Comparable<ab_1> {
    public boolean a;
    public String b;
    public String c;
    public ad_0 d;
    public Method e;
    public aa_1[] f;
    public String g;
    public String h;
    public ai_1.a i;

    ab_1() {
    }

    public int a(ab_1 o) {
        return this.h.compareToIgnoreCase(o.h);
    }

    @Override
    public /* synthetic */ int compareTo(Object x0) {
        return this.a((ab_1)x0);
    }
}


/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.ArrayList;
import java.util.List;
import yo.aa_1;

class aq_1
extends aa_1 {
    public String a;

    public aq_1(String v) {
        this.a = v;
    }

    @Override
    public void a(String a2) {
        throw new RuntimeException("Const cannot be init'ed");
    }

    @Override
    public Object a(String in, String locale) {
        return null;
    }

    @Override
    public List<String> b(String in) {
        ArrayList<String> a2 = new ArrayList<String>();
        String lValue = this.a;
        if (lValue.startsWith(in)) {
            a2.add(lValue);
        }
        return a2;
    }

    @Override
    public String c(String locale) {
        return this.a;
    }

    @Override
    public boolean a() {
        return true;
    }

    @Override
    public Class<?> b() {
        return null;
    }
}


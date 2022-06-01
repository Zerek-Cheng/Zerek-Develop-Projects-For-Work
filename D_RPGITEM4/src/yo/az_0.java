/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.ArrayList;
import java.util.List;
import yo.aa_1;
import yo.ac_1;
import yo.ao_0;

class az_0
extends aa_1 {
    private int a;

    az_0() {
    }

    @Override
    public void a(String a2) {
        this.a = a2.length() == 0 ? 0 : Integer.parseInt(a2);
    }

    @Override
    public Object a(String in, String locale) {
        if (this.a != 0 && in.length() > this.a) {
            return new ac_1(String.format(ao_0.a("message.error.string.length", locale), in, this.a));
        }
        return in;
    }

    @Override
    public List<String> b(String in) {
        return new ArrayList<String>();
    }

    @Override
    public String c(String locale) {
        if (this.a != 0) {
            return String.format(ao_0.a("command.info.string.limit", locale), this.a);
        }
        return ao_0.a("command.info.string", locale);
    }

    @Override
    public Class<?> b() {
        return String.class;
    }
}


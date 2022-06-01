/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import yo.aa_1;
import yo.ac_1;
import yo.ao_0;
import yo.ap_1;

class as_1
extends aa_1 {
    Class<?> a;
    List<?> b;
    boolean c = false;
    String d;
    String e;

    as_1() {
    }

    @Override
    public void a(String a2) {
        try {
            String[] sp = a2.split("#");
            if (sp.length > 1) {
                this.d = sp[0].isEmpty() ? sp[1] : sp[0];
                this.e = sp[1];
            } else {
                this.d = "";
                this.e = a2;
            }
            this.a = ap_1.getClassByName(this.e);
            if (!this.a.isEnum()) {
                throw new RuntimeException(a2 + " is not an enum");
            }
            if (this.d.isEmpty()) {
                this.d = this.a.getSimpleName();
            } else {
                this.d = this.d.toLowerCase();
                this.c = true;
            }
            this.b = Arrays.asList(this.a.getEnumConstants());
        }
        catch (ClassNotFoundException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override
    public Object a(String in, String locale) {
        Object en = null;
        try {
            en = Enum.valueOf(this.a, in.toUpperCase());
        }
        catch (IllegalArgumentException ex) {
            return new ac_1(String.format(ao_0.a("message.error.enum.format", locale), in, this.a.getSimpleName(), this.b.toString().toLowerCase()));
        }
        return en;
    }

    @Override
    public List<String> b(String in) {
        ArrayList<String> out = new ArrayList<String>();
        String it = in.toUpperCase();
        for (Object en : this.b) {
            if (!en.toString().startsWith(it)) continue;
            out.add(en.toString());
        }
        return out;
    }

    @Override
    public String c(String locale) {
        return "[" + (this.c ? ao_0.a(new StringBuilder().append("command.info.").append(this.d).toString(), locale) : this.d) + "]";
    }

    @Override
    public Class<?> b() {
        return this.a;
    }
}


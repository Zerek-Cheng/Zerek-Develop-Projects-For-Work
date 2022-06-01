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

class ax_0
extends aa_1 {
    private List<String> a;
    private String b = "";

    ax_0() {
    }

    @Override
    public void a(String a2) {
        if (a2.contains("@")) {
            String[] args = a2.split("@");
            this.b = args[0];
            a2 = args[1];
        }
        String[] options = a2.split(",");
        for (int i = 0; i < options.length; ++i) {
            options[i] = options[i].trim();
        }
        this.a = Arrays.asList(options);
    }

    @Override
    public Object a(String in, String locale) {
        for (String o : this.a) {
            if (!o.equalsIgnoreCase(in)) continue;
            return o;
        }
        return new ac_1(String.format(ao_0.a("message.error.option", locale), in, this.a.toString()));
    }

    @Override
    public List<String> b(String in) {
        ArrayList<String> out = new ArrayList<String>();
        in = in.toLowerCase();
        for (String o : this.a) {
            if (!o.startsWith(in)) continue;
            out.add(o);
        }
        return out;
    }

    @Override
    public String c(String locale) {
        if (this.b.length() == 0) {
            return this.a.toString().replaceAll(", ", ",");
        }
        return "[" + this.b + "]";
    }

    @Override
    public Class<?> b() {
        return String.class;
    }
}


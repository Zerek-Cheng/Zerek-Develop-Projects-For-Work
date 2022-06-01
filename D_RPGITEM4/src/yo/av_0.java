/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.ArrayList;
import java.util.List;
import yo.aa_1;
import yo.ac_1;
import yo.ao_0;

class av_0
extends aa_1 {
    private boolean a;
    private long b;
    private long c;

    av_0() {
    }

    @Override
    public void a(String a2) {
        if (a2.length() == 0) {
            this.a = false;
        } else {
            this.a = true;
            String[] args = a2.split(",");
            if (args.length != 2) {
                throw new RuntimeException("ArgumentLong limits errror");
            }
            this.b = Long.parseLong(args[0]);
            this.c = Long.parseLong(args[1]);
        }
    }

    @Override
    public Object a(String in, String locale) {
        if (this.a) {
            try {
                long i = Long.parseLong(in);
                if (i < this.b || i > this.c) {
                    return new ac_1(String.format(ao_0.a("message.error.long.limit", locale), this.b, this.c));
                }
                return i;
            }
            catch (NumberFormatException e2) {
                return new ac_1(String.format(ao_0.a("message.error.long.format", locale), in));
            }
        }
        try {
            long i = Long.parseLong(in);
            return i;
        }
        catch (NumberFormatException e3) {
            return new ac_1(String.format(ao_0.a("message.error.long.format", locale), in));
        }
    }

    @Override
    public List<String> b(String in) {
        return new ArrayList<String>();
    }

    @Override
    public String c(String locale) {
        if (this.a) {
            return String.format(ao_0.a("command.info.long.limit", locale), this.b, this.c);
        }
        return ao_0.a("command.info.long", locale);
    }

    @Override
    public Class<?> b() {
        return Long.TYPE;
    }
}


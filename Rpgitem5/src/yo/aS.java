// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class as extends aA
{
    Class<?> a;
    List<?> b;
    boolean c;
    String d;
    String e;
    
    as() {
        this.c = false;
    }
    
    @Override
    public void a(final String a) {
        try {
            final String[] sp = a.split("#");
            if (sp.length > 1) {
                if (sp[0].isEmpty()) {
                    this.d = sp[1];
                }
                else {
                    this.d = sp[0];
                }
                this.e = sp[1];
            }
            else {
                this.d = "";
                this.e = a;
            }
            this.a = (Class<?>)aP.getClassByName(this.e);
            if (!this.a.isEnum()) {
                throw new RuntimeException(a + " is not an enum");
            }
            if (this.d.isEmpty()) {
                this.d = this.a.getSimpleName();
            }
            else {
                this.d = this.d.toLowerCase();
                this.c = true;
            }
            this.b = Arrays.asList(this.a.getEnumConstants());
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Object a(final String in, final String locale) {
        Enum<?> en = null;
        try {
            en = Enum.valueOf(this.a, in.toUpperCase());
        }
        catch (IllegalArgumentException ex) {
            return new aC(String.format(aO.a("message.error.enum.format", locale), in, this.a.getSimpleName(), this.b.toString().toLowerCase()));
        }
        return en;
    }
    
    @Override
    public List<String> b(final String in) {
        final ArrayList<String> out = new ArrayList<String>();
        final String it = in.toUpperCase();
        for (final Object en : this.b) {
            if (en.toString().startsWith(it)) {
                out.add(en.toString());
            }
        }
        return out;
    }
    
    @Override
    public String c(final String locale) {
        return "[" + (this.c ? aO.a("command.info." + this.d, locale) : this.d) + "]";
    }
    
    @Override
    public Class<?> b() {
        return this.a;
    }
}

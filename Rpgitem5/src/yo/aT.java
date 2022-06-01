// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.ArrayList;
import java.util.List;

class at extends aA
{
    private boolean a;
    private int b;
    private int c;
    
    @Override
    public void a(final String a) {
        if (a.length() == 0) {
            this.a = false;
        }
        else {
            this.a = true;
            final String[] args = a.split(",");
            if (args.length != 2) {
                throw new RuntimeException("ArgumentInteger limits errror");
            }
            this.b = Integer.parseInt(args[0]);
            this.c = Integer.parseInt(args[1]);
        }
    }
    
    @Override
    public Object a(final String in, final String locale) {
        if (this.a) {
            try {
                final int i = Integer.parseInt(in);
                if (i < this.b || i > this.c) {
                    return new aC(String.format(aO.a("message.error.integer.limit", locale), this.b, this.c));
                }
                return i;
            }
            catch (NumberFormatException e) {
                return new aC(String.format(aO.a("message.error.integer.format", locale), in));
            }
        }
        try {
            final int i = Integer.parseInt(in);
            return i;
        }
        catch (NumberFormatException e) {
            return new aC(String.format(aO.a("message.error.integer.format", locale), in));
        }
    }
    
    @Override
    public List<String> b(final String in) {
        return new ArrayList<String>();
    }
    
    @Override
    public String c(final String locale) {
        if (this.a) {
            return String.format(aO.a("command.info.integer.limit", locale), this.b, this.c);
        }
        return aO.a("command.info.integer", locale);
    }
    
    @Override
    public Class<?> b() {
        return Integer.TYPE;
    }
}

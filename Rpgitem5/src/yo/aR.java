// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.ArrayList;
import java.util.List;

class ar extends aA
{
    private boolean a;
    private double b;
    private double c;
    
    @Override
    public void a(final String a) {
        if (a.length() == 0) {
            this.a = false;
        }
        else {
            this.a = true;
            final String[] args = a.split(",");
            if (args.length != 2) {
                throw new RuntimeException("ArgumentDouble limits errror");
            }
            this.b = Double.parseDouble(args[0]);
            this.c = Double.parseDouble(args[1]);
        }
    }
    
    @Override
    public Object a(final String in, final String locale) {
        if (this.a) {
            try {
                final double i = Double.parseDouble(in);
                if (i < this.b || i > this.c) {
                    return new aC(String.format(aO.a("message.error.double.limit", locale), this.b, this.c));
                }
                return i;
            }
            catch (NumberFormatException e) {
                return new aC(String.format(aO.a("message.error.double.format", locale), in));
            }
        }
        try {
            final double i = Double.parseDouble(in);
            return i;
        }
        catch (NumberFormatException e) {
            return new aC(String.format(aO.a("message.error.double.format", locale), in));
        }
    }
    
    @Override
    public List<String> b(final String in) {
        return new ArrayList<String>();
    }
    
    @Override
    public String c(final String locale) {
        if (this.a) {
            return String.format(aO.a("command.info.double.limit", locale), this.b, this.c);
        }
        return aO.a("command.info.double", locale);
    }
    
    @Override
    public Class<?> b() {
        return Double.TYPE;
    }
}

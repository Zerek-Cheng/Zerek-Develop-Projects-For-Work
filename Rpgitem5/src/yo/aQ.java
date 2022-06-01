// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.ArrayList;
import java.util.List;

class aq extends aA
{
    public String a;
    
    public aq(final String v) {
        this.a = v;
    }
    
    @Override
    public void a(final String a) {
        throw new RuntimeException("Const cannot be init'ed");
    }
    
    @Override
    public Object a(final String in, final String locale) {
        return null;
    }
    
    @Override
    public List<String> b(final String in) {
        final ArrayList<String> a = new ArrayList<String>();
        final String lValue = this.a;
        if (lValue.startsWith(in)) {
            a.add(lValue);
        }
        return a;
    }
    
    @Override
    public String c(final String locale) {
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

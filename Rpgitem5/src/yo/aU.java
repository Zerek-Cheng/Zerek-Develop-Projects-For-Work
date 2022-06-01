// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import think.rpgitems.item.RPGItem;

class au extends aA
{
    @Override
    public void a(final String a) {
    }
    
    @Override
    public Object a(String in, final String locale) {
        in = in.toLowerCase();
        final RPGItem item = by.c(in);
        if (item == null) {
            return new aC(String.format(aO.a("message.error.item", locale), in));
        }
        return item;
    }
    
    @Override
    public List<String> b(String in) {
        in = in.toLowerCase();
        final ArrayList<String> out = new ArrayList<String>();
        for (final String i : by.b.keySet()) {
            if (i.startsWith(in)) {
                out.add(i);
            }
        }
        return out;
    }
    
    @Override
    public String c(final String locale) {
        return aO.a("command.info.item", locale);
    }
    
    @Override
    public Class<?> b() {
        return RPGItem.class;
    }
}

/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import think.rpgitems.item.RPGItem;
import yo.aa_1;
import yo.ac_1;
import yo.ao_0;
import yo.by_0;

class au_1
extends aa_1 {
    au_1() {
    }

    @Override
    public void a(String a2) {
    }

    @Override
    public Object a(String in, String locale) {
        RPGItem item = by_0.c(in = in.toLowerCase());
        if (item == null) {
            return new ac_1(String.format(ao_0.a("message.error.item", locale), in));
        }
        return item;
    }

    @Override
    public List<String> b(String in) {
        in = in.toLowerCase();
        ArrayList<String> out = new ArrayList<String>();
        for (String i : by_0.b.keySet()) {
            if (!i.startsWith(in)) continue;
            out.add(i);
        }
        return out;
    }

    @Override
    public String c(String locale) {
        return ao_0.a("command.info.item", locale);
    }

    @Override
    public Class<?> b() {
        return RPGItem.class;
    }
}


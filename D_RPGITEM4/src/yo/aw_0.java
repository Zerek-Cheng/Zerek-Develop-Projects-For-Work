/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package yo;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import yo.aa_1;
import yo.ac_1;
import yo.ao_0;

class aw_0
extends aa_1 {
    aw_0() {
    }

    @Override
    public void a(String a2) {
    }

    @Override
    public Object a(String in, String locale) {
        Material mat = Material.matchMaterial((String)in);
        if (mat == null) {
            return new ac_1(String.format(ao_0.a("message.error.material", locale), in));
        }
        return mat;
    }

    @Override
    public List<String> b(String in) {
        ArrayList<String> out = new ArrayList<String>();
        String it = in.toUpperCase();
        for (Material m : Material.values()) {
            if (!m.toString().startsWith(it)) continue;
            out.add(m.toString());
        }
        return out;
    }

    @Override
    public String c(String locale) {
        return ao_0.a("command.info.material", locale);
    }

    @Override
    public Class<?> b() {
        return Material.class;
    }
}


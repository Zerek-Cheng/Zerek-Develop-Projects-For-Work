/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import yo.bj_0;
import yo.bk_0;
import yo.bl_0;
import yo.bm_0;
import yo.bn_0;

public class bi_0 {
    protected static HashMap<Class<? extends bj_0>, bj_0> a = new HashMap();

    public static void a() {
        a.clear();
        bi_0.a(new bm_0());
        bi_0.a(new bl_0());
        bi_0.a(new bn_0());
        bi_0.a(new bk_0());
    }

    public static <T extends bj_0> T a(Class<T> clzz) {
        try {
            return (T)a.get(clzz);
        }
        catch (Exception e2) {
            return null;
        }
    }

    public static bj_0 b(Class<? extends bj_0> clzz) {
        return a.get(clzz);
    }

    public static bj_0 a(String pluginName) {
        for (Class<? extends bj_0> clzz : bi_0.c()) {
            if (!clzz.getSimpleName().equalsIgnoreCase(pluginName)) continue;
            return bi_0.b(clzz);
        }
        return null;
    }

    public static Class<? extends bj_0> b(String pluginName) {
        for (Class<? extends bj_0> clzz : bi_0.c()) {
            if (!clzz.getSimpleName().equalsIgnoreCase(pluginName)) continue;
            return clzz;
        }
        return null;
    }

    public static List<String> b() {
        ArrayList<String> list = new ArrayList<String>();
        for (Class<? extends bj_0> clzz : bi_0.c()) {
            list.add(clzz.getSimpleName());
        }
        return list;
    }

    public static Set<Class<? extends bj_0>> c() {
        return a.keySet();
    }

    public static Collection<bj_0> d() {
        return a.values();
    }

    public static void a(bj_0 object) {
        try {
            bi_0.a(object.c(), object);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void a(Class<? extends bj_0> iHookClass, bj_0 object) {
        try {
            a.put(iHookClass, object);
            if (!object.b()) return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}


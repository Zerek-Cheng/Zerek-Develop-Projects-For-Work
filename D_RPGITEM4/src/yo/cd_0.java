/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.ca_0;
import yo.cb_0;
import yo.cc_0;
import yo.ce_0;
import yo.cf_0;
import yo.cg_0;
import yo.cu;
import yo.cv;
import yo.cw;
import yo.cx;
import yo.cy;
import yo.cz;

public abstract class cd_0 {
    protected static HashMap<Class<? extends cv>, cv> a = new HashMap();
    private static cu b = new cu();

    public static void a() {
        a.clear();
        cd_0.a(new cg_0());
        cd_0.a(new cc_0());
        cz plotme = null;
        if (Bukkit.getServer().getPluginManager().getPlugin("PlotMe") != null) {
            cb_0 plotme_old = new cb_0();
            if (plotme_old.g()) {
                plotme = plotme_old;
            } else {
                ca_0 plotme_core = new ca_0();
                if (plotme_core.g()) {
                    plotme = plotme_core;
                }
            }
        }
        if (plotme == null) {
            plotme = new cz();
        }
        cd_0.a(cz.class, plotme);
        cd_0.a(new cy());
        cd_0.a(new cx());
        cd_0.a(new ce_0());
        cd_0.a(new cw());
        cd_0.a(new cf_0());
    }

    public static cv b() {
        return b;
    }

    public static cv a(Class<? extends cv> clzz) {
        return a.get(clzz);
    }

    public static cv a(String pluginName) {
        Class<? extends cv> clzz = cd_0.b(pluginName);
        if (clzz != null) {
            return cd_0.a(clzz);
        }
        return null;
    }

    public static Class<? extends cv> b(String pluginName) {
        for (cv ips : cd_0.e()) {
            if (!ips.b().equalsIgnoreCase(pluginName)) continue;
            return ips.f();
        }
        return null;
    }

    public static List<String> c() {
        ArrayList<String> list = new ArrayList<String>();
        for (cv ips : cd_0.e()) {
            list.add(ips.b());
        }
        return list;
    }

    public static Set<Class<? extends cv>> d() {
        return a.keySet();
    }

    public static Collection<cv> e() {
        return a.values();
    }

    public static void a(cv object) {
        try {
            cd_0.a(object.getClass(), object);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void a(Class<? extends cv> iPluginSupportClass, cv object) {
        try {
            a.put(iPluginSupportClass, object);
            if (!object.g()) return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}


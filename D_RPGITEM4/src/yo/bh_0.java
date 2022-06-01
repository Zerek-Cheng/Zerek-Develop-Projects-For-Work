/*
 * Decompiled with CFR 0_133.
 */
package yo;

import yo.bg_1;

public class bh_0 {
    private final String a;
    private final Integer[] b;
    private final long c;

    public bh_0(String version) {
        this.a = version;
        this.b = bh_0.d(this.a);
        long v = 0L;
        for (int i = 0; i < this.b.length; ++i) {
            v = (long)((double)v + Math.pow(100.0, this.b.length - i - 1) * (double)this.b[i].intValue());
        }
        this.c = v;
    }

    public long a() {
        return this.c;
    }

    public Integer[] b() {
        return (Integer[])this.b.clone();
    }

    public boolean a(String ver) {
        bh_0 version = new bh_0(ver);
        return this.c >= version.c;
    }

    public boolean b(String ver) {
        bh_0 version = new bh_0(ver);
        return this.c > version.c;
    }

    public boolean c(String ver) {
        bh_0 version = new bh_0(ver);
        return this.c < version.c;
    }

    public boolean equals(Object obj) {
        bh_0 version = new bh_0(obj.toString());
        return this.c == version.c;
    }

    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int)(this.c ^ this.c >>> 32);
        return hash;
    }

    public String toString() {
        return bg_1.a(".", this.b);
    }

    private static Integer[] d(String verionString) {
        String[] sp = verionString.split("\\.");
        while (sp.length < 3) {
            verionString = verionString + ".0";
            sp = verionString.split("\\.");
        }
        Integer[] versionPart = new Integer[sp.length];
        for (int i = 0; i < sp.length; ++i) {
            try {
                versionPart[i] = Integer.parseInt(sp[i]);
                continue;
            }
            catch (Exception e2) {
                versionPart[i] = 0;
            }
        }
        return versionPart;
    }
}


/*
 * Decompiled with CFR 0_133.
 */
package yo;

import yo.ai_1;
import yo.al_0;
import yo.am_0;
import yo.ar_0;
import yo.au_0;
import yo.ax_1;
import yo.bc_0;
import yo.bd_0;

public enum ap_1 {
    EVENTTYPE(am_0.class),
    QUALITY(bd_0.class),
    WEATHERTYPE(au_0.class),
    ALIASTYPE(al_0.class),
    PURGETYPE(bc_0.class),
    EXECUTORTYPE(ai_1.a.class),
    METADATAKEY(ar_0.a.class),
    ARROWRESULT(ax_1.class);
    
    private Class<?> enumClass;

    private ap_1(Class<?> enumClass) {
        this.enumClass = enumClass;
    }

    public Class<?> getEnumClass() {
        return this.enumClass;
    }

    public static Class getClassByName(String name) throws ClassNotFoundException {
        if (name.contains(".")) {
            return Class.forName(name);
        }
        try {
            for (ap_1 next : ap_1.values()) {
                if (!next.name().equals(name.toUpperCase())) continue;
                return next.getEnumClass();
            }
        }
        catch (Exception arr$) {
            // empty catch block
        }
        return null;
    }
}


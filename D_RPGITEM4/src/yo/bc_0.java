/*
 * Decompiled with CFR 0_133.
 */
package yo;

public enum bc_0 {
    DEBUFF,
    BUFF,
    ALL;
    

    private bc_0() {
    }

    public static bc_0 getPurgeType(String name) {
        try {
            return bc_0.valueOf(name.toUpperCase());
        }
        catch (Exception exception) {
            return ALL;
        }
    }
}


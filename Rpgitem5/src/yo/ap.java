// 
// Decompiled by Procyon v0.5.30
// 

package yo;

public enum aP
{
    EVENTTYPE((Class<?>)aM.class), 
    QUALITY((Class<?>)bD.class), 
    WEATHERTYPE((Class<?>)aU.class), 
    ALIASTYPE((Class<?>)aL.class), 
    PURGETYPE((Class<?>)bC.class), 
    EXECUTORTYPE((Class<?>)aI.a.class), 
    METADATAKEY((Class<?>)aR.a.class), 
    ARROWRESULT((Class<?>)aX.class);
    
    private Class<?> enumClass;
    
    private aP(final Class<?> enumClass) {
        this.enumClass = enumClass;
    }
    
    public Class<?> getEnumClass() {
        return this.enumClass;
    }
    
    public static Class getClassByName(final String name) throws ClassNotFoundException {
        if (name.contains(".")) {
            return Class.forName(name);
        }
        try {
            for (final aP next : values()) {
                if (next.name().equals(name.toUpperCase())) {
                    return next.getEnumClass();
                }
            }
        }
        catch (Exception ex) {}
        return null;
    }
}

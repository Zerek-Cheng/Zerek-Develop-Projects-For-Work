// 
// Decompiled by Procyon v0.5.30
// 

package yo;

public class bd
{
    private final bC a;
    private final long b;
    
    public bd(final bC type, final long expireTime) {
        this.a = type;
        this.b = expireTime;
    }
    
    public bC a() {
        return this.a;
    }
    
    public long b() {
        return this.b;
    }
}

// 
// Decompiled by Procyon v0.5.30
// 

package yo;

public class aY
{
    private double a;
    
    public aY() {
        this(0.0);
    }
    
    public aY(final double value) {
        this.a = value;
    }
    
    public double a() {
        return this.a;
    }
    
    public aY a(final double value) {
        this.a = value;
        return this;
    }
    
    public aY b(final double value) {
        this.a -= value;
        return this;
    }
    
    public aY c(final double value) {
        this.a += value;
        return this;
    }
    
    public aY d(final double value) {
        this.a *= value;
        return this;
    }
    
    public aY e(final double value) {
        this.a /= value;
        return this;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.a);
    }
}

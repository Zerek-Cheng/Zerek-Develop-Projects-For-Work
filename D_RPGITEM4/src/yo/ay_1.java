/*
 * Decompiled with CFR 0_133.
 */
package yo;

public class ay_1 {
    private double a;

    public ay_1() {
        this(0.0);
    }

    public ay_1(double value) {
        this.a = value;
    }

    public double a() {
        return this.a;
    }

    public ay_1 a(double value) {
        this.a = value;
        return this;
    }

    public ay_1 b(double value) {
        this.a -= value;
        return this;
    }

    public ay_1 c(double value) {
        this.a += value;
        return this;
    }

    public ay_1 d(double value) {
        this.a *= value;
        return this;
    }

    public ay_1 e(double value) {
        this.a /= value;
        return this;
    }

    public String toString() {
        return String.valueOf(this.a);
    }
}


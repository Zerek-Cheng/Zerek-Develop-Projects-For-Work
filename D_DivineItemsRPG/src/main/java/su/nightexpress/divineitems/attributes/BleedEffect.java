/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.attributes;

public class BleedEffect {
    private int time;
    private double dmg;

    public BleedEffect(int n, double d) {
        this.setTime(n);
        this.setDamage(d);
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int n) {
        this.time = n;
    }

    public double getDamage() {
        return this.dmg;
    }

    public void setDamage(double d) {
        this.dmg = d;
    }
}


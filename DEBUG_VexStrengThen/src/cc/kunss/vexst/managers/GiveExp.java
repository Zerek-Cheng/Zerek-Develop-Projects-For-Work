/*
 * Decompiled with CFR 0_133.
 */
package cc.kunss.vexst.managers;

public class GiveExp {
    private double SuccessMin;
    private double SuccessMax;
    private double DefeatMin;
    private double DefeatMax;

    public GiveExp(double a, double b, double c, double d) {
        this.setSuccessMin(a);
        this.setSuccessMax(b);
        this.setDefeatMin(c);
        this.setDefeatMax(d);
    }

    public double getDefeatMax() {
        return this.DefeatMax;
    }

    public void setDefeatMax(double defeatMax) {
        this.DefeatMax = defeatMax;
    }

    public double getDefeatMin() {
        return this.DefeatMin;
    }

    public void setDefeatMin(double defeatMin) {
        this.DefeatMin = defeatMin;
    }

    public double getSuccessMax() {
        return this.SuccessMax;
    }

    public void setSuccessMax(double successMax) {
        this.SuccessMax = successMax;
    }

    public double getSuccessMin() {
        return this.SuccessMin;
    }

    public void setSuccessMin(double successMin) {
        this.SuccessMin = successMin;
    }
}


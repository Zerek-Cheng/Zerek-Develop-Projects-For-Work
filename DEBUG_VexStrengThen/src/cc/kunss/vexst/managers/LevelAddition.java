/*
 * Decompiled with CFR 0_133.
 */
package cc.kunss.vexst.managers;

public class LevelAddition {
    private double lucky;
    private double pt;
    private double money;
    private double points;

    public LevelAddition(double a, double b, double c, double d) {
        this.setLucky(a);
        this.setPt(b);
        this.setMoney(c);
        this.setPoints(d);
    }

    public double getLucky() {
        return this.lucky;
    }

    public void setLucky(double lucky) {
        this.lucky = lucky;
    }

    public double getPt() {
        return this.pt;
    }

    public void setPt(double pt) {
        this.pt = pt;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getPoints() {
        return this.points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}


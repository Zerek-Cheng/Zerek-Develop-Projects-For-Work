/*
 * Decompiled with CFR 0_133.
 */
package cc.kunss.vexst.managers;

public class ProtectionStoneManager {
    private String lore;
    private double min;
    private double max;

    public ProtectionStoneManager(String lore, double min, double max) {
        this.setLore(lore);
        this.setMax(max);
        this.setMin(min);
    }

    public String getLore() {
        return this.lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public double getMax() {
        return this.max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return this.min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}


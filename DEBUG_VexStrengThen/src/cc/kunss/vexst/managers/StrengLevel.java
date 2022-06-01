/*
 * Decompiled with CFR 0_133.
 */
package cc.kunss.vexst.managers;

public class StrengLevel {
    private int level;
    private double exp;
    private String prefix;

    public StrengLevel(int level, double exp, String prefix) {
        this.setExp(exp);
        this.setLevel(level);
        this.setPrefix(prefix);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExp() {
        return this.exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }
}


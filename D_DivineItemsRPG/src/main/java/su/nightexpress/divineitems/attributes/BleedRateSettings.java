/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.attributes;

import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.attributes.StatSettings;

public class BleedRateSettings
extends StatSettings {
    private int time;
    private String formula;
    private String effect;

    public BleedRateSettings(ItemStat itemStat, int n, String string, String string2) {
        super(itemStat);
        this.setTime(n);
        this.setFormula(string);
        this.setEffect(string2);
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int n) {
        this.time = n;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String string) {
        this.formula = string;
    }

    public String getEffect() {
        return this.effect;
    }

    public void setEffect(String string) {
        this.effect = string;
    }
}


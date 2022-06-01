/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.attributes;

import su.nightexpress.divineitems.attributes.ItemStat;

public class StatSettings {
    private ItemStat att;

    public StatSettings(ItemStat itemStat) {
        this.att = itemStat;
    }

    public ItemStat getAttribute() {
        return this.att;
    }
}


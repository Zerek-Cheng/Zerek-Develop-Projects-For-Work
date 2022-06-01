/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.attributes;

import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.attributes.StatSettings;

public class DisarmRateSettings
extends StatSettings {
    private String effect;
    private String msg_dam;
    private String msg_zertva;

    public DisarmRateSettings(ItemStat itemStat, String string, String string2, String string3) {
        super(itemStat);
        this.effect = string;
        this.msg_dam = string2;
        this.msg_zertva = string3;
    }

    public String getEffect() {
        return this.effect;
    }

    public String getMsgToDamager() {
        return this.msg_dam;
    }

    public String getMsgToEntity() {
        return this.msg_zertva;
    }
}


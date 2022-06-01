/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.glowapi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import su.nightexpress.divineitems.libs.glowapi.GlowAPI;

public class GlowData {
    public Map<UUID, GlowAPI.Color> colorMap = new HashMap<UUID, GlowAPI.Color>();

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        GlowData glowData = (GlowData)object;
        return this.colorMap != null ? this.colorMap.equals(glowData.colorMap) : glowData.colorMap == null;
    }

    public int hashCode() {
        return this.colorMap != null ? this.colorMap.hashCode() : 0;
    }
}


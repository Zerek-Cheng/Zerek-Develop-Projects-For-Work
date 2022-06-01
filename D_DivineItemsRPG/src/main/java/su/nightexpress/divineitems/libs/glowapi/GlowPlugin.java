/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package su.nightexpress.divineitems.libs.glowapi;

import org.bukkit.plugin.Plugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.libs.apihelper.APIManager;
import su.nightexpress.divineitems.libs.glowapi.GlowAPI;

public class GlowPlugin {
    private DivineItems plugin;
    private GlowAPI glowAPI;

    public GlowPlugin(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    public void setup() {
        this.glowAPI = new GlowAPI();
        APIManager.registerAPI(this.glowAPI, (Plugin)this.plugin);
        APIManager.initAPI(GlowAPI.class);
    }

    public void disable() {
        APIManager.disableAPI(GlowAPI.class);
    }
}


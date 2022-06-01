/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package su.nightexpress.divineitems.libs.packetlistener;

import org.bukkit.plugin.Plugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.libs.apihelper.APIManager;
import su.nightexpress.divineitems.libs.packetlistener.PacketListenerAPI;

public class PacketListenerPlugin {
    private DivineItems plugin;
    private PacketListenerAPI packetListenerAPI;

    public PacketListenerPlugin(DivineItems divineItems) {
        this.plugin = divineItems;
        this.packetListenerAPI = new PacketListenerAPI();
    }

    public void setup() {
        APIManager.registerAPI(this.packetListenerAPI, (Plugin)this.plugin);
        APIManager.initAPI(PacketListenerAPI.class);
    }

    public void disable() {
        this.packetListenerAPI.disable((Plugin)this.plugin);
        APIManager.disableAPI(PacketListenerAPI.class);
    }

    public PacketListenerAPI getPLA() {
        return this.packetListenerAPI;
    }
}


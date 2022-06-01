/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package su.nightexpress.divineitems.libs.apihelper;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.plugin.Plugin;
import su.nightexpress.divineitems.libs.apihelper.API;
import su.nightexpress.divineitems.libs.apihelper.exception.MissingHostException;

public class RegisteredAPI {
    protected final API api;
    protected final Set<Plugin> hosts = new HashSet<Plugin>();
    protected boolean initialized = false;
    protected Plugin initializerHost;
    protected boolean eventsRegistered = false;

    public RegisteredAPI(API aPI) {
        this.api = aPI;
    }

    public void registerHost(Plugin plugin) {
        this.hosts.add(plugin);
    }

    public Plugin getNextHost() {
        if (this.api instanceof Plugin && ((Plugin)this.api).isEnabled()) {
            return (Plugin)this.api;
        }
        if (this.hosts.isEmpty()) {
            throw new MissingHostException("API '" + this.api.getClass().getName() + "' is disabled, but no other Hosts have been registered");
        }
        for (Plugin plugin : this.hosts) {
            if (!plugin.isEnabled()) continue;
            return plugin;
        }
        throw new MissingHostException("API '" + this.api.getClass().getName() + "' is disabled and all registered Hosts are as well");
    }

    public void init() {
        if (this.initialized) {
            return;
        }
        this.initializerHost = this.getNextHost();
        this.api.init(this.initializerHost);
        this.initialized = true;
    }

    public void disable() {
        if (!this.initialized) {
            return;
        }
        this.api.disable(this.initializerHost);
        this.initialized = false;
    }
}


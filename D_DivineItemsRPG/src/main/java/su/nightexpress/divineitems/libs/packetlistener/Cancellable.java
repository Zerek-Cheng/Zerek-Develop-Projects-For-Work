/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Cancellable
 */
package su.nightexpress.divineitems.libs.packetlistener;

public class Cancellable
implements org.bukkit.event.Cancellable {
    private boolean cancelled;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean bl) {
        this.cancelled = bl;
    }
}


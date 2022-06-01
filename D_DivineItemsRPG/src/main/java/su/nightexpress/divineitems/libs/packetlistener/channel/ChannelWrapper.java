/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.packetlistener.channel;

import java.net.SocketAddress;

public class ChannelWrapper<T> {
    private T channel;

    public ChannelWrapper(T t) {
        this.channel = t;
    }

    public T channel() {
        return this.channel;
    }

    public SocketAddress getRemoteAddress() {
        return null;
    }

    public SocketAddress getLocalAddress() {
        return null;
    }
}


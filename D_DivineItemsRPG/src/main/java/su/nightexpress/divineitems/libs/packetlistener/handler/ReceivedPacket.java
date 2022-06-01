/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 */
package su.nightexpress.divineitems.libs.packetlistener.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import su.nightexpress.divineitems.libs.packetlistener.channel.ChannelWrapper;
import su.nightexpress.divineitems.libs.packetlistener.handler.PacketAbstract;

public class ReceivedPacket
extends PacketAbstract {
    public ReceivedPacket(Object object, Cancellable cancellable, Player player) {
        super(object, cancellable, player);
    }

    public ReceivedPacket(Object object, Cancellable cancellable, ChannelWrapper channelWrapper) {
        super(object, cancellable, channelWrapper);
    }
}


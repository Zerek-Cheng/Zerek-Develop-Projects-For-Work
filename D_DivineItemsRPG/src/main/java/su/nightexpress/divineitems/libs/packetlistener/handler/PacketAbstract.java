/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 */
package su.nightexpress.divineitems.libs.packetlistener.handler;

import java.lang.reflect.Field;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import su.nightexpress.divineitems.libs.packetlistener.channel.ChannelWrapper;
import su.nightexpress.divineitems.libs.packetlistener.handler.SentPacket;
import su.nightexpress.divineitems.libs.reflection.resolver.FieldResolver;

public abstract class PacketAbstract {
    private Player player;
    private ChannelWrapper channelWrapper;
    private Object packet;
    private Cancellable cancellable;
    protected FieldResolver fieldResolver;

    public PacketAbstract(Object object, Cancellable cancellable, Player player) {
        this.player = player;
        this.packet = object;
        this.cancellable = cancellable;
        this.fieldResolver = new FieldResolver(object.getClass());
    }

    public PacketAbstract(Object object, Cancellable cancellable, ChannelWrapper channelWrapper) {
        this.channelWrapper = channelWrapper;
        this.packet = object;
        this.cancellable = cancellable;
        this.fieldResolver = new FieldResolver(object.getClass());
    }

    public void setPacketValue(String string, Object object) {
        try {
            this.fieldResolver.resolve(string).set(this.getPacket(), object);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setPacketValueSilent(String string, Object object) {
        try {
            this.fieldResolver.resolve(string).set(this.getPacket(), object);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setPacketValue(int n, Object object) {
        try {
            this.fieldResolver.resolveIndex(n).set(this.getPacket(), object);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setPacketValueSilent(int n, Object object) {
        try {
            this.fieldResolver.resolveIndex(n).set(this.getPacket(), object);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public Object getPacketValue(String string) {
        try {
            return this.fieldResolver.resolve(string).get(this.getPacket());
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Object getPacketValueSilent(String string) {
        try {
            return this.fieldResolver.resolve(string).get(this.getPacket());
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Object getPacketValue(int n) {
        try {
            return this.fieldResolver.resolveIndex(n).get(this.getPacket());
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Object getPacketValueSilent(int n) {
        try {
            return this.fieldResolver.resolveIndex(n).get(this.getPacket());
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public FieldResolver getFieldResolver() {
        return this.fieldResolver;
    }

    public void setCancelled(boolean bl) {
        this.cancellable.setCancelled(bl);
    }

    public boolean isCancelled() {
        return this.cancellable.isCancelled();
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean hasPlayer() {
        if (this.player != null) {
            return true;
        }
        return false;
    }

    public ChannelWrapper<?> getChannel() {
        return this.channelWrapper;
    }

    public boolean hasChannel() {
        if (this.channelWrapper != null) {
            return true;
        }
        return false;
    }

    public String getPlayername() {
        if (!this.hasPlayer()) {
            return null;
        }
        return this.player.getName();
    }

    public void setPacket(Object object) {
        this.packet = object;
    }

    public Object getPacket() {
        return this.packet;
    }

    public String getPacketName() {
        return this.packet.getClass().getSimpleName();
    }

    public String toString() {
        return "Packet{ " + (this.getClass().equals(SentPacket.class) ? "[> OUT >]" : "[< IN <]") + " " + this.getPacketName() + " " + (this.hasPlayer() ? this.getPlayername() : (this.hasChannel() ? this.getChannel().channel() : "#server#")) + " }";
    }
}


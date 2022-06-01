/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelDuplexHandler
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelPipeline
 *  io.netty.channel.ChannelPromise
 *  org.bukkit.entity.Player
 */
package su.nightexpress.divineitems.libs.packetlistener.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import org.bukkit.entity.Player;
import su.nightexpress.divineitems.libs.packetlistener.Cancellable;
import su.nightexpress.divineitems.libs.packetlistener.IPacketListener;
import su.nightexpress.divineitems.libs.packetlistener.channel.ChannelAbstract;
import su.nightexpress.divineitems.libs.packetlistener.channel.ChannelWrapper;
import su.nightexpress.divineitems.libs.reflection.minecraft.Minecraft;
import su.nightexpress.divineitems.libs.reflection.resolver.FieldResolver;

public class INCChannel
extends ChannelAbstract {
    private static final Field channelField = networkManagerFieldResolver.resolveByFirstTypeSilent(Channel.class);

    public INCChannel(IPacketListener iPacketListener) {
        super(iPacketListener);
    }

    @Override
    public void addChannel(final Player player) {
        try {
            final Channel channel = this.getChannel(player);
            this.addChannelExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    if (channel.pipeline().get("packet_listener_server") == null) {
                        try {
                            channel.pipeline().addBefore("packet_handler", "packet_listener_player", (io.netty.channel.ChannelHandler)new ChannelHandler(player));
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                }
            });
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException("Failed to add channel for " + (Object)player, reflectiveOperationException);
        }
    }

    @Override
    public void removeChannel(Player player) {
        try {
            final Channel channel = this.getChannel(player);
            this.removeChannelExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (channel.pipeline().get("packet_listener_player") != null) {
                            channel.pipeline().remove("packet_listener_player");
                        }
                    }
                    catch (Exception exception) {
                        throw new RuntimeException(exception);
                    }
                }
            });
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException("Failed to remove channel for " + (Object)player, reflectiveOperationException);
        }
    }

    Channel getChannel(Player player) {
        Object object = Minecraft.getHandle((Object)player);
        Object object2 = playerConnection.get(object);
        return (Channel)channelField.get(networkManager.get(object2));
    }

    public ChannelAbstract.IListenerList newListenerList() {
        return new ListenerList();
    }

    class ChannelHandler
    extends ChannelDuplexHandler
    implements ChannelAbstract.IChannelHandler {
        private Object owner;

        public ChannelHandler(Player player) {
            this.owner = player;
        }

        public ChannelHandler(ChannelWrapper channelWrapper) {
            this.owner = channelWrapper;
        }

        public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) {
            Cancellable cancellable = new Cancellable();
            Object object2 = object;
            if (INCChannel.Packet.isAssignableFrom(object.getClass())) {
                object2 = INCChannel.this.onPacketSend(this.owner, object, cancellable);
            }
            if (cancellable.isCancelled()) {
                return;
            }
            super.write(channelHandlerContext, object2, channelPromise);
        }

        public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) {
            Cancellable cancellable = new Cancellable();
            Object object2 = object;
            if (INCChannel.Packet.isAssignableFrom(object.getClass())) {
                object2 = INCChannel.this.onPacketReceive(this.owner, object, cancellable);
            }
            if (cancellable.isCancelled()) {
                return;
            }
            super.channelRead(channelHandlerContext, object2);
        }

        public String toString() {
            return "INCChannel$ChannelHandler@" + this.hashCode() + " (" + this.owner + ")";
        }
    }

    class INCChannelWrapper
    extends ChannelWrapper<Channel>
    implements ChannelAbstract.IChannelWrapper {
        public INCChannelWrapper(Channel channel) {
            super(channel);
        }

        @Override
        public SocketAddress getRemoteAddress() {
            return ((Channel)this.channel()).remoteAddress();
        }

        @Override
        public SocketAddress getLocalAddress() {
            return ((Channel)this.channel()).localAddress();
        }
    }

    class ListenerList<E>
    extends ArrayList<E>
    implements ChannelAbstract.IListenerList<E> {
        ListenerList() {
        }

        @Override
        public boolean add(E e) {
            try {
                final E e2 = e;
                INCChannel.this.addChannelExecutor.execute(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            Channel channel = null;
                            while (channel == null) {
                                channel = (Channel)channelField.get(e2);
                            }
                            if (channel.pipeline().get("packet_listener_server") == null) {
                                channel.pipeline().addBefore("packet_handler", "packet_listener_server", (io.netty.channel.ChannelHandler)new ChannelHandler(new INCChannelWrapper(channel)));
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                });
            }
            catch (Exception exception) {
                // empty catch block
            }
            return super.add(e);
        }

        @Override
        public boolean remove(Object object) {
            try {
                final Object object2 = object;
                INCChannel.this.removeChannelExecutor.execute(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            Channel channel = null;
                            while (channel == null) {
                                channel = (Channel)channelField.get(object2);
                            }
                            channel.pipeline().remove("packet_listener_server");
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                });
            }
            catch (Exception exception) {
                // empty catch block
            }
            return super.remove(object);
        }

    }

}


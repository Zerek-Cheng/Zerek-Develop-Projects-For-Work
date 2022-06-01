/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package su.nightexpress.divineitems.libs.packetlistener;

import java.lang.reflect.Constructor;
import org.bukkit.entity.Player;
import su.nightexpress.divineitems.libs.packetlistener.IPacketListener;
import su.nightexpress.divineitems.libs.packetlistener.channel.ChannelAbstract;
import su.nightexpress.divineitems.libs.reflection.resolver.ClassResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.ConstructorResolver;

public class ChannelInjector {
    private static final ClassResolver CLASS_RESOLVER = new ClassResolver();
    private ChannelAbstract channel;

    public boolean inject(IPacketListener iPacketListener) {
        try {
            Class.forName("io.netty.channel.Channel");
            this.channel = this.newChannelInstance(iPacketListener, "su.nightexpress.divineitems.libs.packetlistener.channel.INCChannel");
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    protected ChannelAbstract newChannelInstance(IPacketListener iPacketListener, String string) {
        return (ChannelAbstract)new ConstructorResolver(CLASS_RESOLVER.resolve(string)).resolve(new Class[][]{{IPacketListener.class}}).newInstance(iPacketListener);
    }

    public void addChannel(Player player) {
        this.channel.addChannel(player);
    }

    public void removeChannel(Player player) {
        this.channel.removeChannel(player);
    }

    public void addServerChannel() {
        this.channel.addServerChannel();
    }
}


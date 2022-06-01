/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 */
package su.nightexpress.divineitems.libs.packetlistener.channel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import su.nightexpress.divineitems.libs.packetlistener.Cancellable;
import su.nightexpress.divineitems.libs.packetlistener.IPacketListener;
import su.nightexpress.divineitems.libs.reflection.resolver.FieldResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.MethodResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.minecraft.NMSClassResolver;
import su.nightexpress.divineitems.libs.reflection.util.AccessUtil;

public abstract class ChannelAbstract {
    protected static final NMSClassResolver nmsClassResolver = new NMSClassResolver();
    static final Class<?> EntityPlayer = nmsClassResolver.resolveSilent("EntityPlayer");
    static final Class<?> PlayerConnection = nmsClassResolver.resolveSilent("PlayerConnection");
    static final Class<?> NetworkManager = nmsClassResolver.resolveSilent("NetworkManager");
    static final Class<?> Packet = nmsClassResolver.resolveSilent("Packet");
    static final Class<?> ServerConnection = nmsClassResolver.resolveSilent("ServerConnection");
    static final Class<?> MinecraftServer = nmsClassResolver.resolveSilent("MinecraftServer");
    protected static final FieldResolver entityPlayerFieldResolver = new FieldResolver(EntityPlayer);
    protected static final FieldResolver playerConnectionFieldResolver = new FieldResolver(PlayerConnection);
    protected static final FieldResolver networkManagerFieldResolver = new FieldResolver(NetworkManager);
    protected static final FieldResolver minecraftServerFieldResolver = new FieldResolver(MinecraftServer);
    protected static final FieldResolver serverConnectionFieldResolver = new FieldResolver(ServerConnection);
    static final Field networkManager = playerConnectionFieldResolver.resolveSilent("networkManager");
    static final Field playerConnection = entityPlayerFieldResolver.resolveSilent("playerConnection");
    static final Field serverConnection = minecraftServerFieldResolver.resolveByFirstTypeSilent(ServerConnection);
    static final Field connectionList = serverConnectionFieldResolver.resolveByLastTypeSilent(List.class);
    protected static final MethodResolver craftServerFieldResolver = new MethodResolver(Bukkit.getServer().getClass());
    static final Method getServer = craftServerFieldResolver.resolveSilent("getServer");
    final Executor addChannelExecutor = Executors.newSingleThreadExecutor();
    final Executor removeChannelExecutor = Executors.newSingleThreadExecutor();
    static final String KEY_HANDLER = "packet_handler";
    static final String KEY_PLAYER = "packet_listener_player";
    static final String KEY_SERVER = "packet_listener_server";
    private IPacketListener iPacketListener;

    public ChannelAbstract(IPacketListener iPacketListener) {
        this.iPacketListener = iPacketListener;
    }

    public abstract void addChannel(Player var1);

    public abstract void removeChannel(Player var1);

    public void addServerChannel() {
        try {
            Object object = getServer.invoke((Object)Bukkit.getServer(), new Object[0]);
            if (object == null) {
                return;
            }
            Object object2 = serverConnection.get(object);
            if (object2 == null) {
                return;
            }
            List list = (List)connectionList.get(object2);
            Field field = AccessUtil.setAccessible(list.getClass().getSuperclass().getDeclaredField("list"));
            Object object3 = field.get(list);
            if (IListenerList.class.isAssignableFrom(object3.getClass())) {
                return;
            }
            List<Object> list2 = Collections.synchronizedList(this.newListenerList());
            for (E e : list) {
                list2.add(e);
            }
            connectionList.set(object2, list2);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public abstract IListenerList<Object> newListenerList();

    protected final Object onPacketSend(Object object, Object object2, Cancellable cancellable) {
        return this.iPacketListener.onPacketSend(object, object2, cancellable);
    }

    protected final Object onPacketReceive(Object object, Object object2, Cancellable cancellable) {
        return this.iPacketListener.onPacketReceive(object, object2, cancellable);
    }

    static interface IChannelHandler {
    }

    static interface IChannelWrapper {
    }

    static interface IListenerList<E>
    extends List<E> {
    }

}


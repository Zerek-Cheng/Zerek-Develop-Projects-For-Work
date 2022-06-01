/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package su.nightexpress.divineitems.libs.packetlistener.handler;

import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import su.nightexpress.divineitems.libs.packetlistener.handler.PacketOptions;
import su.nightexpress.divineitems.libs.packetlistener.handler.ReceivedPacket;
import su.nightexpress.divineitems.libs.packetlistener.handler.SentPacket;
import su.nightexpress.divineitems.libs.reflection.minecraft.Minecraft;
import su.nightexpress.divineitems.libs.reflection.resolver.FieldResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.MethodResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.minecraft.NMSClassResolver;
import su.nightexpress.divineitems.libs.reflection.util.AccessUtil;

public abstract class PacketHandler {
    private static final List<PacketHandler> handlers = new ArrayList<PacketHandler>();
    private boolean hasSendOptions;
    private boolean forcePlayerSend;
    private boolean forceServerSend;
    private boolean hasReceiveOptions;
    private boolean forcePlayerReceive;
    private boolean forceServerReceive;
    static NMSClassResolver nmsClassResolver = new NMSClassResolver();
    static FieldResolver EntityPlayerFieldResolver = new FieldResolver(nmsClassResolver.resolveSilent("EntityPlayer"));
    static MethodResolver PlayerConnectionMethodResolver = new MethodResolver(nmsClassResolver.resolveSilent("PlayerConnection"));
    private Plugin plugin;

    public static boolean addHandler(PacketHandler packetHandler) {
        boolean bl = handlers.contains(packetHandler);
        if (!bl) {
            PacketOptions packetOptions;
            try {
                packetOptions = packetHandler.getClass().getMethod("onSend", SentPacket.class).getAnnotation(PacketOptions.class);
                if (packetOptions != null) {
                    packetHandler.hasSendOptions = true;
                    if (packetOptions.forcePlayer() && packetOptions.forceServer()) {
                        throw new IllegalArgumentException("Cannot force player and server packets at the same time!");
                    }
                    if (packetOptions.forcePlayer()) {
                        packetHandler.forcePlayerSend = true;
                    } else if (packetOptions.forceServer()) {
                        packetHandler.forceServerSend = true;
                    }
                }
            }
            catch (Exception exception) {
                throw new RuntimeException("Failed to register handler (onSend)", exception);
            }
            try {
                packetOptions = packetHandler.getClass().getMethod("onReceive", ReceivedPacket.class).getAnnotation(PacketOptions.class);
                if (packetOptions != null) {
                    packetHandler.hasReceiveOptions = true;
                    if (packetOptions.forcePlayer() && packetOptions.forceServer()) {
                        throw new IllegalArgumentException("Cannot force player and server packets at the same time!");
                    }
                    if (packetOptions.forcePlayer()) {
                        packetHandler.forcePlayerReceive = true;
                    } else if (packetOptions.forceServer()) {
                        packetHandler.forceServerReceive = true;
                    }
                }
            }
            catch (Exception exception) {
                throw new RuntimeException("Failed to register handler (onReceive)", exception);
            }
        }
        handlers.add(packetHandler);
        return !bl;
    }

    public static boolean removeHandler(PacketHandler packetHandler) {
        return handlers.remove(packetHandler);
    }

    public static void notifyHandlers(SentPacket sentPacket) {
        for (PacketHandler packetHandler : PacketHandler.getHandlers()) {
            try {
                if (packetHandler.hasSendOptions && (packetHandler.forcePlayerSend ? !sentPacket.hasPlayer() : packetHandler.forceServerSend && !sentPacket.hasChannel())) continue;
                packetHandler.onSend(sentPacket);
            }
            catch (Exception exception) {
                System.err.println("[PacketListenerAPI] An exception occured while trying to execute 'onSend'" + (packetHandler.plugin != null ? new StringBuilder(" in plugin ").append(packetHandler.plugin.getName()).toString() : "") + ": " + exception.getMessage());
                exception.printStackTrace(System.err);
            }
        }
    }

    public static void notifyHandlers(ReceivedPacket receivedPacket) {
        for (PacketHandler packetHandler : PacketHandler.getHandlers()) {
            try {
                if (packetHandler.hasReceiveOptions && (packetHandler.forcePlayerReceive ? !receivedPacket.hasPlayer() : packetHandler.forceServerReceive && !receivedPacket.hasChannel())) continue;
                packetHandler.onReceive(receivedPacket);
            }
            catch (Exception exception) {
                System.err.println("[PacketListenerAPI] An exception occured while trying to execute 'onReceive'" + (packetHandler.plugin != null ? new StringBuilder(" in plugin ").append(packetHandler.plugin.getName()).toString() : "") + ": " + exception.getMessage());
                exception.printStackTrace(System.err);
            }
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        PacketHandler packetHandler = (PacketHandler)object;
        if (this.hasSendOptions != packetHandler.hasSendOptions) {
            return false;
        }
        if (this.forcePlayerSend != packetHandler.forcePlayerSend) {
            return false;
        }
        if (this.forceServerSend != packetHandler.forceServerSend) {
            return false;
        }
        if (this.hasReceiveOptions != packetHandler.hasReceiveOptions) {
            return false;
        }
        if (this.forcePlayerReceive != packetHandler.forcePlayerReceive) {
            return false;
        }
        if (this.forceServerReceive != packetHandler.forceServerReceive) {
            return false;
        }
        return !(this.plugin != null ? !this.plugin.equals((Object)packetHandler.plugin) : packetHandler.plugin != null);
    }

    public int hashCode() {
        int n = this.hasSendOptions ? 1 : 0;
        n = 31 * n + (this.forcePlayerSend ? 1 : 0);
        n = 31 * n + (this.forceServerSend ? 1 : 0);
        n = 31 * n + (this.hasReceiveOptions ? 1 : 0);
        n = 31 * n + (this.forcePlayerReceive ? 1 : 0);
        n = 31 * n + (this.forceServerReceive ? 1 : 0);
        n = 31 * n + (this.plugin != null ? this.plugin.hashCode() : 0);
        return n;
    }

    public String toString() {
        return "PacketHandler{hasSendOptions=" + this.hasSendOptions + ", forcePlayerSend=" + this.forcePlayerSend + ", forceServerSend=" + this.forceServerSend + ", hasReceiveOptions=" + this.hasReceiveOptions + ", forcePlayerReceive=" + this.forcePlayerReceive + ", forceServerReceive=" + this.forceServerReceive + ", plugin=" + (Object)this.plugin + '}';
    }

    public static List<PacketHandler> getHandlers() {
        return new ArrayList<PacketHandler>(handlers);
    }

    public static List<PacketHandler> getForPlugin(Plugin plugin) {
        ArrayList<PacketHandler> arrayList = new ArrayList<PacketHandler>();
        if (plugin == null) {
            return arrayList;
        }
        for (PacketHandler packetHandler : PacketHandler.getHandlers()) {
            if (!plugin.equals((Object)packetHandler.getPlugin())) continue;
            arrayList.add(packetHandler);
        }
        return arrayList;
    }

    public void sendPacket(Player player, Object object) {
        if (player == null || object == null) {
            throw new NullPointerException();
        }
        try {
            Object object2 = Minecraft.getHandle((Object)player);
            Object object3 = EntityPlayerFieldResolver.resolve("playerConnection").get(object2);
            PlayerConnectionMethodResolver.resolve("sendPacket").invoke(object3, object);
        }
        catch (Exception exception) {
            System.err.println("[PacketListenerAPI] Exception while sending " + object + " to " + (Object)player);
            exception.printStackTrace();
        }
    }

    public Object cloneObject(Object object) {
        if (object == null) {
            return object;
        }
        Object obj = object.getClass().newInstance();
        Field[] arrfield = object.getClass().getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            Field field = arrfield[n2];
            field = AccessUtil.setAccessible(field);
            field.set(obj, field.get(object));
            ++n2;
        }
        return obj;
    }

    @Deprecated
    public PacketHandler() {
    }

    public PacketHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public abstract void onSend(SentPacket var1);

    public abstract void onReceive(ReceivedPacket var1);
}


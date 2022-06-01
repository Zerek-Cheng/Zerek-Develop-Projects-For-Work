/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Cancellable
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package su.nightexpress.divineitems.libs.packetlistener;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import su.nightexpress.divineitems.libs.apihelper.API;
import su.nightexpress.divineitems.libs.apihelper.APIManager;
import su.nightexpress.divineitems.libs.packetlistener.ChannelInjector;
import su.nightexpress.divineitems.libs.packetlistener.IPacketListener;
import su.nightexpress.divineitems.libs.packetlistener.channel.ChannelWrapper;
import su.nightexpress.divineitems.libs.packetlistener.handler.PacketHandler;
import su.nightexpress.divineitems.libs.packetlistener.handler.ReceivedPacket;
import su.nightexpress.divineitems.libs.packetlistener.handler.SentPacket;

public class PacketListenerAPI
implements IPacketListener,
Listener,
API {
    private ChannelInjector channelInjector;
    protected boolean injected = false;
    Logger logger = Logger.getLogger("PacketListenerAPI");

    @Override
    public void load() {
        this.channelInjector = new ChannelInjector();
        this.injected = this.channelInjector.inject(this);
        if (this.injected) {
            this.channelInjector.addServerChannel();
            this.logger.info("Injected custom channel handlers.");
        } else {
            this.logger.severe("Failed to inject channel handlers");
        }
    }

    @Override
    public void init(Plugin plugin) {
        APIManager.registerEvents(this, this);
        this.logger.info("Adding channels for online players...");
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.channelInjector.addChannel(player);
        }
    }

    @Override
    public void disable(Plugin plugin) {
        if (!this.injected) {
            return;
        }
        this.logger.info("Removing channels for online players...");
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.channelInjector.removeChannel(player);
        }
        this.logger.info("Removing packet handlers (" + PacketHandler.getHandlers().size() + ")...");
        while (!PacketHandler.getHandlers().isEmpty()) {
            PacketHandler.removeHandler(PacketHandler.getHandlers().get(0));
        }
    }

    public static boolean addPacketHandler(PacketHandler packetHandler) {
        return PacketHandler.addHandler(packetHandler);
    }

    public static boolean removePacketHandler(PacketHandler packetHandler) {
        return PacketHandler.removeHandler(packetHandler);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        this.channelInjector.addChannel(playerJoinEvent.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        this.channelInjector.removeChannel(playerQuitEvent.getPlayer());
    }

    @Override
    public Object onPacketReceive(Object object, Object object2, Cancellable cancellable) {
        ReceivedPacket receivedPacket = object instanceof Player ? new ReceivedPacket(object2, cancellable, (Player)object) : new ReceivedPacket(object2, cancellable, (ChannelWrapper)object);
        PacketHandler.notifyHandlers(receivedPacket);
        if (receivedPacket.getPacket() != null) {
            return receivedPacket.getPacket();
        }
        return object2;
    }

    @Override
    public Object onPacketSend(Object object, Object object2, Cancellable cancellable) {
        SentPacket sentPacket = object instanceof Player ? new SentPacket(object2, cancellable, (Player)object) : new SentPacket(object2, cancellable, (ChannelWrapper)object);
        PacketHandler.notifyHandlers(sentPacket);
        if (sentPacket.getPacket() != null) {
            return sentPacket.getPacket();
        }
        return object2;
    }
}


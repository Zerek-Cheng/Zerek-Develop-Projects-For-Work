package com.lmyun.minecraft.event.playerheadtitle;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class PackageListener extends PacketAdapter {
    Main main;

    public PackageListener(Main main) {
        super(main,
                ListenerPriority.NORMAL,
                PacketType.Play.Server.REL_ENTITY_MOVE);
        this.main = main;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Integer playerId = event.getPacket().getIntegers().read(0);
        Player pR = event.getPlayer();//收包玩家
        CraftEntity p = (((CraftWorld) pR.getWorld()).getHandle().getEntity(playerId)).getBukkitEntity();//移动的玩家
        if (!(p instanceof Player)) {
            return;
        }

        Location loc = p.getLocation();
        loc.setY(loc.getY() + 0.9 + 1.5);
        Location locLine1 = loc.clone();
        Location locLine2 = loc.clone();
        Location locLine3 = loc.clone();
        locLine2.setY(locLine1.getY() - 0.3);
        locLine3.setY(locLine2.getY() - 0.3);

        TitleEntitiesManager titleEntitiesManager = this.main.titleEntities.get(p.getUniqueId());
        if (titleEntitiesManager == null || titleEntitiesManager.isKilled()) {
            return;
        }
        titleEntitiesManager.refreshLine();
        CraftEntity line1 = (CraftEntity) titleEntitiesManager.getL1();
        CraftEntity line2 = (CraftEntity) titleEntitiesManager.getL2();
        CraftEntity line3 = (CraftEntity) titleEntitiesManager.getL3();
        PacketContainer p_l1 = this.main.protocolManager.
                createPacket(PacketType.Play.Server.ENTITY_TELEPORT);
        p_l1.getIntegers().write(0, line1.getEntityId());
        p_l1.getDoubles().
                write(0, locLine1.getX()).
                write(1, locLine1.getY()).
                write(2, locLine1.getZ());
        p_l1.getBytes().write(0, (byte) (int) locLine1.getYaw())
                .write(0, (byte) (int) locLine1.getPitch());
        p_l1.getBooleans().write(0, line1.isOnGround());
        PacketContainer p_l2 = this.main.protocolManager.
                createPacket(PacketType.Play.Server.ENTITY_TELEPORT);
        p_l2.getIntegers().write(0, line2.getEntityId());
        p_l2.getDoubles().
                write(0, locLine2.getX()).
                write(1, locLine2.getY()).
                write(2, locLine2.getZ());
        p_l2.getBytes().write(0, (byte) (int) locLine2.getYaw())
                .write(0, (byte) (int) locLine2.getPitch());
        p_l2.getBooleans().write(0, line2.isOnGround());
        PacketContainer p_l3 = this.main.protocolManager.
                createPacket(PacketType.Play.Server.ENTITY_TELEPORT);
        p_l3.getIntegers().write(0, line3.getEntityId());
        p_l3.getDoubles().
                write(0, locLine3.getX()).
                write(1, locLine3.getY()).
                write(2, locLine3.getZ());
        p_l3.getBytes().write(0, (byte) (int) locLine3.getYaw())
                .write(0, (byte) (int) locLine3.getPitch());
        p_l3.getBooleans().write(0, line3.isOnGround());

        try {
            this.main.protocolManager.sendServerPacket(pR, p_l1);
            this.main.protocolManager.sendServerPacket(pR, p_l2);
            this.main.protocolManager.sendServerPacket(pR, p_l3);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + p_l2, e);
        }
    }
}

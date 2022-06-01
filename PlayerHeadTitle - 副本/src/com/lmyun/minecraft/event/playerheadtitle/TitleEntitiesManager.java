package com.lmyun.minecraft.event.playerheadtitle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TitleEntitiesManager {
    UUID l1;
    UUID l2;
    UUID l3;


    public void setL1(Entity l1) {
        this.l1 = l1.getUniqueId();
    }

    public void setL2(Entity l2) {
        this.l2 = l2.getUniqueId();
    }

    public void setL3(Entity l3) {
        this.l3 = l3.getUniqueId();
    }

    public Entity getL1() {
        return Bukkit.getEntity(this.l1);
    }

    public Entity getL2() {
        return Bukkit.getEntity(this.l2);
    }

    public Entity getL3() {
        return Bukkit.getEntity(this.l3);
    }

    public boolean isKilled() {
        Entity entity = Bukkit.getEntity(this.l1);
        Entity entity1 = Bukkit.getEntity(this.l2);
        Entity entity2 = Bukkit.getEntity(this.l3);
        return !(entity != null && entity1 != null && entity2 != null);
    }

    public void tp(Player p) {
        Location loc = p.getLocation();
        loc.setY(loc.getY() + 0.9+1);
        Location locLine1 = loc.clone();
        Location locLine2 = loc.clone();
        Location locLine3 = loc.clone();
        locLine2.setY(locLine1.getY() - 0.3);
        locLine3.setY(locLine2.getY() - 0.3);

        this.getL1().teleport(locLine1);
        this.getL2().teleport(locLine2);
        this.getL3().teleport(locLine3);
    }
}

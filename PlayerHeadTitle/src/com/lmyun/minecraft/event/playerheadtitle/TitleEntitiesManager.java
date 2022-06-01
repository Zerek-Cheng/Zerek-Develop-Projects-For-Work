package com.lmyun.minecraft.event.playerheadtitle;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TitleEntitiesManager {
    UUID l1;
    UUID l2;
    UUID l3;
    UUID p;


    public void setL1(Entity l1) {
        this.l1 = l1.getUniqueId();
    }

    public void setL2(Entity l2) {
        this.l2 = l2.getUniqueId();
    }

    public void setL3(Entity l3) {
        this.l3 = l3.getUniqueId();
    }

    public void setP(Player p) {
        this.p = p.getUniqueId();
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

    public Player getP() {
        return Bukkit.getPlayer(this.p);
    }

    public boolean isKilled() {
        Entity entity = Bukkit.getEntity(this.l1);
        Entity entity1 = Bukkit.getEntity(this.l2);
        Entity entity2 = Bukkit.getEntity(this.l3);
        return !(entity != null && entity1 != null && entity2 != null);
    }

    public void refreshLine() {
        ((CraftPlayer) getP()).getHandle().setCustomNameVisible(false);
        List<String> titles = Main.getPlugin().pluginConfig.get("Config").getStringList("title");
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            title = PlaceholderAPI.setPlaceholders(this.getP(), title);
            titles.set(i, title);
        }
        this.getL1().setCustomName(titles.get(0));
        this.getL2().setCustomName(titles.get(1));
        this.getL3().setCustomName(titles.get(2));
    }

}

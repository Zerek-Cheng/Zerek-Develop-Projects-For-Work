package com._0myun.minecraft.blockexploderepel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = false)
    public void onExplode(EntityExplodeEvent e) {
        Entity entity = e.getEntity();
        //int r = getConfig().getInt("r");
        int r=3;
        Location loc = e.getLocation();
        List<Player> ps = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.getLocation().distance(loc) <= r) ps.add(p);
        });
        ps.forEach(p -> {
            Location pLoc = p.getLocation();
            //pLoc =loc.subtract(pLoc);
            Vector vector = pLoc.toVector().subtract(loc.toVector());
            vector = vector.multiply(getConfig().getDouble("xymult"));
            vector=vector.setY(getConfig().getDouble("hightadd"));
            //vector=vector.setY(1);
            /*vector = vector.setX(r - vector.getX()).setY(r - vector.getY()).setZ(r - vector.getZ());
            vector = vector.setX(0.2* vector.getX()).setY(0.2* vector.getY()).setZ(0.2* vector.getZ());*/
            System.out.println("vector.getX() = " + vector.getX());
            System.out.println("vector.getY() = " + vector.getY());
            System.out.println("vector = " + vector.getZ());
            p.setVelocity(vector);
        });
    }
}

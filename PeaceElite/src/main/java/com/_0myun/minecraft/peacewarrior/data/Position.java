package com._0myun.minecraft.peacewarrior.data;

import lombok.Data;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Map;

@Data
@ToString
@SerializableAs("com._0myun.minecraft.peacewarrior.data.Position")
public class Position extends DataBase implements Cloneable {
    String world;
    int x, y, z;

    public Location toBukkitLocation() {
        return new Location(Bukkit.getWorld(getWorld()), getX(), getY(), getZ());
    }

    public Position(Map<String, Object> args) {
        super(args);
    }

    public Position(String world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(Location loc) {
        this.world = loc.getWorld().getName();
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
    }

    @Override
    public Position clone() {
        return new Position(toBukkitLocation());
    }
}

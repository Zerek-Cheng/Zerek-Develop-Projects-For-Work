package com._0myun.minecraft.treasuremap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("com._0myun.minecraft.treasuremap.Position")
@Data
@AllArgsConstructor
@ToString
public class Position implements ConfigurationSerializable {

    private String world;
    private int x, y, z;

    public Location getLocation() {
        return new Location(Bukkit.getWorld(getWorld()), getX(), getY(), getZ());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("world", getWorld());
        result.put("x", getX());
        result.put("y", getY());
        result.put("z", getZ());

        return result;
    }

    public static Position deserialize(Map<String, Object> args) {
        return new Position(String.valueOf(args.get("world")), (Integer) args.get("x"), (Integer) args.get("y"), (Integer) args.get("z"));
    }
}
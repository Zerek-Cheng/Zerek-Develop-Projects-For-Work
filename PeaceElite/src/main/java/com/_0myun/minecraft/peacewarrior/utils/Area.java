package com._0myun.minecraft.peacewarrior.utils;

import com._0myun.minecraft.peacewarrior.data.Position;
import lombok.*;
import org.bukkit.Location;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Area {
    Position pos1;
    Position pos2;

    public Area narrow(int length) {
        if (length == 0) return this;
        int xaSide = new Random().nextInt(length);
        int xbSide = length - xaSide;

        int zaSide = new Random().nextInt(length);
        int zbSide = length - zaSide;


        pos1.setX(pos1.getX() + xaSide);
        pos2.setX(pos2.getX() - xbSide);

        pos1.setZ(pos1.getZ() + zaSide);
        pos2.setZ(pos2.getZ() - zbSide);
        return this;
    }

    public boolean inside(Position pos) {
        if (!pos.getWorld().equalsIgnoreCase(pos1.getWorld())) return false;
        return pos1.getX() <= pos.getX() && pos.getX() <= pos2.getX()
                && pos1.getZ() <= pos.getZ() && pos.getZ() <= pos2.getZ();
    }

    public boolean inside(Location loc) {
        return inside(new Position(loc));
    }

    public Position randPosition() {
        Position pos = pos1.clone();
        pos.setX(pos1.getX() + new Random().nextInt(pos2.getX() - pos1.getX()));
        pos.setZ(pos1.getZ() + new Random().nextInt(pos2.getZ() - pos1.getZ()));
        return pos;

    }
}

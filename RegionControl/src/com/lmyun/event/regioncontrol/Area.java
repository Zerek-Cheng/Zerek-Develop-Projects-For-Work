package com.lmyun.event.regioncontrol;

import org.bukkit.Location;

import javax.annotation.Nonnull;

public class Area {
    /**
     * 两坐标
     */
    private final Location minLocation, maxLocation;

    /**
     * 两点必须在同一个世界，且两点坐标将会被重新计算。
     * 构造出来的 minLocation 和 maxLocation 和传入的 point 不一定相同，
     * 但其代表的区域一致。
     *
     * @param point1 第一个点
     * @param point2 第二个点
     * @throws IllegalArgumentException 坐标在不同世界时
     */
    public Area(@Nonnull Location point1, @Nonnull Location point2) {
        // 坐标在不同世界
        if (!point1.getWorld().equals(point2.getWorld()))
            throw new IllegalArgumentException("Area cannot be created between different worlds");

        //Location1 < Location2
        double x1 = point1.getX(), x2 = point2.getX(), y1 = point1.getY(), y2 = point2.getY(), z1 = point1.getZ(), z2 = point2.getZ(), t;
        if (x1 > x2) {
            t = x1;
            x1 = x2;
            x2 = t;
        }
        if (y1 > y2) {
            t = y1;
            y1 = y2;
            y2 = t;
        }
        if (z1 > z2) {
            t = z1;
            z1 = z2;
            z2 = t;
        }
        this.minLocation = new Location(point1.getWorld(), x1, y1, z1);
        this.maxLocation = new Location(point2.getWorld(), x2, y2, z2);
    }

    /**
     * 判断坐标是否含于该区域
     *
     * @param location 坐标
     */
    public boolean contain(final Location location) {
        if (location == null)
            return false;
        if (!getMinLocation().getWorld().equals(location.getWorld()))
            return false;
        if (getMinLocation().getX() > location.getX())
            return false;
        if (getMinLocation().getY() > location.getY())
            return false;
        if (getMinLocation().getZ() > location.getZ())
            return false;
        if (getMaxLocation().getX() < location.getX())
            return false;
        if (getMaxLocation().getY() < location.getY())
            return false;
        //noinspection RedundantIfStatement
        if (getMaxLocation().getZ() < location.getZ())
            return false;
        return true;
    }

    public final Location getMinLocation() {
        return minLocation;
    }

    public final Location getMaxLocation() {
        return maxLocation;
    }
}

package net.ginyai.poketrainerrank.api.util;

public class Location {
    public final double x,y,z;
    public final float pitch,yaw;
    public final Object world;

    public Location(double x, double y, double z, float pitch, float yaw, Object world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.world = world;
    }
}

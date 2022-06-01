package net.ginyai.poketrainerrank.api.util;

public final class Pos {
    public final int x;
    public final int y;
    public final int z;

    public Pos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Pos(double x, double y, double z) {
        this.x = MathHelper.floor(x);
        this.y = MathHelper.floor(y);
        this.z = MathHelper.floor(z);
    }

}

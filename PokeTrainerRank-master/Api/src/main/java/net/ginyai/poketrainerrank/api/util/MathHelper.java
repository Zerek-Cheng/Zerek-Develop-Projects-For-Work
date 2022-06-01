package net.ginyai.poketrainerrank.api.util;

public class MathHelper {
    /**
     * Returns the greatest integer less than or equal to the double argument
     */
    public static int floor(double value) {
        int i = (int)value;
        return value < (double)i ? i - 1 : i;
    }
}

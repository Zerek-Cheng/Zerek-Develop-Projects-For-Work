package org.bukkit.util;

public final class NumberConversions
{
    public static int floor(double num)
    {
        int floor = (int)num;
        return floor == num ? floor : floor - (int)(Double.doubleToRawLongBits(num) >>> 63);
    }

    public static int ceil(double num)
    {
        int floor = (int)num;
        return floor == num ? floor : floor + (int)((Double.doubleToRawLongBits(num) ^ 0xFFFFFFFF) >>> 63);
    }

    public static int round(double num)
    {
        return floor(num + 0.5D);
    }

    public static double square(double num)
    {
        return num * num;
    }

    public static int toInt(Object object)
    {
        if ((object instanceof Number)) {
            return ((Number)object).intValue();
        }
        try
        {
            return Integer.valueOf(object.toString()).intValue();
        }
        catch (NumberFormatException e) {}catch (NullPointerException e) {}
        return 0;
    }

    public static float toFloat(Object object)
    {
        if ((object instanceof Number)) {
            return ((Number)object).floatValue();
        }
        try
        {
            return Float.valueOf(object.toString()).floatValue();
        }
        catch (NumberFormatException e) {}catch (NullPointerException e) {}
        return 0.0F;
    }

    public static double toDouble(Object object)
    {
        if ((object instanceof Number)) {
            return ((Number)object).doubleValue();
        }
        try
        {
            return Double.valueOf(object.toString()).doubleValue();
        }
        catch (NumberFormatException e) {}catch (NullPointerException e) {}
        return 0.0D;
    }

    public static long toLong(Object object)
    {
        if ((object instanceof Number)) {
            return ((Number)object).longValue();
        }
        try
        {
            return Long.valueOf(object.toString()).longValue();
        }
        catch (NumberFormatException e) {}catch (NullPointerException e) {}
        return 0L;
    }

    public static short toShort(Object object)
    {
        if ((object instanceof Number)) {
            return ((Number)object).shortValue();
        }
        try
        {
            return Short.valueOf(object.toString()).shortValue();
        }
        catch (NumberFormatException e) {}catch (NullPointerException e) {}
        return 0;
    }

    public static byte toByte(Object object)
    {
        if ((object instanceof Number)) {
            return ((Number)object).byteValue();
        }
        try
        {
            return Byte.valueOf(object.toString()).byteValue();
        }
        catch (NumberFormatException e) {}catch (NullPointerException e) {}
        return 0;
    }
}

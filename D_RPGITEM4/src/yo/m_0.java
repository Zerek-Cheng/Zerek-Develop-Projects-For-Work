/*
 * Decompiled with CFR 0_133.
 */
package yo;

public final class m_0 {
    public static int a(double value) {
        assert (!Double.isNaN(value)) : "Values of NaN are not supported.";
        long bits = Double.doubleToLongBits(value);
        return (int)(bits ^ bits >>> 32);
    }

    public static int a(float value) {
        assert (!Float.isNaN(value)) : "Values of NaN are not supported.";
        return Float.floatToIntBits(value * 6.6360896E8f);
    }

    public static int a(int value) {
        return value;
    }

    public static int a(long value) {
        return (int)(value ^ value >>> 32);
    }

    public static int a(Object object) {
        return object == null ? 0 : object.hashCode();
    }

    public static int b(float v) {
        int possible_result = (int)v;
        if (v - (float)possible_result > 0.0f) {
            ++possible_result;
        }
        return possible_result;
    }
}


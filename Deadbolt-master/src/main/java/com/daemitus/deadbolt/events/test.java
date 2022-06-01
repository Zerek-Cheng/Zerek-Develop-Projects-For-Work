package com.daemitus.deadbolt.events;

public class test {
    public static void main(String[] args) {
        System.out.println(ALLATORIxDEMO("c\u0001\u00051Z$\u0002|\u0002%\u0018b\u001bn\u0016y\u0014m\u0001m\u001ay\u0000f[e\u0010Zm\u001ay\u0000f\u0006$\u0018j\u0005{\u001ce\u0012&\u0014e\u0011&\u0018d\u0011o\u001ce\u0012$\u0018b\u001bn\u0016y\u0014m\u0001&\u0018d\u0011xZ|\u001c{Xf\u001ao\u0006$G8E2D2F&\u0002b\u0005&\u0014g\u0005c\u0014&\u0014y\u0018d\u0000y\u0010y\u0006&\u0002d\u0007`\u0006c\u001a{X|\u0010j\u0005d\u001b&\u0014y\u0018d\u0000yXx\u001eb\u001bx"));
    }
    public static String ALLATORIxDEMO(final String a) {
        final int n = 1 << 3 ^ 0x3;
        final int n2 = (0x2 ^ 0x5) << 4 ^ 0x5;
        final int length = a.length();
        final char[] array = new char[length];
        int n3;
        int i = n3 = length - 1;
        final char[] array2 = array;
        final char c = (char)n2;
        final int n4 = n;
        while (i >= 0) {
            final char[] array3 = array2;
            final int n5 = n3;
            final char char1 = a.charAt(n5);
            --n3;
            array3[n5] = (char)(char1 ^ n4);
            if (n3 < 0) {
                break;
            }
            final char[] array4 = array2;
            final int n6 = n3--;
            array4[n6] = (char)(a.charAt(n6) ^ c);
            i = n3;
        }
        return new String(array2);
    }
}

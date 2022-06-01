/*
 * Decompiled with CFR 0_133.
 */
package yo;

import java.io.IOException;
import java.io.InputStream;
import think.rpgitems.Plugin;

public class an_0 {
    public static int[] a;

    public static void a() {
        a = new int[65535];
        try {
            InputStream in = Plugin.c.getResource("font.bin");
            for (int i = 0; i < a.length; ++i) {
                an_0.a[i] = in.read();
            }
            in.close();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}


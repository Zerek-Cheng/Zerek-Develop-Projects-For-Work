/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package yo;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yo.f_0;
import yo.x_0;

public class as_0
extends x_0<Object> {
    private static final String y = "\u00a7c\u00a7a\u00a7f\u00a7e";
    public static final int w = 0;
    public static final int x = 4;

    public static boolean a(ItemStack item) {
        if (!item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return false;
        }
        List lore = meta.getLore();
        if (lore.isEmpty()) {
            return false;
        }
        return ((String)lore.get(0)).contains(y);
    }

    public static as_0 a(String lore) {
        as_0 meta = new as_0();
        int pos = lore.indexOf(y);
        if (pos == -1) {
            return meta;
        }
        String lenStr = lore.substring(pos + y.length(), pos + y.length() + 8);
        int length = as_0.b(lenStr, 0);
        String data = lore.substring(pos + y.length() + 8, pos + y.length() + 8 + length);
        int off = 0;
        while (off < length) {
            int index = as_0.c(data, off);
            off += 4;
            int key = index & 31;
            int type = index >> 5;
            switch (type) {
                case 0: {
                    int byteValue = as_0.c(data, off);
                    off += 4;
                    meta.a(key, Byte.valueOf((byte)byteValue));
                    break;
                }
                case 1: {
                    int shortValue = as_0.b(data, off);
                    off += 8;
                    meta.a(key, Short.valueOf((short)shortValue));
                    break;
                }
                case 2: {
                    int intValue = as_0.a(data, off);
                    off += 16;
                    meta.a(key, Integer.valueOf(intValue));
                    break;
                }
                case 3: {
                    int floatValueBits = as_0.a(data, off);
                    off += 16;
                    meta.a(key, Float.valueOf(Float.intBitsToFloat(floatValueBits)));
                    break;
                }
                case 4: {
                    int stringLength = as_0.b(data, off);
                    off += 8;
                    byte[] bytes = new byte[stringLength];
                    for (int i = 0; i < stringLength; ++i) {
                        bytes[i] = (byte)as_0.c(data, off);
                        off += 4;
                    }
                    try {
                        meta.a(key, new String(bytes, "UTF-8"));
                        break;
                    }
                    catch (UnsupportedEncodingException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return meta;
    }

    private static int a(String iStr, int off) {
        return as_0.b(iStr, off + 0) << 16 | as_0.b(iStr, off + 8);
    }

    private static int b(String sStr, int off) {
        return as_0.c(sStr, off + 0) << 8 | as_0.c(sStr, off + 4);
    }

    private static int c(String bStr, int off) {
        return Integer.parseInt("" + bStr.charAt(off + 1) + bStr.charAt(off + 3), 16);
    }

    public String k() {
        StringBuilder out = new StringBuilder();
        out.append(y);
        f_0 it = this.i();
        while (it.hasNext()) {
            it.a();
            int key = it.b();
            Object value = it.c();
            int index = key & 31;
            if (value instanceof Byte) {
                this.a(out, index |= 0);
                this.a(out, ((Byte)value).intValue());
                continue;
            }
            if (value instanceof Short) {
                this.a(out, index |= 32);
                this.b(out, ((Short)value).intValue());
                continue;
            }
            if (value instanceof Integer) {
                this.a(out, index |= 64);
                this.c(out, (Integer)value);
                continue;
            }
            if (value instanceof Float) {
                this.a(out, index |= 96);
                this.c(out, Float.floatToIntBits(((Float)value).floatValue()));
                continue;
            }
            if (!(value instanceof String)) continue;
            this.a(out, index |= 128);
            this.a(out, (String)value);
        }
        int size = out.length() - y.length();
        this.b(out, y.length(), size);
        return out.toString();
    }

    private void a(StringBuilder out, String value) {
        try {
            byte[] data = value.getBytes("UTF-8");
            this.b(out, data.length);
            for (byte b2 : data) {
                this.a(out, b2);
            }
        }
        catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
    }

    private void a(StringBuilder out, int b2) {
        String hex = Integer.toString(b2, 16);
        out.append('\u00a7');
        out.append(hex.length() == 1 ? "0" : Character.valueOf(hex.charAt(0)));
        out.append('\u00a7');
        out.append(hex.charAt(hex.length() - 1));
    }

    private void b(StringBuilder out, int s) {
        this.a(out, s >> 8);
        this.a(out, s & 255);
    }

    private void c(StringBuilder out, int s) {
        this.b(out, s >> 16);
        this.b(out, s & 65535);
    }

    private void a(StringBuilder out, int offset, int b2) {
        String hex = Integer.toString(b2, 16);
        out.insert(offset, '\u00a7');
        out.insert(offset + 1, hex.length() == 1 ? "0" : Character.valueOf(hex.charAt(0)));
        out.insert(offset + 2, '\u00a7');
        out.insert(offset + 3, hex.charAt(hex.length() - 1));
    }

    private void b(StringBuilder out, int offset, int s) {
        this.a(out, offset, s >> 8);
        this.a(out, offset + 4, s & 255);
    }

    private void c(StringBuilder out, int offset, int s) {
        this.b(out, offset, s >> 16);
        this.b(out, offset + 8, s & 65535);
    }
}


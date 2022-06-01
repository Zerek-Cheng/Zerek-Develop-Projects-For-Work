package com._0myun.minecraft.loreeffect;

public class ColorSign {
    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append("a");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split("a");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    public static String asciiToLore(String value) {
        String result = "";
        for (int i = 0; i < value.length(); i++) {
            String tmp = value.substring(i, i + 1);
            result += "§" + tmp;
        }
        return result;
    }
}

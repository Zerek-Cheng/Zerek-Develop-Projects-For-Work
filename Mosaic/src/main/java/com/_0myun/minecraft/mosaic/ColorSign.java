package com._0myun.minecraft.mosaic;

public class ColorSign {
    public static String parse(String str) {
        String tmp = "";
        char[] b = str.toCharArray();
        //转换成响应的ASCLL
        for (char c : b) {
            tmp += Integer.valueOf(c);
        }
        str = tmp;
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            String sub = str.substring(i, i + 1);
            result += "§" + sub;
        }
        return result;
    }
}

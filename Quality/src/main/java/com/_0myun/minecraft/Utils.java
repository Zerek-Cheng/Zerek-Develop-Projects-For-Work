package com._0myun.minecraft;

import java.util.List;

public class Utils {
    public static boolean contains(List<String> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(str)) return true;
        }
        return false;
    }
}

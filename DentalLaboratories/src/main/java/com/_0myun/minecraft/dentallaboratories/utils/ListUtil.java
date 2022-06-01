package com._0myun.minecraft.dentallaboratories.utils;

import java.util.List;

public class ListUtil {
    public static boolean contain(List<String> list, String str) {
        for (String s : list) {
            if (s.contains(str)) return true;
        }
        return false;
    }
}

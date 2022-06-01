package com._0myun.minecraft.utils;

import java.util.List;

public class ItemUtils {
    public static boolean LoreHas(List<String> lores, String str) {
        boolean[] result = {false};
        lores.forEach((lore) -> {
            if (!lore.contains(str)) return;
            result[0] = true;
        });
        return result[0];
    }
}

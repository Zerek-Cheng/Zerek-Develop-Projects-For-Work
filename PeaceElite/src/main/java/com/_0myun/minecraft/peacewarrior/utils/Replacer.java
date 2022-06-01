package com._0myun.minecraft.peacewarrior.utils;

import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import lombok.var;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Replacer {
    public static List<String> replace(List<String> strs, Object replaced) {
        if (replaced == null) return strs;
        var result = new ArrayList<String>();
        for (String str : strs) {
            result.add(replace(str, replaced));
        }
        return result;
    }


    public static List<String> replace(List<String> strs, Battle replaced) {
        if (replaced == null) return strs;
        var result = new ArrayList<String>();
        for (String str : strs) {
            str = str.replace("!stat!", R.INSTANCE.lang("lang_battle_stat_" + replaced.getStat().getStat()));
            result.add(str);
        }
        return replace(result, (Object) replaced);
    }

    public static String replace(String str, Object replaced) {
        if (str == null) return str;
        for (Field field : replaced.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                str = str.replace("!" + field.getName() + "!", String.valueOf(field.get(replaced)));
            } catch (Exception ex) {
                continue;
            }
        }
        return str;
    }
}

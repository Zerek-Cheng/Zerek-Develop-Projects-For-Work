package com._0myun.systemtransaction.systemtransaction.util;

import java.util.List;

public class ListUtil {
    public static boolean isInclude(List<String> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).indexOf(str) != -1) {
                return true;
            }
        }
        return false;
    }

}

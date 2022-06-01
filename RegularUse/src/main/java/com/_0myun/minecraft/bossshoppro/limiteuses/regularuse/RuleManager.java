package com._0myun.minecraft.bossshoppro.limiteuses.regularuse;

import java.util.Arrays;
import java.util.List;

public class RuleManager {
    public static List<String> getConfig() {
        return RegularUse.getPlugin().getConfig().getStringList("rule");
    }

    public static long getRule(String menu, String god) {
        List<String> rules = getConfig();
        String[] rule = {""};
        String[] tmp = rule;
        rules.forEach(ruleTmp -> {
            String[] split = ruleTmp.split(":");
            if (split.length < 3) return;
            if (ruleTmp.startsWith(menu + ":" + god)) tmp[0] = ruleTmp;
        });
        rule = tmp[0].split(":");
        return Long.valueOf(rule[2]);
    }
}

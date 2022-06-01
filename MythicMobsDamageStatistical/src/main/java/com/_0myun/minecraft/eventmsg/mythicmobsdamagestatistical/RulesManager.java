package com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical;

import com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Rule;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class RulesManager {
    private static FileConfiguration getConfig() {
        return Main.plugin.getConfig();
    }

    public static ConfigurationSection getAllRules() {
        return getConfig().getConfigurationSection("rule");
    }

    public static Rule getRule(String name) {
        if (!getAllRules().isSet(name))return null;
        return (Rule) getAllRules().get(name);
    }
}

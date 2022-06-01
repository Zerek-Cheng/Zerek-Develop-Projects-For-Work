package com._0myun.minecraft.tiredcommands;

import org.bukkit.configuration.ConfigurationSection;

public class CommandsManager {

    private static ConfigurationSection getData() {
        return Main.plugin.getConfig().getConfigurationSection("commands");
    }

    public static ConfigurationSection getCommandConfig(String name) {
        return getData().getConfigurationSection(name);
    }

    public static String getCommand(String name) {
        return getCommandConfig(name).getString("command");
    }


    public static String getCommandCost(String name) {
        return getCommandConfig(name).getString("cost");
    }

}

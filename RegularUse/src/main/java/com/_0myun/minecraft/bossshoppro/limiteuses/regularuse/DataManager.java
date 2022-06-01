package com._0myun.minecraft.bossshoppro.limiteuses.regularuse;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class DataManager {
    private static FileConfiguration getData() {
        return RegularUse.getPlugin().getData();
    }

    public static ConfigurationSection getPlayerData(String player) {
        if (!getData().contains(player)) getData().createSection(player);
        return getData().getConfigurationSection(player);
    }

    public static long getPlayerUseTime(String player, String menu, String god) {
        return getPlayerData(player).getLong(menu + "." + god);
    }

    public static void resetPlayerUseTime(String player, String menu, String god) {
        long rule = RuleManager.getRule(menu, god);
        if (rule <= 0) return;
        getPlayerData(player).set(menu + "." + god, System.currentTimeMillis() + rule);

        TaskChain.newChain().add(new TaskChain.GenericTask() {
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lius set " + player + " " + menu + " " + god + " 0");
            }
        }).execute();
        System.out.println("调用的命令: " + "lius set " + player + " " + menu + " " + god + " 0");
       /* LimitedUsesManager api = RegularUse.getPlugin().getApi().getLimitedUsesManager();
        api.loadPlayer(Bukkit.getOfflinePlayer(player));
        api.detectUsedAmount(Bukkit.getOfflinePlayer(player), menu + ":" + god);
        api.savePlayer(Bukkit.getOfflinePlayer(player), false);*/
        System.out.println("刷新...");
    }
}

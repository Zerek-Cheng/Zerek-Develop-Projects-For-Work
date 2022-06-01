package com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical;

import com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Rule;
import com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Statistical;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.logging.Level;

public class DataManager {
    private static FileConfiguration getConfig() {
        return Main.plugin.getConfig();
    }

    public static ConfigurationSection getData() {
        return getConfig().getConfigurationSection("data");
    }

    public static Statistical getStatistical() {
        return (Statistical) getData().get("statistical");
    }

    public static Map<UUID, Long> getStatistical(String name) {
        return getStatistical().getData().get(name);
    }

    public static List<UUID> getTop10(String name) {
        Map<UUID, Long> statistical = DataManager.getStatistical(name);
        if (statistical == null) return null;
        List<Map.Entry<UUID, Long>> sort = new ArrayList<>(statistical.entrySet());
        Collections.sort(sort, Comparator.comparingLong(Map.Entry::getValue));
        List<UUID> getter = new ArrayList<>();
        for (int i = 0; i < (sort.size() >= 10 ? 10 : sort.size()); i++)
            getter.add(sort.get(sort.size() - 1 - i).getKey());
        return getter;
    }

    public static void resetStatistical(String name) {
        if (!getStatistical().getData().containsKey(name)) return;
        getStatistical().getData().put(name, new HashMap<>());
    }

    public static List<Map<?, ?>> getReward() {
        return getData().getMapList("reward");
    }

    public static List<String> getPlayerReward(UUID uuid) {
        List<Map<?, ?>> rewards = getReward();
        for (int i = 0; i < rewards.size(); i++) {
            Map<?, ?> reward = rewards.get(i);
            if (!String.valueOf(reward.get("uuid")).equalsIgnoreCase(uuid.toString())) continue;
            List<String> rewardCmd = (List<String>) reward.get("reward");
            rewards.remove(i);
            getData().set("reward", rewards);
            return rewardCmd;
        }
        return null;
    }

    public static void reward(String name, UUID uuid) {
        List<UUID> top10 = getTop10(name);
        int index = top10.indexOf(uuid) + 1;
        List<Map<?, ?>> reward = getReward();
        Rule rule = RulesManager.getRule(name);
        List<String> rewardCmd = rule.getReward(index);
        if (rewardCmd == null) {
            Main.getPlugin().getLogger().log(Level.WARNING, "没有设置第" + index + "名的奖励！");
            return;
        }
        rewardCmd = Arrays.asList(rewardCmd.toArray(new String[0]));
        Map<String, Object> rewardMap = new HashMap<>();
        rewardMap.put("uuid", uuid.toString());
        rewardMap.put("reward", rewardCmd);
        reward.add(rewardMap);
        getData().set("reward", reward);
    }

    public static void record(UUID player, long damage) {
        Set<String> keys = RulesManager.getAllRules().getKeys(false);
        keys.forEach(key -> {
            Map<String, Map<UUID, Long>> data = getStatistical().getData();
            if (!data.containsKey(key)) data.put(key, new HashMap<>());
            Map<UUID, Long> section = data.get(key);
            section.put(player, section.get(player) == null ? 0 : Long.valueOf(section.get(player)) + damage);
        });
    }
}

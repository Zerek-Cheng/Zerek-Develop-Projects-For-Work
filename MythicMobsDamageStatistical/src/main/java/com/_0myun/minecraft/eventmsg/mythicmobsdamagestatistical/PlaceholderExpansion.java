package com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical;

import com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Rule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "mmds";
    }

    @Override
    public String getAuthor() {
        return "0MYUN.COM-EVENTMSG";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (!params.contains("_")) return "unknown";
        //mmds_    day_name_1
        //mmds_    day_damage_1
        String[] split = params.split("_");
        if (split.length < 2) return "unknown";
        String ruleName = split[0];
        String type = split[1];

        if (split.length == 3) {
            int index = Integer.valueOf(split[2]);
            Rule rule = RulesManager.getRule(ruleName);
            if (rule == null) return "规则无效";
            List<UUID> top10 = DataManager.getTop10(ruleName);
            if (index > top10.size()) return "还没有人占坑呢";
            UUID uuid = top10.get(index - 1);


            if (type.equalsIgnoreCase("name")) {
                return Bukkit.getOfflinePlayer(uuid).getName();
            } else if (type.equalsIgnoreCase("damage")) {
                return String.valueOf(DataManager.getStatistical(ruleName).get(uuid));
            }
            return "unknown";
        }
        if (split.length == 2 && type.equalsIgnoreCase("damage")) {
            return String.valueOf(DataManager.getStatistical(ruleName).get(p.getUniqueId()));
        }
        return "unknown";
    }
}

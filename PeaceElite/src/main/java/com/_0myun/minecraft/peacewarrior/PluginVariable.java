package com._0myun.minecraft.peacewarrior;

import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com._0myun.minecraft.peacewarrior.data.BattleMap;
import com._0myun.minecraft.peacewarrior.task.battle.SafeAreaThread;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PluginVariable extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "peaceelite";
    }

    @Override
    public String getAuthor() {
        return "灵梦云科技";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }


    @Override
    public String onPlaceholderRequest(Player p, String params) {
        String[] split = params.split("_");
        try {
            PlayerData playerData = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
            if (split[0].equalsIgnoreCase("p") || split[0].equalsIgnoreCase("player")) {
                if (split.length > 1) {
                    if (split[1].equalsIgnoreCase("map")) return playerData.getMap();
                    if (split[1].equalsIgnoreCase("kill")) return String.valueOf(playerData.getKill());
                    if (split[1].equalsIgnoreCase("win")) return String.valueOf(playerData.getWin());
                    if (split[1].equalsIgnoreCase("total")) return String.valueOf(playerData.getTotal());
                }
            }
            if (split[0].equalsIgnoreCase("b") || split[0].equalsIgnoreCase("battle")) {
                if (playerData.getMap() == null || playerData.getMap().isEmpty()) return "null map";
                Battle battle = DBManager.battleDao.queryForMap(playerData.getMap());
                if (split.length > 1) {
                    if (split[1].equalsIgnoreCase("map")) return battle.getMap();
                    if (split[1].equalsIgnoreCase("playeramount")) return String.valueOf(battle.getPlayer_amount());
                    if (split[1].equalsIgnoreCase("alive")) return String.valueOf(battle.getAlive());
                    if (split[1].equalsIgnoreCase("progress")) return String.valueOf(battle.getProgress());

                    if (split[1].equalsIgnoreCase("nextprogress"))
                        return SafeAreaThread.nextTime.containsKey(battle.getMap()) ? SafeAreaThread.nextTime.get(battle.getMap()) : "暂无";
                }
            }
            if (split[0].equalsIgnoreCase("m") || split[0].equalsIgnoreCase("map")) {
                if (playerData.getMap() == null || playerData.getMap().isEmpty()) return "null map";
                BattleMap map = MapManager.getMapByName(playerData.getMap());
                if (split.length > 1) {
                    if (split[1].equalsIgnoreCase("name")) return map.getName();
                    if (split[1].equalsIgnoreCase("display")) return map.getDisplay();
                }
            }
            if (split[0].equalsIgnoreCase("t") || split[0].equalsIgnoreCase("team")) {
                List<UUID> members = TeamManager.getTeamMembers(p.getUniqueId());
                UUID captain = TeamManager.captain.get(p.getUniqueId());
                String membersName = "";
                for (UUID member : members) {
                    membersName += Bukkit.getOfflinePlayer(member).getName();
                }
                if (split[1].equalsIgnoreCase("captain"))
                    return captain != null ? Bukkit.getOfflinePlayer(captain).getName() : "无";
                if (split[1].equalsIgnoreCase("members")) return membersName;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
        return "unknown";
    }
}

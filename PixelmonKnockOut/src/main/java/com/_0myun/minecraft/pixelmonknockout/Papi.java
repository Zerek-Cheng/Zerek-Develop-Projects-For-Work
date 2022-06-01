package com._0myun.minecraft.pixelmonknockout;

import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerGameData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Papi extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "pixelmonknockout";
    }

    @Override
    public String getAuthor() {
        return "0MYUN";
    }

    @Override
    public String getVersion() {
        return "RELEASE";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        try {
            String[] split = params.split("_");
            //games_default_xxxxxx
            if (split.length < 3) return params;

            switch (split[0]) {
                case "games":
                    return getGames(params);
                case "playerdata":
                    return getPlayerData(params);
                case "playergamedata":
                    return getPlayerGameData(params);
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            return "undefined:" + params;
        }
        return "unknown";
    }

    public String getGames(String params) throws SQLException {
        String[] split = params.split("_");
        Games games = DB.games.queryForGame(split[1]);
        return Replacer.replace("!" + split[2] + "!", games);
    }

    public String getPlayerData(String params) throws SQLException {
        String[] split = params.split("_");
        PlayerData playerData = DB.playerData.queryForUUID(Bukkit.getOfflinePlayer(split[1]).getUniqueId());
        return Replacer.replace("!" + split[2] + "!", playerData);
    }

    public String getPlayerGameData(String params) throws SQLException {
        String[] split = params.split("_");
        PlayerGameData playerGameData = DB.playerGameData.queryForUUID(Bukkit.getOfflinePlayer(split[1]).getUniqueId(), split[2]);
        return Replacer.replace("!" + split[3] + "!", playerGameData);
    }
}

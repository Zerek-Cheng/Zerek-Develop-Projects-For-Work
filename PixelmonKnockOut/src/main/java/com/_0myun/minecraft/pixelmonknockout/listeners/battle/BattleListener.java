package com._0myun.minecraft.pixelmonknockout.listeners.battle;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.GameUtils;
import com._0myun.minecraft.pixelmonknockout.api.events.GameRoundQuitEvent;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerGameData;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import com._0myun.minecraft.pixelmonknockout.task.game.GameRoundThread;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Map;

public class BattleListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSystem(GameRoundQuitEvent e) {
        try {

            GameRoundThread round = e.getRound();
            Games games = round.getGame();
            Game config = games.getGameConfig();

            PlayerGameData data = DB.playerGameData.queryForUUID(e.getPlayer(), games.getGame());
            switch (e.getRank()) {
                case 1:
                    data.setFirst(data.getFirst() + 1);
                    Map reward = config.getRankAmountReward("win-first", data.getFirst());
                    if (reward != null)
                        GameUtils.runCmd(Bukkit.getPlayer(e.getPlayer()), (List<String>) reward.get("cmd"));
                    break;
                case 2:
                    data.setSecond(data.getSecond() + 1);
                    reward = config.getRankAmountReward("win-second", data.getSecond());
                    if (reward != null)
                        GameUtils.runCmd(Bukkit.getPlayer(e.getPlayer()), (List<String>) reward.get("cmd"));
                    break;
                case 3:
                    data.setThird(data.getThird() + 1);
                    reward = config.getRankAmountReward("win-third", data.getThird());
                    if (reward != null)
                        GameUtils.runCmd(Bukkit.getPlayer(e.getPlayer()), (List<String>) reward.get("cmd"));
                    break;
            }
            data.setTotal(data.getTotal() + 1);
            DB.playerGameData.update(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

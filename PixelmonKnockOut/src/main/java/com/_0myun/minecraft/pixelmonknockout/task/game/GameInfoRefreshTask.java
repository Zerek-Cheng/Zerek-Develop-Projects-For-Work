package com._0myun.minecraft.pixelmonknockout.task.game;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;

import java.util.List;

public class GameInfoRefreshTask implements Runnable {
    @Override
    public void run() {
        try {
            List<Games> games = DB.games.queryForStat(Games.Stat.WAIT, Games.Stat.PLAY);
            for (Games game : games) {
                List<PlayerData> playerData = DB.playerData.queryForGame(game.getId());
                if (game.getStat().equals(Games.Stat.WAIT))
                    game.setPlayerAmount(playerData.size());
                if (game.getStat().equals(Games.Stat.PLAY))
                    game.setAlive(playerData.size());
                DB.games.update(game);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

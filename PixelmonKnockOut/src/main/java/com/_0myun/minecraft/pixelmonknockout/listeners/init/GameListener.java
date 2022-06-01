package com._0myun.minecraft.pixelmonknockout.listeners.init;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.api.events.GameLoadEvent;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import com._0myun.minecraft.pixelmonknockout.task.game.GameStarter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class GameListener implements Listener {
    @EventHandler
    public void on(GameLoadEvent e) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(R.INSTANCE,
                () -> {
                    try {
                        Game game = e.getGame();
                        String nextTime = game.getNextStartTime(null);
                        List<Games> games = DB.games.queryForGame(game.getName(), Games.Stat.STOP, Games.Stat.WAIT, Games.Stat.PLAY, Games.Stat.FINISH);
                        for (Games sqlGame : games)//已有下次开始场次直接跳过
                            if (sqlGame.getStarTime().equalsIgnoreCase(nextTime) || !sqlGame.getStat().equals(Games.Stat.FINISH))
                                return;
                        Games sqlGame = new Games();
                        sqlGame.setGame(game.getName());
                        sqlGame.setStarTime(nextTime);
                        DB.games.createIfNotExists(sqlGame);
                        R.INSTANCE.getLogger().info(game.getDisplay() + "加载时下一场游戏初始化(" + nextTime + ")");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                , 0, 5 * 20
        );
        Bukkit.getScheduler().runTaskTimer(R.INSTANCE, new GameStarter(e.getGame()), 20L, 20L);
    }
}

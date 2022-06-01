package com._0myun.minecraft.pixelmonknockout.task.game;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.GameUtils;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 游戏时间检查任务
 * 到时见就开始锦标赛
 */
@Getter
@AllArgsConstructor
public class GameStarter implements Runnable {
    Game gameConfig;

    @Override
    public synchronized void run() {
        try {
            List<Games> gameList = DB.games.queryForGame(gameConfig.getName(), Games.Stat.WAIT);
            if (gameList.isEmpty()) {
                return;
            }
            Games game = gameList.get(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//2019年6月7日 20:00分
            if (format.parse(game.getStarTime()).after(new Date())) return;//时间未到

            GameUtils.start(game.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

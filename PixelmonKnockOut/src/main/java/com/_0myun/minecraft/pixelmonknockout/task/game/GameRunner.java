package com._0myun.minecraft.pixelmonknockout.task.game;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class GameRunner extends BukkitRunnable {

    Games game;
    List<UUID> players = new ArrayList<>();
    GameRoundThread round = null;

    public GameRunner(Games game) {
        this.game = game;
    }

    @Override
    public synchronized void run() {
        Game config = game.getGameConfig();
        try {
            game.setProgress(1);
            DB.games.update(game);

            for (PlayerData playerData : DB.playerData.queryForGame(game.getId(), PlayerData.Stat.NONE))//获取玩家UUID
                players.add(playerData.getUuid());
            R.INSTANCE.getLogger().info("锦标赛" + game.getGame() + "编号" + game.getId() + "开始！共" + players.size() + "人参赛");

            do {
                DB.games.refresh(game);

                GameRoundThread t = new GameRoundThread(this, game, new ArrayList<>(Arrays.asList(players.toArray(new UUID[0]))));//建立本轮战斗主线程
                this.round = t;
                t.setPriority(Thread.MAX_PRIORITY);
                t.start();
                t.join();
                this.players = t.getPlayersUUID();
                if (this.players.size() <= 0) break;
                if (t.isInterrupted()) {
                    R.INSTANCE.getLogger().warning("锦标赛" + game.getGame() + "编号" + game.getId() + "第" + game.getProgress() + "轮！" +
                            "余" + players.size() + "人.出现异常！！重置回合！！");
                    continue;
                }

                DB.games.refresh(game);
                game.setProgress(game.getProgress() + 1);
                DB.games.update(game);

                R.INSTANCE.getLogger().info("锦标赛" + game.getGame() + "编号" + game.getId() + "第" + game.getProgress() + "轮结束！" +
                        "余" + players.size() + "人");

                game.broadcastGame(String.format(R.INSTANCE.lang("lang9"), game.getProgress()));//x轮战斗结束通知

            } while (players.size() > 0);
            //全部战斗完成
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public BukkitTask getTask() throws Exception {
        Field task = this.getClass().getDeclaredField("task");
        task.setAccessible(false);
        return (BukkitTask) task.get(this);
    }
}

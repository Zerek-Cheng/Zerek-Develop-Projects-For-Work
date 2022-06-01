package com._0myun.minecraft.pixelmonknockout.listeners.battle;

import catserver.api.bukkit.event.ForgeEvent;
import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.GameUtils;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerGameData;
import com._0myun.minecraft.pixelmonknockout.task.game.GameRoundThread;
import com._0myun.minecraft.pixelmonknockout.task.game.GameRunner;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PixelmonBattleListener implements Listener {
    public static List<Integer> indexes = new ArrayList<>();

    @EventHandler
    public void on(ForgeEvent fe) {
        try {

            if (!(fe.getForgeEvent() instanceof BattleEndEvent)) return;
            BattleEndEvent e = (BattleEndEvent) fe.getForgeEvent();

            BattleControllerBase battle = e.bc;
            if (!indexes.contains(battle.battleIndex)) return;
            PlayerParticipant team1 = (PlayerParticipant) battle.participants.get(0);
            PlayerParticipant team2 = (PlayerParticipant) battle.participants.get(1);
            Player winnerPlayer = null;
            Player failedPlayer = null;

            PlayerData playerData = DB.playerData.queryForUUID(team1.player.getBukkitEntity().getUniqueId());
            Games games = playerData.getGames();
            GameRunner runner = R.INSTANCE.getRunners().get(playerData.getGame());
            GameRoundThread round = runner.getRound();
            int needWin = games.getGameConfig().getGameMode().equalsIgnoreCase("w2") ? 2 : 1;

            for (Map.Entry<BattleParticipant, BattleResults> set : e.results.entrySet()) {
                BattleParticipant a = set.getKey();
                BattleResults b = set.getValue();
                try {
                    PlayerParticipant participant = (PlayerParticipant) a;
                    Player p = participant.player.getBukkitEntity();
                    if (round.getBattles().get(p.getUniqueId()) != battle) return;//不是这一次的战斗

                    switch (b) {
                        case VICTORY:
                            winnerPlayer = p;
                            Integer win = round.getWin().get(p.getUniqueId());
                            round.getWin().put(p.getUniqueId(), win == null ? 1 : win + 1);
                            games.broadcastGame(String.format(R.INSTANCE.lang("lang22"), p.getName()));

                            GameUtils.runCmd(winnerPlayer, games.getGameConfig().getCmdWin());
                            break;
                        case FLEE://逃离
                        case DRAW://平局？
                        case DEFEAT://失败
                            failedPlayer = p;
                            games.broadcastGame(String.format(R.INSTANCE.lang("lang23"), p.getName()));
                            GameUtils.runCmd(p, games.getGameConfig().getCmdFail());
                            break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            indexes.remove((Integer) battle.battleIndex);

            if (games.getGameConfig().getGameMode().equalsIgnoreCase("normal")) {
                round.quit(failedPlayer.getUniqueId(), round.getPlayersUUID().size());

                round.getBattleUUID().remove(winnerPlayer.getUniqueId());
                round.getBattleUUID().remove(failedPlayer.getUniqueId());
                battle.endPause();
                battle.endBattle();
            } else {

                if (winnerPlayer != null && round.getWin().get(winnerPlayer.getUniqueId()) >= needWin) {//胜利后清算  唯一结束入口
                    round.getBattleUUID().remove(team1.player.getBukkitEntity().getUniqueId());
                    round.getBattleUUID().remove(team2.player.getBukkitEntity().getUniqueId());
                    UUID failer = null;
                    //干掉总成绩失败的玩家
                    if (!winnerPlayer.getUniqueId().equals(team1.player.getBukkitEntity().getUniqueId()))
                        failer = team1.player.getBukkitEntity().getUniqueId();
                    if (!winnerPlayer.getUniqueId().equals(team2.player.getBukkitEntity().getUniqueId()))
                        failer = team2.player.getBukkitEntity().getUniqueId();
                    round.quit(failer, round.getPlayersUUID().size());
                    games.broadcastGame(String.format(R.INSTANCE.lang("lang24"), Bukkit.getOfflinePlayer(failer).getName()));//最终淘汰

                    GameUtils.runCmd(Bukkit.getPlayer(failer), games.getGameConfig().getCmdFail());

                } else {//没有胜利者或胜利局数不达标直接开始下一局
                    battle.endPause();
                    battle.endBattle();
                    battle.endPause();
                    if (winnerPlayer == null) {
                        round.quit(team1.player.getBukkitEntity().getUniqueId());
                        round.quit(team2.player.getBukkitEntity().getUniqueId());
                        GameUtils.runCmd(team2.player.getBukkitEntity(), games.getGameConfig().getCmdFail());
                        return;
                    }
                    round.getNeedBattle().put(team1.player.getBukkitEntity().getUniqueId(), team2.player.getBukkitEntity().getUniqueId());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

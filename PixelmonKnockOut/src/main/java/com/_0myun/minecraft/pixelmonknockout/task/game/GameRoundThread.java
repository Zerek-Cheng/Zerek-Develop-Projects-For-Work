package com._0myun.minecraft.pixelmonknockout.task.game;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.GameUtils;
import com._0myun.minecraft.pixelmonknockout.KPlayer;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.api.events.GameRoundQuitEvent;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import com._0myun.minecraft.pixelmonknockout.listeners.battle.PixelmonBattleListener;
import com._0myun.minecraft.pixelmonknockout.party.SingleParty;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
public class GameRoundThread extends Thread {
    GameRunner runner;
    Games game;
    /**
     * 这里的player是本轮仍未战斗完成的玩家
     * 只有所有人战斗结束上层runner才可以开始下一轮的战斗
     */
    List<UUID> playersUUID;
    List<UUID> readyUUID = new ArrayList<>();
    List<UUID> battleUUID = new ArrayList<>();
    List<UUID> battleStartUUID = new ArrayList<>();

    HashMap<UUID, BattleControllerBase> battles = new HashMap<>();
    HashMap<UUID, Integer> win = new HashMap<>();
    HashMap<UUID, UUID> needBattle = new HashMap<>();

    public GameRoundThread(GameRunner runner, Games game, List<UUID> playersUUID) {
        this.runner = runner;
        this.game = game;
        this.playersUUID = playersUUID;
    }

    @Override
    public void run() {
        if (this.getPlayersUUID().size() == 1) {
            quit(this.getPlayersUUID().get(0), 1);
            return;
        }
        readyUUID = new ArrayList<>(Arrays.asList(Arrays.copyOf(playersUUID.toArray(new UUID[0]), playersUUID.size())));//未准备UUID初始化
        try {
            Game config = game.getGameConfig();
            game.broadcastGame(String.format(R.INSTANCE.lang("lang14"), config.getRoundWait()));
            for (int i = 0; i < config.getRoundWait(); i++) {//等待准备
                Thread.sleep(1000);
                if (readyUUID.size() == 0) break;
            }
            List<UUID> tmp = new ArrayList<>();
            if (this.getGame().getProgress() == 1) {//第一轮没准备才会被淘汰
                for (UUID notReadyUUID : readyUUID) {
                    game.broadcastGame(String.format(R.INSTANCE.lang("lang25"), Bukkit.getOfflinePlayer(notReadyUUID).getName()));//超时未准备淘汰提示
                    tmp.add(notReadyUUID);
                }
            }
            for (UUID uuid : tmp) {
                quit(uuid);
            }

            tmp.clear();

            game.broadcastGame(String.format(R.INSTANCE.lang("lang17"), this.getPlayersUUID().size()));//还有X名选手参赛

            battleStartUUID = new ArrayList<>(Arrays.asList(playersUUID.toArray(new UUID[0])));//未开始战斗UUID初始化
            battleUUID = new ArrayList<>(Arrays.asList(Arrays.copyOf(playersUUID.toArray(new UUID[0]), playersUUID.size())));//未战斗完成UUID初始化

            while (battleStartUUID.size() > 0) {//             全部对战完成就不分配了
                synchronized (this) {
                    if (battleStartUUID.size() == 1) {       //单数时随机抽出一人晋级  由于随时可能有人退出就放在里面
                        UUID luckWiner = battleStartUUID.get(new Random().nextInt(battleStartUUID.size()));
                        battleStartUUID.remove(luckWiner);
                        battleUUID.remove(luckWiner);
                        Player kp = new KPlayer(luckWiner).getPlayer();
                        kp.sendMessage(R.INSTANCE.lang("lang18"));
                        game.broadcastGame(String.format(R.INSTANCE.lang("lang19"), kp.getName()));
                        continue;
                    }
                }
                UUID p1 = battleStartUUID.get(new Random().nextInt(battleStartUUID.size()));
                battleStartUUID.remove(p1);
                UUID p2 = battleStartUUID.get(new Random().nextInt(battleStartUUID.size()));
                battleStartUUID.remove(p2);
                needBattle.put(p1, p2);
                game.broadcastGame(String.format(R.INSTANCE.lang("lang21")
                        , Bukkit.getOfflinePlayer(p1).getName()
                        , Bukkit.getOfflinePlayer(p2).getName()
                ));
                //battle(p1, p2);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    List<UUID> success = new ArrayList<>();
                    for (Map.Entry<UUID, UUID> entry : getNeedBattle().entrySet()) {
                        UUID p1 = entry.getKey();
                        UUID p2 = entry.getValue();
                        success.add(p1);
                        battle(p1, p2);
                    }
                    for (UUID uuid : success) {
                        getNeedBattle().remove(uuid);
                    }
                }
            }.runTaskTimerAsynchronously(R.INSTANCE, 20l, 20l);

            int roundTime = 0;
            while (battleUUID.size() > 0) {//最大对战时间
                Thread.sleep(1000);
                roundTime++;
                if (roundTime >= config.getRoundMaxTime()) {//回合超时
                    for (UUID timeout : battleStartUUID) {
                        if (getBattles().containsKey(timeout))
                            getBattles().get(timeout).endBattle();
                        quit(timeout, this.getPlayersUUID().size());
                    }
                    game.broadcastGame(R.INSTANCE.lang("lang20"));//全部淘汰提示
                    break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 开始战斗,检查各种规则,发现违规直接判输,两人都违规直接都输强制退赛
     */
    public void battle(UUID p1, UUID p2) {
/*        if (!GameUtils.checkSameId(p1)) {
            Bukkit.getPlayer(p1).sendMessage(R.INSTANCE.lang("lang29"));
            quit(p1);
            getBattleUUID().remove(p1);
            getBattleUUID().remove(p2);
            return;
        }*/
        if (!GameUtils.checkBaned(p1, game.getGameConfig().getPokemonBan())) {
            Bukkit.getPlayer(p1).sendMessage(R.INSTANCE.lang("lang30"));
            quit(p1, this.getPlayersUUID().size());
            getBattleUUID().remove(p1);
            getBattleUUID().remove(p2);
            return;
        }/*
        if (!GameUtils.checkSameId(p2)) {
            Bukkit.getPlayer(p2).sendMessage(R.INSTANCE.lang("lang29"));
            quit(p2);
            getBattleUUID().remove(p1);
            getBattleUUID().remove(p2);
            return;
        }*/
        if (!GameUtils.checkBaned(p2, game.getGameConfig().getPokemonBan())) {
            Bukkit.getPlayer(p2).sendMessage(R.INSTANCE.lang("lang30"));
            quit(p2, this.getPlayersUUID().size());
            getBattleUUID().remove(p1);
            getBattleUUID().remove(p2);
            return;
        }

        HashMap<UUID, BattleControllerBase> battles = getBattles();
        if (battles.containsKey(p1)) battles.get(p1).endBattle();
        if (battles.containsKey(p2)) battles.get(p2).endBattle();
        BattleControllerBase controller = BattleRegistry.startBattle(new SingleParty(p1).createParticipant()
                , new SingleParty(p2).createParticipant()
                , this.getGame().getGameConfig().getRules());
        if (controller == null) return;
        PixelmonBattleListener.indexes.add(controller.battleIndex);

        this.battles.put(p1, controller);
        this.battles.put(p2, controller);
    }

    public boolean ready(UUID uuid) {
        return readyUUID.remove(uuid);
    }

    public synchronized void quit(UUID uuid, Integer rank) {
        try {
            if (rank != null) {
                this.getGame().broadcastGame(String.format(R.INSTANCE.lang("lang31"), Bukkit.getOfflinePlayer(uuid).getName(), rank));
                List<String> cmds = this.getGame().getGameConfig().getReward(rank);
                if (cmds != null)
                    GameUtils.runCmd(Bukkit.getPlayer(uuid), cmds);
            }
            this.getPlayersUUID().remove(uuid);//直接淘汰
            this.getBattleStartUUID().remove(uuid);//直接不允许开始战斗
            this.getBattleUUID().remove(uuid);//直接战斗完成
            this.getReadyUUID().remove(uuid);//直接不考虑准备
            this.getWin().remove(uuid);

            this.getBattles().remove(uuid);
            PlayerData playerData = DB.playerData.queryForUUID(uuid);
            playerData.setGame(-1);
            DB.playerData.update(playerData);

            Bukkit.getPluginManager().callEvent(new GameRoundQuitEvent(this, uuid, rank));
            System.out.println("强制退出" + uuid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 玩家未及时准备或强制退出调用本方法
     *
     * @param uuid
     */
    public synchronized void quit(UUID uuid) {
        quit(uuid, null);
    }
}

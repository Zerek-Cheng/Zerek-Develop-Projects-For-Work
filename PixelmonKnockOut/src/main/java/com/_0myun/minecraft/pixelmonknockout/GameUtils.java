package com._0myun.minecraft.pixelmonknockout;

import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import com._0myun.minecraft.pixelmonknockout.task.game.GameRunner;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameUtils {

    public static void join(Player p, String gameName) throws SQLException {
        PlayerData playerData = DB.playerData.queryForUUID(p.getUniqueId());
        if (playerData.getGame() != -1) {//已经报名比赛
            p.sendMessage(R.INSTANCE.lang("lang5"));
            return;
        }
        if (R.INSTANCE.getGame(gameName) == null) {//游戏不存在
            p.sendMessage(R.INSTANCE.lang("lang1"));
            return;
        }
        Games game = DB.games.queryForGame(gameName);
        if (game == null) {//场次未初始化
            p.sendMessage(R.INSTANCE.lang("lang3"));
            return;
        }
        if (!game.getStat().equals(Games.Stat.WAIT)) {
            p.sendMessage(R.INSTANCE.lang("lang12"));
            return;
        }
        if (game.getPlayerAmount() >= game.getGameConfig().getMaxPlayer()) {
            p.sendMessage(R.INSTANCE.lang("lang26"));
            return;
        }
        playerData.setGame(game.getId());
        DB.playerData.update(playerData);
        p.sendMessage(R.INSTANCE.lang("lang4"));
    }

    public static void leave(Player p) throws SQLException {
        PlayerData playerData = DB.playerData.queryForUUID(p.getUniqueId());
        try {
            Games games = playerData.getGames();

            if (games.getStat().equals(Games.Stat.PLAY)) {
                p.sendMessage(R.INSTANCE.lang("lang8"));
                return;
            }
            playerData.setGame(-1);
            DB.playerData.update(playerData);
            p.sendMessage(R.INSTANCE.lang("lang6"));

        } catch (Exception ex) {
            playerData.setGame(-1);
            DB.playerData.update(playerData);
            p.sendMessage(R.INSTANCE.lang("lang6"));
        }
    }

    public synchronized static void start(int game) {
        try {
            Games games = DB.games.queryForId(game);
            DB.games.refresh(games);
            if (games.getStat().equals(Games.Stat.PLAY)) return;
            games.setStat(Games.Stat.PLAY);
            DB.games.update(games);

            Game gameConfig = games.getGameConfig();
            if (games.getPlayerAmount() < gameConfig.getMinPlayer()) {//玩家不足
                games.broadcastGame(R.INSTANCE.lang("lang10"));
                GameUtils.finish(game);
                return;
            }
            GameRunner runner = new GameRunner(games);
            Bukkit.getScheduler().runTaskAsynchronously(R.INSTANCE, runner);
            R.INSTANCE.getRunners().put(games.getId(), runner);

            games.broadcastGame(R.INSTANCE.lang("lang7"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void finish(int gameId) {
        try {
            Games game = DB.games.queryForId(gameId);
            game.setStat(Games.Stat.FINISH);
            DB.games.update(game);
            for (PlayerData playerData : DB.playerData.queryForGame(gameId)) {
                playerData.setGame(-1);
                DB.playerData.update(playerData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void runCmd(Player p, List<String> cmds) {
        boolean op = p.isOp();
        try {
            if (!op) p.setOp(true);
            for (String cmd : cmds) {
                p.performCommand(cmd.replace("!player!", p.getName()));
            }
        } finally {
            if (!op) p.setOp(false);
        }
    }

    public static boolean checkSameId(UUID uuid) {
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(uuid);
        List<Integer> ids = new ArrayList<>();
        Pokemon[] pokemons = party.getAll();
        for (Pokemon pokemon : pokemons) {
            if (pokemon == null) continue;
            int id = pokemon.getSpecies().getNationalPokedexInteger();
            if (ids.contains(id)) return false;
            ids.add(id);
        }
        return true;
    }

    public static boolean checkBaned(UUID uuid, List<String> names) {
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(uuid);
        Pokemon[] pokemons = party.getAll();
        for (Pokemon pokemon : pokemons) {
            if (pokemon == null) continue;
            String name = pokemon.getSpecies().getLocalizedName();
            if (names.contains(name)) return false;
        }
        return true;
    }

}

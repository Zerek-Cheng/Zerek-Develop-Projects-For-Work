package net.ginyai.poketrainerrank.core.data;

import net.ginyai.poketrainerrank.api.util.Tuple;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.battle.Rank;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.battle.Reward;
import net.ginyai.poketrainerrank.core.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    private transient final RankSeason season;
    private final UUID uuid;
    private String name;
    private int score = -1;
    private int highestScore;
    private transient int pos;
    private int highestPos;
    private int battles;
    private int win, lose;
    private boolean block;

    PlayerData(RankSeason season, UUID uuid, String name) {
        this.season = season;
        this.uuid = uuid;
        this.name = name;
    }

    PlayerData(RankSeason season, UUID uuid, String name, int score, int highestScore, int pos, int highestPos, int battles, int win, int lose, boolean block) {
        this.season = season;
        this.uuid = uuid;
        this.name = name;
        this.score = score;
        this.highestScore = highestScore;
        this.pos = pos;
        this.highestPos = highestPos;
        this.battles = battles;
        this.win = win;
        this.lose = lose;
        this.block = block;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public RankSeason getSeason() {
        return season;
    }

    public Rank getRank() {
        return season.getRank(score);
    }

    public Rank getHighestRank() {
        return season.getRank(highestScore);
    }

    public int getPos() {
        return pos;
    }

    public int getHighestPos() {
        return highestPos;
    }

    public int getBattles() {
        return battles;
    }

    public int getWin() {
        return win;
    }

    public int getLose() {
        return lose;
    }

    PlayerData setName(String name) {
        this.name = name;
        return this;
    }

    synchronized PlayerData addScore(int add) {
        return updateScore(score + add);
    }

    synchronized PlayerData updateScore(int score) {
        score = Math.min(season.getMaxScore(), Math.max(0, score));
        List<Reward> rewards = new ArrayList<>();
        if (score > 0 && score > this.score) {
            for (Rank rank : season.getRanks()) {
                if (rank.getScore() > score) {
                    break;
                }
                if (rank.getScore() > this.score) {
                    rewards.add(rank.getReachReward());
                }
            }
        }
        this.score = score;
        if (score > 0 && score > highestScore) {
            for (Rank rank : season.getRanks()) {
                if (rank.getScore() > score) {
                    break;
                }
                if (rank.getScore() > this.highestScore) {
                    rewards.add(rank.getFirstReachReward());
                }
            }
            this.highestScore = score;
        }
        if (!rewards.isEmpty()) {
            PokeTrainerRankMod.instance.execute(() ->
                    Utils.getPlayer(uuid).ifPresent(
                            player -> {
                                for (Reward reward : rewards) {
                                    reward.apply(player, season);
                                }
                            }
                    ));
        }
        return this;
    }

    PlayerData setHighestScore(int score) {
        this.highestScore = score;
        return this;
    }


    PlayerData updatePos(int pos) {
        this.pos = pos;
        if (pos < this.highestPos) {
            List<Reward> rewards = new ArrayList<>();
            for (Tuple<Integer, Reward> tuple : season.getTopRewards()) {
                if (tuple.getFirst() >= highestPos) {
                    continue;
                }
                if (tuple.getFirst() < pos) {
                    break;
                }
                rewards.add(tuple.getSecond());
            }
            this.highestPos = pos;
            if (!rewards.isEmpty()) {
                PokeTrainerRankMod.instance.execute(() ->
                        Utils.getPlayer(uuid).ifPresent(
                                player -> {
                                    for (Reward reward : rewards) {
                                        reward.apply(player, season);
                                    }
                                }
                        ));
            }
        }
        return this;
    }

    PlayerData setHighestPos(int pos) {
        this.highestPos = pos;
        return this;
    }


    synchronized PlayerData addBattles(int add) {
        this.battles += add;
        return this;
    }

    synchronized PlayerData setBattles(int battles) {
        this.battles = battles;
        return this;
    }

    synchronized PlayerData addWin(int add) {
        this.win += add;
        return this;
    }

    synchronized PlayerData setWin(int win) {
        this.win = win;
        return this;
    }

    synchronized PlayerData addLose(int add) {
        this.lose += add;
        return this;
    }

    synchronized PlayerData setLose(int lose) {
        this.lose = lose;
        return this;
    }

    public boolean isBlock() {
        return block;
    }

    PlayerData block(boolean flag) {
        this.block = flag;
        return this;
    }
}

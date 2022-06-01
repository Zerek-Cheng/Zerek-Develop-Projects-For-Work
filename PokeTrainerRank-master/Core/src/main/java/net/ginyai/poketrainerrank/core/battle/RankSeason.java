package net.ginyai.poketrainerrank.core.battle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import net.ginyai.poketrainerrank.api.util.Tuple;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.ginyai.poketrainerrank.core.data.SeasonDataManager;
import net.ginyai.poketrainerrank.core.util.Utils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import static net.ginyai.poketrainerrank.core.util.Utils.copy;

public class RankSeason {
    private final String name;
    private final boolean enable;
    private final int maxScore;
    private final String permission;
    private final BattleRules battleRules;
    private final ImmutableList<PokemonSpec> blocks;
    private final ImmutableSet<Integer> teamSize;
    private final int partySize; //0 单人 1 双人 2都可以
    private final Rank defaultRank;
    private final ImmutableList<Rank> ranks;
    private final SeasonDataManager dataManager;
    private final ImmutableList<Tuple<Integer, Reward>> topRewards;
    private List<PlayerData> tops = Collections.emptyList();

    private RankSeason(String name, boolean enable, int maxScore, String permission, BattleRules battleRules, ImmutableList<PokemonSpec> blocks, ImmutableSet<Integer> teamSize, int partySize, Rank defaultRank, ImmutableList<Rank> ranks, ImmutableList<Tuple<Integer, Reward>> topRewards) {
        this.name = Objects.requireNonNull(name);
        this.enable = enable;
        this.maxScore = maxScore;
        this.permission = permission;
        this.battleRules = battleRules;
        this.blocks = blocks;
        this.teamSize = teamSize;
        this.partySize = partySize;
        this.defaultRank = defaultRank;
        this.ranks = ranks;
        this.topRewards = topRewards;
        dataManager = PokeTrainerRankMod.instance.createDataManager(this);
        try {
            dataManager.init();
            updateTops();
        } catch (Exception e) {
            PokeTrainerRankMod.logger.error("Failed to init Data Manager", e);
        }
    }

    public String getName() {
        return name;
    }

    public boolean isEnable() {
        return enable;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public String getPermission() {
        return permission;
    }

    public BattleRules getBattleRules(boolean setToDouble) {
        return copy(battleRules, setToDouble);
    }

    public List<PokemonSpec> getBlocks() {
        return blocks;
    }

    public Set<Integer> getTeamSize() {
        return teamSize;
    }

    public int getPartySize() {
        return partySize;
    }

    public Rank getRank(int score) {
        if (score < 0) {
            return defaultRank;
        } else {
            Rank selected = defaultRank;
            for (Rank rank : ranks) {
                if (score >= rank.getScore()) {
                    selected = rank;
                }
            }
            return selected;
        }
    }

    public SeasonDataManager getDataManager() {
        return dataManager;
    }

    public ImmutableList<Rank> getRanks() {
        return ranks;
    }

    public ImmutableList<Tuple<Integer, Reward>> getTopRewards() {
        return topRewards;
    }

    public void updateTops() {
        dataManager.getTops(100).whenComplete(
                (tops, t) -> {
                    if (t != null) {
                        PokeTrainerRankMod.logger.error("Failed to get tops for " + name, t);
                    } else {
                        this.tops = tops;
                    }
                }
        );
    }

    @Nullable
    public PlayerData getTop(int p) {
        if (p >= 0 && p < tops.size()) {
            return tops.get(p);
        } else {
            return null;
        }
    }

    public List<PlayerData> getTops() {
        return tops;
    }

    public static RankSeason deserialize(ConfigurationNode node) throws ObjectMappingException {
        String name = node.getNode("name").getString();
        if (name == null) {
            throw new ObjectMappingException("Season name not set.");
        }
        boolean enable = node.getNode("enable").getBoolean(true);
        int maxScore = node.getNode("max-score").getInt(Integer.MAX_VALUE);
        String permission = node.getNode("permission").getString("");
        ImmutableList.Builder<PokemonSpec> builder = ImmutableList.builder();
        for (ConfigurationNode node1 : node.getNode("block-pixelmons").getChildrenList()) {
            if (node1.hasMapChildren()) {
                try {
                    builder.add(Utils.deserializeSpec(node1));
                } catch (IOException | ObjectMappingException e) {
                    throw new ObjectMappingException("Failed to load block-pixelmons in " + name, e);
                }
            } else {
                builder.add(new PokemonSpec(node1.getString("")));
            }
        }
        ImmutableList<PokemonSpec> blocks = builder.build();
        BattleRules battleRules = new BattleRules(node.getNode("rules").getString(""));
        ImmutableSet<Integer> teamsize = ImmutableSet.copyOf(node.getNode("teamsize").getList(TypeToken.of(Integer.class), Collections.emptyList()));
        List<Integer> party = node.getNode("partysize").getList(TypeToken.of(Integer.class));
        int partySize = 0;
        if (party.contains(2)) {
            if (party.contains(1)) {
                partySize = 2;
            } else {
                partySize = 1;
            }
        }
        Rank defaultRank = deserializeRank(node.getNode("defaultRank"), Rank.DEFAULT, -1);
        int order = 0;
        Rank lastRank = defaultRank;
        ImmutableList.Builder<Rank> rankBuilder = ImmutableList.builder();
        for (ConfigurationNode node1 : node.getNode("ranks").getChildrenList()) {
            Rank rank = deserializeRank(node1, defaultRank, order++);
            lastRank.setRankScore(rank.getScore() - lastRank.getScore());
            rankBuilder.add(rank);
            lastRank = rank;
        }
        ImmutableList<Rank> ranks = rankBuilder.build();
        ArrayList<Tuple<Integer, Reward>> list = new ArrayList<>();
        for (Map.Entry<Object, ? extends ConfigurationNode> entry : node.getNode("top-rewards").getChildrenMap().entrySet()) {
            list.add(new Tuple<>(Integer.parseInt(entry.getKey().toString()), Reward.deserialize(entry.getValue())));
        }
        list.sort(Comparator.<Tuple<Integer, Reward>>comparingInt(Tuple::getFirst).reversed());
        ImmutableList<Tuple<Integer, Reward>> topRewards = ImmutableList.copyOf(list);
        return new RankSeason(
                name, enable, maxScore, permission, battleRules, blocks, teamsize, partySize, defaultRank, ranks,
                topRewards);
    }

    private static Rank deserializeRank(ConfigurationNode node, Rank defaultRank, int order) throws ObjectMappingException {
        String name = node.getNode("name").getString(defaultRank.getName());
        String display = node.getNode("display").getString(defaultRank.getDisplayName());
        int score = node.getNode("score").getInt(defaultRank.getScore());
        MatchingRule matchingRule;
        if (node.getNode("matching").isVirtual()) {
            matchingRule = defaultRank.getMatchingRule();
        } else {
            matchingRule = new MatchingRule(node.getNode("matching", "score").getString(""), node.getNode("matching", "rank").getString(""));
        }
        Reward win = deserializeReward(node.getNode("win"), defaultRank::getWinReward);
        Reward lose = deserializeReward(node.getNode("lose"), defaultRank::getLoseReward);
        Reward reach = deserializeReward(node.getNode("reach"), defaultRank::getReachReward);
        Reward firstReach = deserializeReward(node.getNode("first-reach"), defaultRank::getFirstReachReward);
        return new Rank(order, name, display, score, matchingRule, win, lose, reach, firstReach);
    }

    private static Reward deserializeReward(ConfigurationNode node, Supplier<Reward> supplier) throws ObjectMappingException {
        return node.isVirtual() ? supplier.get() : Reward.deserialize(node);
    }
}

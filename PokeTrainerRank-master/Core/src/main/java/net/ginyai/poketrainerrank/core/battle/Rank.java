package net.ginyai.poketrainerrank.core.battle;

public class Rank {
    public static final Rank DEFAULT = new Rank(
            -1,"","",-1,
            new MatchingRule("", "1"),
            new Reward(1), new Reward(-1),
            new Reward(0), new Reward(0));

    private final transient int order;
    private final String name;
    private final String displayName;
    private final int score;
    private final MatchingRule matchingRule;
    private final Reward winReward;
    private final Reward loseReward;
    private final Reward reach;
    private final Reward firstReach;
    private transient int rankScore = -1;

    public Rank(int order, String name, String displayName, int score, MatchingRule matchingRule, Reward winReward, Reward loseReward, Reward reach, Reward firstReach) {
        this.order = order;
        this.name = name;
        this.displayName = displayName;
        this.score = score;
        this.matchingRule = matchingRule;
        this.winReward = winReward;
        this.loseReward = loseReward;
        this.reach = reach;
        this.firstReach = firstReach;
    }

    public int getRankScore() {
        return rankScore;
    }

    void setRankScore(int rankScore) {
        this.rankScore = rankScore;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getScore() {
        return score;
    }

    public MatchingRule getMatchingRule() {
        return matchingRule;
    }

    public Reward getWinReward() {
        return winReward;
    }

    public Reward getLoseReward() {
        return loseReward;
    }

    public Reward getReachReward() {
        return reach;
    }

    public Reward getFirstReachReward() {
        return firstReach;
    }
}

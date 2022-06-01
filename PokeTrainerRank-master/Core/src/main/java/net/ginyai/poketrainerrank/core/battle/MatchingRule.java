package net.ginyai.poketrainerrank.core.battle;

import net.ginyai.poketrainerrank.api.util.Tuple;
import net.ginyai.poketrainerrank.core.data.PlayerData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchingRule {

    private static final Pattern PATTERN = Pattern.compile("\\+([0-9]+);-([0-9]+)");

    private int scorePlus;
    private int scoreSub;

    private int rankPlus;
    private int rankSub;

    public MatchingRule(String score, String rank) {
        Tuple<Integer, Integer> sT = parse(score);
        scorePlus = sT.getFirst();
        scoreSub = sT.getSecond();
        Tuple<Integer, Integer> rT = parse(rank);
        rankPlus = rT.getFirst();
        rankSub = rT.getSecond();
    }

    private static Tuple<Integer, Integer> parse(String s) {
        Matcher matcher = PATTERN.matcher(s);
        if (matcher.matches()) {
            return new Tuple<>(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        } else {
            try {
                int i = Integer.parseInt(s);
                return new Tuple<>(i, i);
            } catch (NumberFormatException e) {
                return new Tuple<>(-1, -1);
            }
        }
    }

    public boolean matching(PlayerData data1, PlayerData data2) {
        return (scorePlus < 0 || data2.getScore() - data1.getScore() <= scorePlus)
                && (scoreSub < 0 || data1.getScore() - data2.getScore() <= scoreSub)
                && (rankPlus < 0 || data2.getRank().getOrder() - data1.getRank().getOrder() <= rankPlus)
                && (rankSub < 0 || data1.getRank().getOrder() - data2.getRank().getOrder() <= rankSub);
    }
}

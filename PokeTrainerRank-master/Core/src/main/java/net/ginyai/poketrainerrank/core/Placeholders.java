package net.ginyai.poketrainerrank.core;

import com.google.common.collect.ImmutableMap;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.PlayerData;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Placeholders {
    public static final Function<PlayerData, ?> ALL_SCORE = PlayerData::getScore;
    public static final Function<PlayerData, ?> HIGHEST_SCORE = PlayerData::getHighestScore;
    public static final Function<PlayerData, ?> SCORE = data -> data.getScore() - data.getRank().getScore();
    public static final Function<PlayerData, ?> RANK_SCORE = data -> data.getRank().getRankScore();
    public static final Function<PlayerData, ?> SCORE_TO_NEXT = data -> data.getRank().getRankScore() - data.getScore();
    public static final Function<PlayerData, ?> SCORE_RANK_PERCENT = data -> String.format("%.2f", (float) data.getScore() / data.getRank().getRankScore());
    public static final Function<PlayerData, ?> RANK = data -> data.getRank().getDisplayName();
    public static final Function<PlayerData, ?> HIGHEST_RANK = data -> data.getHighestRank().getDisplayName();
    public static final Function<PlayerData, ?> RANK_NAME = data -> data.getRank().getName();
    public static final Function<PlayerData, ?> RANK_ORDER = data -> data.getRank().getOrder();
    public static final Function<PlayerData, ?> POS = PlayerData::getPos;
    public static final Function<PlayerData, ?> HIGHEST_POS = PlayerData::getHighestPos;
    public static final Function<PlayerData, ?> WINS = PlayerData::getWin;
    public static final Function<PlayerData, ?> LOSES = PlayerData::getLose;
    public static final Function<PlayerData, ?> BATTLES = PlayerData::getBattles;
    public static final Function<PlayerData, ?> SEASON = data -> data.getSeason().getName();
    public static final Function<PlayerData, ?> PLAYER = PlayerData::getName;
    public static final Function<PlayerData, ?> BLOCK = PlayerData::isBlock;


    public static final ImmutableMap<String, Function<PlayerData, ?>> placeholders;
    private static final Pattern PLACEHOLDERS = Pattern.compile("(.+?)_(.+)");
    private static final Pattern TOP_PLAYER = Pattern.compile("(.+?)_([0-9]+)_(.+)");
    private static final Pattern PATTERN = Pattern.compile("\\{(.+?)}");

    static {
        ImmutableMap.Builder<String, Function<PlayerData, ?>> builder = ImmutableMap.builder();
        for (Field field : Placeholders.class.getFields()) {
            if (Function.class.isAssignableFrom(field.getType())) {
                try {
                    //noinspection unchecked
                    builder.put(field.getName().toLowerCase(), (Function<PlayerData, ?>) field.get(null));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        placeholders = builder.build();
    }

    public static String process(@Nullable UUID player, String string) {
        PlayerData data = null;
        String s = "";
        Matcher matcher = TOP_PLAYER.matcher(string);
        if (matcher.matches()) {
            String seasonName = matcher.group(1);
            int top = Integer.parseInt(matcher.group(2));
            s = matcher.group(3);
            for (RankSeason season : PokeTrainerRankMod.getSeasons()) {
                if (season.getName().equalsIgnoreCase(seasonName)) {
                    data = season.getTop(top - 1);
                }
            }
        } else if (player != null) {
            matcher = PLACEHOLDERS.matcher(string);
            if(matcher.matches()) {
                String seasonName = matcher.group(1);
                s = matcher.group(2);
                for (RankSeason season : PokeTrainerRankMod.getSeasons()) {
                    if (season.getName().equalsIgnoreCase(seasonName)) {
                        data = season.getDataManager().getDataFromCache(player);
                    }
                }
            }
        }
        if(data == null) {
            return "";
        }
        return process(data, s);
    }

    public static String process(@Nullable PlayerData data, String string) {
        if (data == null) {
            return "";
        }
        return String.valueOf(placeholders.getOrDefault(string.toLowerCase(), d -> "").apply(data));
    }

    public static String replaceAll(PlayerData data, String string) {
        Matcher matcher = PATTERN.matcher(string);
        StringBuffer builder = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            if (key != null) {
                String replacement;
                if (placeholders.containsKey(key)) {
                    replacement = String.valueOf(placeholders.get(key).apply(data));
                } else {
                    replacement = matcher.group();
                }
                matcher.appendReplacement(builder, replacement);
            }
        }
        return matcher.appendTail(builder).toString();
    }
}

package net.ginyai.poketrainerrank.core.battle;

import com.google.common.reflect.TypeToken;
import net.ginyai.poketrainerrank.api.IPlugin;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Reward {
    private int score;
    private List<String> commands;

    public Reward(int score) {
        this(score, Collections.emptyList());
    }

    public Reward(int score, List<String> commands) {
        this.score = score;
        this.commands = Objects.requireNonNull(commands);
    }

    public CompletableFuture<PlayerData> apply(EntityPlayerMP player, RankSeason season) {
        IPlugin plugin = PokeTrainerRankMod.getPlugin();
        String name = player.getName();
        //todo:map? 替换玩家占位符
        commands.stream().map(s -> s.replaceAll("\\{player}", name)).forEach(plugin::runConsoleCommand);
        return season.getDataManager().addScore(player.getUniqueID(),score);
    }

    public static Reward deserialize(ConfigurationNode node) throws ObjectMappingException {
        return new Reward(
                node.getNode("score").getInt(0),
                node.getNode("commands").getList(TypeToken.of(String.class),Collections.emptyList())
        );
    }
}

package net.ginyai.poketrainerrank.core.command;

import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.minecraft.command.CommandException;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandElement<T> {
    public static final CommandElement<RankSeason> SEASON = new CommandElement<RankSeason>() {
        @Override
        public RankSeason process(String arg) throws CommandException {
            return PokeTrainerRankMod.getSeasons().stream()
                    .filter(s->s.getName().equalsIgnoreCase(arg))
                    .findAny().orElseThrow(()->new CommandException("poketrainerrank.command.noSuchSeason"));
        }

        @Override
        public List<String> tabCompletions(String arg) {
            String prefix = arg.toLowerCase();
            return PokeTrainerRankMod.getSeasons().stream()
                    .map(RankSeason::getName)
                    .filter(s->s.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());
        }
    };

    public abstract T process(String arg) throws CommandException;

    public abstract List<String> tabCompletions(String arg);

}

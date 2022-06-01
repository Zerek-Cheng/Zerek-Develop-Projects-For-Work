package net.ginyai.poketrainerrank.core.events;

import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.UUID;

public class PTRPlayerScoreAddEvent extends Event {
    public UUID uuid;
    public int score;
    public RankSeason season;
}

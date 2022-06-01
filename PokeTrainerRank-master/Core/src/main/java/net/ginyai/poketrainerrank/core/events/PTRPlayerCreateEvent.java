package net.ginyai.poketrainerrank.core.events;

import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PTRPlayerCreateEvent extends Event {
    public PlayerData player = null;
    public RankSeason season = null;
}

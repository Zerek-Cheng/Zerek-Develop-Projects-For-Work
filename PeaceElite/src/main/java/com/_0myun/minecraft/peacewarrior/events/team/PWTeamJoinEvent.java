package com._0myun.minecraft.peacewarrior.events.team;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.util.UUID;

public class PWTeamJoinEvent extends PlayerEvent {
    static HandlerList handlers = new HandlerList();
    UUID join;

    public PWTeamJoinEvent(Player p, UUID join) {
        super(p);
        this.join = join;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

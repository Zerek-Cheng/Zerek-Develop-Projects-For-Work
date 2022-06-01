package com._0myun.minecraft.peacewarrior.events.team;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PWTeamDissoluteEvent extends PlayerEvent {
    static HandlerList handlers = new HandlerList();
    public PWTeamDissoluteEvent(Player p) {
        super(p);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() { return handlers; }
}

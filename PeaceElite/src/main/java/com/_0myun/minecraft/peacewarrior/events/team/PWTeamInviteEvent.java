package com._0myun.minecraft.peacewarrior.events.team;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.util.UUID;

@Data
public class PWTeamInviteEvent extends PlayerEvent {
    static HandlerList handlers = new HandlerList();
    UUID invite;

    public PWTeamInviteEvent(Player p, UUID member) {
        super(p);
        this.invite = member;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

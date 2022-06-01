package com._0myun.minecraft.peacewarrior.events.battle;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Data
public class PWBattleFlyEvent extends PlayerEvent {
    static HandlerList handlers = new HandlerList();
    public PWBattleFlyEvent(Player p) {
        super(p);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() { return handlers; }
}

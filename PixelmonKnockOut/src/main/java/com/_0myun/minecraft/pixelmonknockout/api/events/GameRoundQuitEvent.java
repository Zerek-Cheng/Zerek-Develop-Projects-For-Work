package com._0myun.minecraft.pixelmonknockout.api.events;

import com._0myun.minecraft.pixelmonknockout.task.game.GameRoundThread;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
public class GameRoundQuitEvent extends Event {
    static HandlerList handlerList = new HandlerList();

    GameRoundThread round;
    UUID player;
    Integer rank;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}

package com._0myun.minecraft.pixelmonknockout.api.events;

import com._0myun.minecraft.pixelmonknockout.data.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
@AllArgsConstructor
@Builder
@Data
public class GameLoadEvent extends Event {
    static HandlerList handlerList = new HandlerList();
    Game game;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}

package com._0myun.minecraft.peacewarrior.events.battle;

import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import lombok.Data;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Data
public class PWBattleReadyEvent extends Event {
    static HandlerList handlers = new HandlerList();
    Battle battle;

    public PWBattleReadyEvent(Battle battle) {
        this.battle = battle;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

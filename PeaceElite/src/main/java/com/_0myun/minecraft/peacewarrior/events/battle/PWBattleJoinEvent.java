package com._0myun.minecraft.peacewarrior.events.battle;

import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Data
public class PWBattleJoinEvent extends PlayerEvent implements Cancellable {
    static HandlerList handlers = new HandlerList();
    Battle battle;
    boolean cancelled;

    public PWBattleJoinEvent(Player p, Battle battle) {
        super(p);
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

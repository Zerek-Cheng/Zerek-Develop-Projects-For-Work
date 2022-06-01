package com._0myun.minecraft.peacewarrior.events;

import com._0myun.minecraft.peacewarrior.R;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.PluginEvent;

public class PWPluginInitEvent extends PluginEvent {
    static HandlerList handlerList = new HandlerList();

    public PWPluginInitEvent() {
        super(R.INSTANCE);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() { return handlerList; }
}

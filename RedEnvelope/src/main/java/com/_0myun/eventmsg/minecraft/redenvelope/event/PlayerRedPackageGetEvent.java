package com._0myun.eventmsg.minecraft.redenvelope.event;

import com._0myun.eventmsg.minecraft.redenvelope.bin.RedPackage;
import lombok.Data;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Data
public class PlayerRedPackageGetEvent extends Event {
    private static HandlerList handles = new HandlerList();
    private RedPackage redPackage;
    private boolean cancel;
    private int amount;
    private int index;

    public static HandlerList getHandlerList() {
        return handles;
    }

    @Override
    public HandlerList getHandlers() {
        return handles;
    }
}

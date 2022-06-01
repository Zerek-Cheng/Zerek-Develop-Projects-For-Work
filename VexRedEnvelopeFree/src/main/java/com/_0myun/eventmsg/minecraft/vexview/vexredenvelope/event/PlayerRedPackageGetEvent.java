package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.event;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.RedPackage;
import lombok.Data;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

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

package com._0myun.eventmsg.minecraft.redenvelope.event;

import com._0myun.eventmsg.minecraft.redenvelope.bin.RedPackage;
import lombok.Data;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Data
public class PlayerRedPackageSendEvent extends Event {
    private static HandlerList handles = new HandlerList();
    private String word;
    private RedPackage redPackage;
    private UUID owner;
    private boolean cancel;

    public static HandlerList getHandlerList() {
        return handles;
    }

    @Override
    public HandlerList getHandlers() {
        return handles;
    }
}

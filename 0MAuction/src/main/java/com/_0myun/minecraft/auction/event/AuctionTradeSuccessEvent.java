package com._0myun.minecraft.auction.event;

import com._0myun.minecraft.auction.table.Orders;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AuctionTradeSuccessEvent extends Event implements Cancellable {
    public static final HandlerList handlers = new HandlerList();

    @Getter
    Orders order;
    @Getter
    @Setter
    int take;
    @Getter
    @Setter
    int give;
    boolean cancel;

    public AuctionTradeSuccessEvent(Orders order, int take, int give) {
        this.order = order;
        this.take = take;
        this.give = give;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

}
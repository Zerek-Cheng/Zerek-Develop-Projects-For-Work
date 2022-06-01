package com._0myun.minecraft.auction.event;

import com._0myun.minecraft.auction.table.Orders;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class AuctionSellEvent extends PlayerEvent implements Cancellable {
    public static final HandlerList handlers = new HandlerList();

    @Getter
    Orders order;
    @Getter
    SellType type;
    boolean cancel;

    public AuctionSellEvent(Player p, Orders order, SellType type) {
        super(p);
        this.order = order;
        this.type = type;
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

    public enum SellType {
        FIXED_PRICE(0), AUCTION(1);
        int typeData;

        SellType(int type) {
            this.typeData = type;
        }

        @Override
        public String toString() {
            return String.valueOf(typeData);
        }
    }
}
package com._0myun.minecraft.auction.event;

import com._0myun.minecraft.auction.godtype.GoodType;
import com._0myun.minecraft.auction.payway.Payway;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class AuctionPreSellEvent extends PlayerEvent implements Cancellable {
    public static final HandlerList handlers = new HandlerList();
    @Getter
    Payway payway;
    @Getter
    String data;
    @Getter
    @Setter
    int timeout;
    @Getter
    @Setter
    int price;
    @Getter
    @Setter
    int startPrice;
    @Getter
    GoodType type;
    boolean cancel;

    public AuctionPreSellEvent(Player p, Payway payway, String data, int timeout, int price, int startPrice, GoodType type) {
        super(p);
        this.payway = payway;
        this.data = data;
        this.timeout = timeout;
        this.price = price;
        this.startPrice = startPrice;
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
}

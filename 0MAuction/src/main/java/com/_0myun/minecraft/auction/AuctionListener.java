package com._0myun.minecraft.auction;

import com._0myun.minecraft.auction.event.AuctionPreSellEvent;
import com._0myun.minecraft.auction.event.AuctionTradeSuccessEvent;
import com._0myun.minecraft.auction.payway.Payway;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class AuctionListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void preSell(AuctionPreSellEvent e) {
        Player p = e.getPlayer();
        int timeout = e.getTimeout();
        if (!ConfigUtils.canSell(p)) {
            p.sendMessage(LangManager.getLang("lang6"));
            e.setCancelled(true);
            return;
        }
        if (e.getPrice() > ConfigUtils.getMaxPrice() ||
                e.getStartPrice() > ConfigUtils.getMaxStartPrice()) {
            p.sendMessage(LangManager.getLang("lang18"));
            e.setCancelled(true);
            return;
        }
        Payway payway = e.getPayway();
        if (!payway.has(e.getPlayer().getName(), ConfigUtils.getStartSellFee())) {
            p.sendMessage(String.format(LangManager.getLang("lang8"), String.valueOf(ConfigUtils.getStartSellFee())));
            e.setCancelled(true);
            return;
        }
        payway.take(e.getPlayer().getName(), ConfigUtils.getStartSellFee());
        p.sendMessage(String.format(LangManager.getLang("lang9"), String.valueOf(ConfigUtils.getStartSellFee())));

        if (timeout > ConfigUtils.getTimeMax(ConfigUtils.getGroup(p))) {
            p.sendMessage(LangManager.getLang("lang7"));
            e.setTimeout(ConfigUtils.getTimeMax(ConfigUtils.getGroup(p)));
        }
    }

    @EventHandler
    public void onSuccess(AuctionTradeSuccessEvent e) {
        if (ConfigUtils.getTradeSuccessPriceMode() == 1) {
            e.setGive(Double.valueOf(e.getGive() * (1 - ConfigUtils.getTradeSuccessPrice())).intValue());
        } else if (ConfigUtils.getTradeSuccessPriceMode() == 2) {
            e.setGive(e.getGive() - Double.valueOf(ConfigUtils.getTradeSuccessPrice()).intValue());
        }
    }
}

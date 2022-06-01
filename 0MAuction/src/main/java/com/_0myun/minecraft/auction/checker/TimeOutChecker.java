package com._0myun.minecraft.auction.checker;

import com._0myun.minecraft.auction.Auction;
import com._0myun.minecraft.auction.LangManager;
import com._0myun.minecraft.auction.OrderManager;
import com._0myun.minecraft.auction.event.AuctionTradeSuccessEvent;
import com._0myun.minecraft.auction.payway.Payway;
import com._0myun.minecraft.auction.table.Orders;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class TimeOutChecker extends BukkitRunnable {
    @Override
    public void run() {

        List<Orders> orders = OrderManager.list(Arrays.asList(0, 1));
        if (orders.isEmpty()) return;
        for (Orders order : orders) {
            OrderManager.refresh(order);
            if (System.currentTimeMillis() <= order.getTimeout()) continue;
            if (!order.canBuy()) continue;
            if (order.getStatus() == 0) {//没有人出价也没人买
                order.setStatus(-1);
            }
            if (order.getStatus() == 1) {//竞拍出价了
                Payway payway = order.getPayway();
                if (!payway.has(order.getNowPlayer(), order.getNowPrice())) {//没钱
                    order.setStatus(-1);
                    List<String> cmds = Auction.INSTANCE.getConfig().getStringList("auction.punishment-when-have-not-enough-money-to-auction");
                    for (String cmd : cmds) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("<player>", order.getNowPlayer()));
                    }
                    OfflinePlayer op = Bukkit.getOfflinePlayer(order.getNowPlayer());
                    if (op.isOnline()) {
                        op.getPlayer().sendMessage(LangManager.getLang("lang31"));
                    }
                } else {//有钱
                    AuctionTradeSuccessEvent event = new AuctionTradeSuccessEvent(order, order.getNowPrice(), order.getNowPrice());
                    Bukkit.getPluginManager().callEvent(event);

                    payway.take(order.getNowPlayer(), event.getTake());
                    payway.give(order.getOwner(), event.getGive());
                    order.setStatus(2);
                }
            }
            OrderManager.save(order);
            if (Bukkit.getOfflinePlayer(order.getOwner()).isOnline()) {
                Player p = Bukkit.getPlayer(order.getOwner());
                if (order.getStatus() == -1) p.sendMessage(LangManager.getLang("lang35"));
                if (order.getStatus() == 2) p.sendMessage(LangManager.getLang("lang36"));
            }
            OrderManager.save(order);
        }
    }
}

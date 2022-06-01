package com._0myun.minecraft.auction.checker;

import com._0myun.minecraft.auction.LangManager;
import com._0myun.minecraft.auction.OrderManager;
import com._0myun.minecraft.auction.table.Orders;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

/**
 * 订单发货操作
 */
public class OrderGiveChecker extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            List<Orders> orders = OrderManager.listNowPlayer(p.getName(), Arrays.asList(2, 3));
            if (orders.isEmpty()) return;
            for (Orders order : orders) {
                OrderManager.refresh(order);
                if (order.getStatus() != 2 && order.getStatus() != 3) continue;
                p.sendMessage(LangManager.getLang("lang28"));
                boolean result = order.getGoodType().giveGood(p.getUniqueId(), order.getGoodType().fromString(order.getData()));
                if (!result) {
                    p.sendMessage(LangManager.getLang("lang29"));
                    break;
                }
                order.setStatus(10);
                boolean save = OrderManager.save(order);
                if (!save) {
                    p.sendMessage("数据库异常...");
                    continue;
                }
                p.sendMessage(LangManager.getLang("lang30"));
            }
        });
    }
}

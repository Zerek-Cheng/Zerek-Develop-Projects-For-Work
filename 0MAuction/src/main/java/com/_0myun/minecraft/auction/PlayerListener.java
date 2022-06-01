package com._0myun.minecraft.auction;

import com._0myun.minecraft.auction.table.Orders;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerListener implements Listener {
    @Getter
    private static HashMap<UUID, Integer> auctionPricesWait = new HashMap<>();

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!auctionPricesWait.containsKey(p.getUniqueId())) return;
        Orders order = OrderManager.get(auctionPricesWait.get(p.getUniqueId()));
        e.setCancelled(true);
        String message = e.getMessage();
        if (!isNumeric(message)) {
            p.sendMessage(LangManager.getLang("lang14"));
            auctionPricesWait.remove(p.getUniqueId());
            return;
        }
        if (OrderManager.tryToAuction(p, auctionPricesWait.get(p.getUniqueId()), Integer.valueOf(message))) {
            p.sendMessage(LangManager.getLang("lang15"));
            if (Bukkit.getOfflinePlayer(order.getOwner()).isOnline()) {
                Bukkit.getPlayer(order.getOwner()).sendMessage(String.format(LangManager.getLang("lang42"), p.getName()));
            }
        } else {
            p.sendMessage(LangManager.getLang("lang16"));
            return;
        }
        auctionPricesWait.remove(p.getUniqueId());
    }

    private static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

package com.WeiBoss.bossshoptr.Listener;

import com.WeiBoss.bossshoptr.Database.SQLManager;
import com.WeiBoss.bossshoptr.Main;
import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.core.BSShop;
import org.black_ixx.bossshop.events.BSPlayerPurchasedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BsPurchasedListener implements Listener {
    private static Main plugin = Main.instance;

    @EventHandler
    public void onBuy(BSPlayerPurchasedEvent e) {
        Player p = e.getPlayer();
        BSShop shop = e.getShop();
        BSBuy buy = e.getShopItem();
        String shopName = shop.getShopName();
        String itemName = buy.getName();
        if (!plugin.shop.contains(shopName)) return;
        if (plugin.itemWhite.contains(itemName)) return;
        ItemStack item = buy.getItem();
        SQLManager.addLog(p, item, shopName, itemName);
    }
}
package com._0myun.systemtransaction.systemtransaction.listener;

import com._0myun.systemtransaction.systemtransaction.GoodsManager;
import com._0myun.systemtransaction.systemtransaction.LangUtil;
import com._0myun.systemtransaction.systemtransaction.inventory.SellGoodGui;
import com._0myun.systemtransaction.systemtransaction.inventory.SellMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        int raw = e.getRawSlot();
        if (!(inv.getHolder() instanceof SellMenu) || (raw != 45 && raw != 53)) return;//只处理下一个上一页
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
        SellMenu menu = (SellMenu) inv.getHolder();
        int page = menu.getPage();
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        switch (raw) {
            case 45:
                page--;
                break;
            case 53:
                page++;
                break;
        }
        p.closeInventory();
        SellMenu.openSellInv(p, page);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickItem(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        int raw = e.getRawSlot();
        if (!(inv.getHolder() instanceof SellMenu) || (raw >= 45)) return;//只处理物品
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
        SellMenu menu = (SellMenu) inv.getHolder();
        e.setCancelled(true);
        ItemStack item = e.getCurrentItem();
        SellGoodGui.openSellInv((Player) e.getWhoClicked(), new GoodsManager().searchGoodBySign(GoodsManager.getGoodSign(item)), menu.getPage());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickSellGoodMenuButton(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        int raw = e.getRawSlot();
        if (!(inv.getHolder() instanceof SellGoodGui)) return;
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
        e.setCancelled(true);

        SellGoodGui menu = (SellGoodGui) inv.getHolder();
        Player p = (Player) e.getWhoClicked();
        switch (raw) {
            case 18: {
                this.changeInvAmount(inv, -64);
                break;
            }
            case 19: {
                this.changeInvAmount(inv, -10);
                break;
            }
            case 20: {
                this.changeInvAmount(inv, -1);
                break;
            }
            case 24: {
                this.changeInvAmount(inv, 1);
                break;
            }
            case 25: {
                this.changeInvAmount(inv, 10);
                break;
            }
            case 26: {
                this.changeInvAmount(inv, 64);
                break;
            }
            case 22: {
                goSell(p, menu.getGood(), inv.getItem(4).getAmount());
                break;
            }
            case 0: {
                SellMenu.openSellInv(p, menu.getFromPage());
                break;
            }
        }
    }

    private void changeInvAmount(Inventory inv, int change) {
        ItemStack item = inv.getItem(4).clone();
        int amount = item.getAmount();
        amount += change;
        if (amount < 1) amount = 1;
        if (amount > 64) amount = 64;
        item.setAmount(amount);
        inv.setItem(4, item);
    }

    private void goSell(Player p, GoodsManager.Good good, int amount) {
        double price = 0;
        for (int i = 0; i < amount; i++) {
            boolean result = good.sell(p);
            if (result) {
                p.sendMessage(String.format(LangUtil.getLang("lang15"), String.valueOf(good.getPrice())));
                price += good.getPrice() / 100d;
            } else {
                p.sendMessage(LangUtil.getLang("lang16"));
                p.sendMessage(String.format(LangUtil.getLang("lang17"), String.valueOf(price)));
                return;
            }
        }
        p.sendMessage(String.format(LangUtil.getLang("lang17"), String.valueOf(price)));
    }
}

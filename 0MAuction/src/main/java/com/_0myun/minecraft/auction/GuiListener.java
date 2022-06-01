package com._0myun.minecraft.auction;

import com._0myun.minecraft.auction.gui.*;
import com._0myun.minecraft.auction.table.Orders;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GuiListener implements Listener {
    /**
     * 拍卖行主界面进入一口价
     *
     * @param e
     */
    @EventHandler
    public void onClickOrders(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();
        if (!(inv.getHolder() instanceof MainGuiHolder)) return;
        if (e.getRawSlot() > 54) return;
        e.setCancelled(true);
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        if (!e.getClick().equals(ClickType.LEFT)) return;
        int orderId = MainGuiHolder.getOrderSign(itemStack);
        if (orderId == 0) return;
        p.closeInventory();
        p.openInventory(GuiManager.fixedPriceQuery(orderId));
    }

    /**
     * 拍卖行主界面换页
     *
     * @param e
     */
    @EventHandler
    public void onClickPage(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();
        if (!(inv.getHolder() instanceof MainGuiHolder)) return;
        if (e.getRawSlot() > 54) return;
        e.setCancelled(true);
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        MainGuiHolder holder = (MainGuiHolder) inv.getHolder();

        int orderId = MainGuiHolder.getOrderSign(itemStack);
        if (orderId != 0) return;

        int rawSlot = e.getRawSlot();
        if (rawSlot == 48) {
            p.openInventory(GuiManager.getMain(holder.getPage() - 1));
        }
        if (rawSlot == 50) {
            p.openInventory(GuiManager.getMain(holder.getPage() + 1));
        }
        if (rawSlot == 46) {
            p.openInventory(GuiManager.getSelling(p));
        }
        if (rawSlot == 52) {
            p.openInventory(GuiManager.getShelves(p));
        }
        if (rawSlot == 53) {
            p.openInventory(GuiManager.getLogs(p.getName(), 0));
        }

    }

    /**
     * 拍卖行主界面进入拍卖
     *
     * @param e
     */
    @EventHandler
    public void onClickOrdersAuction(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();
        if (!(inv.getHolder() instanceof MainGuiHolder)) return;
        if (e.getRawSlot() > 54) return;
        e.setCancelled(true);
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        if (!e.getClick().equals(ClickType.RIGHT)) return;
        int orderId = MainGuiHolder.getOrderSign(itemStack);
        if (orderId == 0) return;
        if (OrderManager.get(orderId).getOwner().equalsIgnoreCase(p.getName())) return;
        PlayerListener.getAuctionPricesWait().put(p.getUniqueId(), orderId);
        p.sendMessage(LangManager.getLang("lang13"));
        p.closeInventory();
    }

    /**
     * 拍卖行一口价确认页
     *
     * @param e
     */
    @EventHandler
    public void onClickFixedPriceQuery(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();
        int rawSlot = e.getRawSlot();
        if (!(inv.getHolder() instanceof FixedPriceQueryGuiHolder)) return;
        if (e.getRawSlot() > 54) return;
        e.setCancelled(true);
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        FixedPriceQueryGuiHolder holder = (FixedPriceQueryGuiHolder) inv.getHolder();
        if (rawSlot == 4) return;
        if (rawSlot == 8) {
            p.closeInventory();
            p.openInventory(GuiManager.getMain(0));
        }
        if (rawSlot == 0) {
            if (holder.getOrder().getOwner().equalsIgnoreCase(p.getName())) return;
            p.closeInventory();
            Orders order = holder.getOrder();
            OrderManager.refresh(order);
            if (!order.canBuy() || !order.canBuyWithFixedPrice()) {
                p.sendMessage(LangManager.getLang("lang10"));
                return;
            }
            OrderManager.buyWithFixedPrice(p, order.getId());
        }
    }

    /**
     * 点击自己已上架的物品进入下架界面
     *
     * @param e
     */
    @EventHandler
    public void onClickSellingOrders(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();
        int rawSlot = e.getRawSlot();
        if (!(inv.getHolder() instanceof SellingGuiHolder)) return;
        if (rawSlot > 54) return;
        e.setCancelled(true);
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        if (!e.getClick().equals(ClickType.LEFT)) return;
        int orderId = SellingGuiHolder.getOrderSign(itemStack);
        p.closeInventory();
        if (rawSlot == 45) {
            p.closeInventory();
            p.openInventory(GuiManager.getMain(0));
            return;
        }
        if (orderId == 0) {
            p.openInventory(GuiManager.shelvesQuery(orderId, true));
        } else {
            p.openInventory(GuiManager.shelvesQuery(orderId, false));
        }
    }

    /**
     * 下架物品确认页
     *
     * @param e
     */
    @EventHandler
    public void onClickSellingQuery(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();
        int rawSlot = e.getRawSlot();
        if (!(inv.getHolder() instanceof ShelvesQueryGuiHolder)) return;
        if (e.getRawSlot() > 54) return;
        e.setCancelled(true);
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        ShelvesQueryGuiHolder holder = (ShelvesQueryGuiHolder) inv.getHolder();
        if (rawSlot == 4) return;
        try {
            if (rawSlot == 0) {
                if (holder.isDoAll()) {
                    List<Orders> orders = OrderManager.list(p.getName(), Arrays.asList(0, 1));
                    for (Orders order : orders) {
                        order.setStatus(-1);
                        Auction.INSTANCE.dao.update(order);
                    }
                } else {
                    Orders order = holder.getOrder();
                    order.setStatus(-1);
                    Auction.INSTANCE.dao.update(order);
                }

                p.closeInventory();
                p.openInventory(GuiManager.getSelling(p));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (rawSlot == 8) {
            p.closeInventory();
            p.openInventory(GuiManager.getSelling(p));
        }
    }

    /**
     * 点击自己已下架的物品取回
     *
     * @param e
     */
    @EventHandler
    public void onClickShelvesOrders(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();
        int rawSlot = e.getRawSlot();
        if (!(inv.getHolder() instanceof ShelvesGuiHolder)) return;
        if (rawSlot > 54) return;
        e.setCancelled(true);
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        if (!e.getClick().equals(ClickType.LEFT)) return;
        int orderId = ShelvesGuiHolder.getOrderSign(itemStack);

        if (rawSlot == 45) {
            p.closeInventory();
            p.openInventory(GuiManager.getMain(0));
            return;
        }

        if (orderId == 0) {
            OrderManager.tryToGetShelves(p);
            p.openInventory(GuiManager.getShelves(p));
        } else {
            if (OrderManager.tryToGetShelves(p, orderId))
                inv.setItem(rawSlot, new ItemStack(Material.AIR));
        }
        p.sendMessage(LangManager.getLang("lang33"));
    }

    /**
     * 交易记录换页
     *
     * @param e
     */
    @EventHandler
    public void onClickLogsPage(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack itemStack = e.getCurrentItem();
        if (!(inv.getHolder() instanceof LogsGuiHolder)) return;
        if (e.getRawSlot() > 54) return;
        e.setCancelled(true);
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        LogsGuiHolder holder = (LogsGuiHolder) inv.getHolder();

        int rawSlot = e.getRawSlot();
        if (rawSlot == 45) {
            p.openInventory(GuiManager.getMain(0));
        }
        if (rawSlot == 48) {
            p.openInventory(GuiManager.getLogs(p.getName(), holder.getPage() - 1));
        }
        if (rawSlot == 50) {
            p.openInventory(GuiManager.getLogs(p.getName(), holder.getPage() + 1));
        }

    }

    @EventHandler
    public void denyShiftClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof FixedPriceQueryGuiHolder || holder instanceof LogsGuiHolder || holder instanceof MainGuiHolder || holder instanceof SellingGuiHolder
                || holder instanceof ShelvesGuiHolder || holder instanceof ShelvesQueryGuiHolder) {
            if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                e.setCancelled(true);
            }
        }
    }
}

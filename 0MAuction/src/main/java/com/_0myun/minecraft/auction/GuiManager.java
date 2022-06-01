package com._0myun.minecraft.auction;

import com._0myun.minecraft.auction.gui.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuiManager {

    public static Inventory getMain(int page) {
        if (page < 0) page = 0;
        MainGuiHolder holder = new MainGuiHolder();
        Inventory gui = Bukkit.createInventory(holder, 54, String.format(LangManager.getLang("lang37"), String.valueOf(page + 1)));
        holder.setInv(gui);
        holder.setPage(page);
        holder.init();

        return gui;
    }

    public static Inventory fixedPriceQuery(int orderId) {
        FixedPriceQueryGuiHolder holder = new FixedPriceQueryGuiHolder();
        Inventory gui = Bukkit.createInventory(holder, 9, String.format(LangManager.getLang("lang38"), String.valueOf(orderId)));
        holder.setInv(gui);
        holder.setOrder(OrderManager.get(orderId));
        holder.init();

        return gui;
    }

    public static Inventory getSelling(Player p) {
        SellingGuiHolder holder = new SellingGuiHolder();
        Inventory gui = Bukkit.createInventory(holder, 54, LangManager.getLang("lang39"));
        holder.setInv(gui);
        holder.setPlayer(p.getName());
        holder.init();

        return gui;
    }

    public static Inventory shelvesQuery(int orderId, boolean all) {
        ShelvesQueryGuiHolder holder = new ShelvesQueryGuiHolder();
        Inventory gui = Bukkit.createInventory(holder, 9, LangManager.getLang("lang19"));
        holder.setInv(gui);
        if (!all)
            holder.setOrder(OrderManager.get(orderId));
        holder.setDoAll(all);
        holder.init();

        return gui;
    }

    public static Inventory getShelves(Player p) {
        ShelvesGuiHolder holder = new ShelvesGuiHolder();
        Inventory gui = Bukkit.createInventory(holder, 54, LangManager.getLang("lang40"));
        holder.setInv(gui);
        holder.setPlayer(p.getName());
        holder.init();

        return gui;
    }

    public static Inventory getLogs(String player, int page) {
        if (page < 0) page = 0;
        LogsGuiHolder holder = new LogsGuiHolder();
        Inventory gui = Bukkit.createInventory(holder, 54, String.format(LangManager.getLang("lang41"), String.valueOf(page + 1)));
        holder.setInv(gui);
        holder.setPage(page);
        holder.setPlayer(player);
        holder.init();

        return gui;
    }
}

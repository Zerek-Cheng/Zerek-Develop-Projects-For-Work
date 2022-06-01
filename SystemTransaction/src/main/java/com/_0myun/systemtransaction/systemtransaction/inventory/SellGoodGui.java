package com._0myun.systemtransaction.systemtransaction.inventory;

import com._0myun.systemtransaction.systemtransaction.GoodsManager;
import com._0myun.systemtransaction.systemtransaction.LangUtil;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Data
public class SellGoodGui implements InventoryHolder {

    private Inventory inv;
    private GoodsManager.Good good;
    private int fromPage;

    public Inventory getInventory() {
        return inv;
    }

    public static void openSellInv(Player p, GoodsManager.Good good) {
        openSellInv(p, good, -1);
    }

    public static void openSellInv(Player p, GoodsManager.Good good, int fromPage) {
        SellGoodGui ui = new SellGoodGui();
        Inventory inv = Bukkit.createInventory(ui, 3 * 9, LangUtil.getLang("lang4"));
        ui.setInv(inv);
        ui.setGood(good);
        ui.setFromPage(fromPage);

        List<String> lores = new ArrayList<String>();
        lores.add(LangUtil.getLang("lang11"));

        ItemStack minu = new ItemStack(133);
        ItemMeta minuItemMeta = minu.getItemMeta();
        minuItemMeta.setLore(lores);
        minu.setItemMeta(minuItemMeta);

        ItemStack add = new ItemStack(152);
        ItemMeta addItemMeta = add.getItemMeta();
        addItemMeta.setLore(lores);
        add.setItemMeta(addItemMeta);

        ItemStack query = new ItemStack(278, 1);
        ItemMeta queryItemMeta = query.getItemMeta();
        queryItemMeta.setDisplayName(LangUtil.getLang("lang12"));
        lores = new ArrayList<String>();
        lores.add(LangUtil.getLang("lang13"));
        queryItemMeta.setLore(lores);
        query.setItemMeta(queryItemMeta);

        inv.setItem(4, good.toItem());

        inv.setItem(18, getAmountButton(minu, LangUtil.getLang("lang9"), 64));
        inv.setItem(19, getAmountButton(minu, LangUtil.getLang("lang9"), 10));
        inv.setItem(20, getAmountButton(minu, LangUtil.getLang("lang9"), 1));

        inv.setItem(22, query);

        inv.setItem(24, getAmountButton(add, LangUtil.getLang("lang10"), 1));
        inv.setItem(25, getAmountButton(add, LangUtil.getLang("lang10"), 10));
        inv.setItem(26, getAmountButton(add, LangUtil.getLang("lang10"), 64));
        if (ui.getFromPage() != -1) {
            ItemStack back = new ItemStack(401);
            ItemMeta itemMeta = back.getItemMeta();
            itemMeta.setDisplayName(LangUtil.getLang("lang14"));
            back.setItemMeta(itemMeta);
            inv.setItem(0, back);
        }

        p.openInventory(inv);
    }

    private static ItemStack getAmountButton(ItemStack itemStack, String lang, int amount) {
        itemStack = itemStack.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(String.format(lang, String.valueOf(amount)));
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);
        return itemStack;
    }

}

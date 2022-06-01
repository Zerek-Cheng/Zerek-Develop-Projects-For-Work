package com._0myun.systemtransaction.systemtransaction.inventory;

import com._0myun.systemtransaction.systemtransaction.GoodsManager;
import com._0myun.systemtransaction.systemtransaction.LangUtil;
import com._0myun.systemtransaction.systemtransaction.Main;
import com._0myun.systemtransaction.systemtransaction.util.Minecraft;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Data
public class SellMenu implements InventoryHolder {

    private GoodsManager gm;
    private List<GoodsManager.Good> goodList;
    private Inventory inv;
    private int page = 0;

    public Inventory getInventory() {
        return this.getInv();
    }

    public static void openSellInv(Player p, int page) {
        SellMenu sellMenuHolder = new SellMenu();
        Inventory inv = Bukkit.createInventory(sellMenuHolder, 6 * 9, LangUtil.getLang("lang4"));
        sellMenuHolder.setInv(inv);
        sellMenuHolder.setPage(page);
        sellMenuHolder.setGm(new GoodsManager());
        sellMenuHolder.setGoodList(sellMenuHolder.getGm().getPage(page));

        List<ItemStack> itemView = new ArrayList();
        for (GoodsManager.Good good : sellMenuHolder.getGoodList()) {
            ItemStack item = good.toItem();
            ItemMeta itemMeta = item.getItemMeta();
            List<String> loreList = itemMeta.getLore();
            loreList.add(LangUtil.getLang("lang3").replace("<price>", String.valueOf(good.getPrice() / 100d)));
            itemMeta.setLore(loreList);
            item.setItemMeta(itemMeta);
            itemView.add(item);
        }
        inv.setContents(itemView.toArray(new ItemStack[itemView.size()]));
        if (page > 0) {
            inv.setItem(45, getButtonItem(ButtonType.Before));
        }
        if (((sellMenuHolder.getPage() + 1) * 5 * 9) > sellMenuHolder.getGm().getGoods().size()) {//TODO 有下一页   暂时改成大于
            inv.setItem(53, getButtonItem(ButtonType.After));
        }

        p.openInventory(inv);
    }

    private static ItemStack getButtonItem(ButtonType type) {
        ConfigurationSection buttonsConfig = Main.getPlugin().getConfig().getConfigurationSection("button");
        ConfigurationSection buttonConfig = buttonsConfig.getConfigurationSection(type.getType());
        Minecraft.IdParser idParser = Minecraft.parseItemId(buttonConfig.getString("id"));
        ItemStack item = new ItemStack(0);
        item.setTypeId(idParser.getId());
        item.setDurability(idParser.getSubId());
        ItemMeta itemMeta = item.getItemMeta();
        switch (type) {
            case Before:
                itemMeta.setDisplayName(LangUtil.getLang("lang6"));
                break;
            case After:
                itemMeta.setDisplayName(LangUtil.getLang("lang7"));
                break;
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    enum ButtonType {
        Before("before"), After("after");
        @Getter
        private String type;

        ButtonType(String type) {
            this.type = type;
        }
    }

}


package com._0myun.minecraft.auction.gui;

import com._0myun.minecraft.auction.ConfigUtils;
import com._0myun.minecraft.auction.table.Orders;
import lombok.Data;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Data
public class ShelvesQueryGuiHolder implements InventoryHolder {

    Inventory inv;
    Orders order;
    boolean doAll;

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void init() {
        Inventory inv = this.getInventory();

        inv.setItem(0, ConfigUtils.getConfigItem("gui.button.fixed-price-query-yes"));
        if (this.isDoAll()) {
            inv.setItem(4, ConfigUtils.getConfigItem("gui.button.page-selling-shelves"));
        } else {
            inv.setItem(4, this.order.getItemShow());
        }
        inv.setItem(8, ConfigUtils.getConfigItem("gui.button.fixed-price-query-no"));
    }

}

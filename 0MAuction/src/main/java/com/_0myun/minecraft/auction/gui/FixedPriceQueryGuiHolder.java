package com._0myun.minecraft.auction.gui;

import com._0myun.minecraft.auction.ConfigUtils;
import com._0myun.minecraft.auction.table.Orders;
import lombok.Data;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Data
public class FixedPriceQueryGuiHolder implements InventoryHolder {

    Inventory inv;
    Orders order;

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void init() {
        Inventory inv = this.getInventory();

        inv.setItem(0, ConfigUtils.getConfigItem("gui.button.fixed-price-query-yes"));
        inv.setItem(4, this.getOrder().getItemShow());
        inv.setItem(8, ConfigUtils.getConfigItem("gui.button.fixed-price-query-no"));
    }

}

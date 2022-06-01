package com._0myun.minecraft.eventmsg.auction.inv;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class UiHolder implements InventoryHolder {
    @Setter
    @Getter
    private Inventory inv;

    @Override
    public Inventory getInventory() {
        return this.inv;
    }
}

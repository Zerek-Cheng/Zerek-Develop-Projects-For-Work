package com._0myun.minecraft.banitemshower;

import lombok.Data;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
@Data
public class ShowerHolder implements InventoryHolder {
    Inventory inv;
    int page;

    @Override
    public Inventory getInventory() {
        return inv;
    }
}

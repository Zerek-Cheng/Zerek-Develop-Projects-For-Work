package com._0myun.minecraft.playersignature;

import lombok.Data;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
@Data
public class Holder implements InventoryHolder {
    Inventory inventory;
    @Override
    public Inventory getInventory() {
        return null;
    }
}

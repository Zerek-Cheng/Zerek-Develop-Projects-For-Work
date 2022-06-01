package com._0myun.minecraft.randgift;

import lombok.Data;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Data
public class SetterHolderShower implements InventoryHolder {
    Inventory inv;
    String name;

    @Override
    public Inventory getInventory() {
        return this.inv;
    }
}

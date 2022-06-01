package com._0myun.minecraft.dentallaboratories.gui;

import lombok.Data;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

@Data
public class GuiHolder implements InventoryHolder {

    private Inventory inventory;
    private ItemStack itemStack;

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}

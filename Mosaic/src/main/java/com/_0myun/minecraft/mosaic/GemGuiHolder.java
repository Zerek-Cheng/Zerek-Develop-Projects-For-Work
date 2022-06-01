package com._0myun.minecraft.mosaic;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Data
public class GemGuiHolder implements InventoryHolder {
    Inventory inventory;
    ItemStack itemStack;
    Player player;

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void init() {
        Inventory inv = this.getInventory();
        int max = Mosaic.INSTANCE.getMax();

        ItemStack wallItem = getWallItem();
        ItemStack breakItem = getBreakItem();
        ItemStack canOpenItem = getCanOpenItem();
        for (int i = max; i < inv.getSize(); i++) {
            inv.setItem(i, wallItem);
        }
        for (int i = 0; i < max; i++) {
            if (Mosaic.INSTANCE.isSlotCanOpen(itemStack, i)) inv.setItem(i, canOpenItem);
            if (Mosaic.INSTANCE.isSlotBreak(itemStack, i)) inv.setItem(i, breakItem);
            if (Mosaic.INSTANCE.isSlotUse(itemStack, i)) inv.setItem(i, Mosaic.INSTANCE.getSlotGemDataItem(itemStack, i));
        }

    }

    private static ItemStack getWallItem() {
        ItemStack wall = new ItemStack(Mosaic.INSTANCE.getConfig().getInt("gui.wall"));
        ItemMeta itemMeta = wall.getItemMeta();
        itemMeta.setDisplayName(LangManager.get("lang2"));
        wall.setItemMeta(itemMeta);
        return wall;
    }

    private static ItemStack getBreakItem() {
        ItemStack wall = new ItemStack(Mosaic.INSTANCE.getConfig().getInt("gui.break"));
        ItemMeta itemMeta = wall.getItemMeta();
        itemMeta.setDisplayName(LangManager.get("lang4"));
        wall.setItemMeta(itemMeta);
        return wall;
    }

    private static ItemStack getCanOpenItem() {
        ItemStack wall = new ItemStack(Mosaic.INSTANCE.getConfig().getInt("gui.canOpen"));
        ItemMeta itemMeta = wall.getItemMeta();
        itemMeta.setDisplayName(LangManager.get("lang3"));
        wall.setItemMeta(itemMeta);
        return wall;
    }
}

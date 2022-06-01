package com._0myun.minecraft.mosaic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiManager {

    public static Inventory getGemGui(Player p, ItemStack itemStack) {
        GemGuiHolder holder = new GemGuiHolder();
        holder.setItemStack(itemStack);
        holder.setPlayer(p);
        Inventory inv = Bukkit.createInventory(holder, 9);
        holder.setInventory(inv);

        holder.init();
        return inv;
    }
}

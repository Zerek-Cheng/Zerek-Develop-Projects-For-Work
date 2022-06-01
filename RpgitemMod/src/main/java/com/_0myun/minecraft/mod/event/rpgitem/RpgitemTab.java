package com._0myun.minecraft.mod.event.rpgitem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RpgitemTab extends CreativeTabs {
    public RpgitemTab() {
        super("rpgitem");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Item.getItemById(7));
    }
}

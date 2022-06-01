package com._0myun.minecraft.mod.event.rpgitem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RpgitemArmotTab extends CreativeTabs {
    public RpgitemArmotTab() {
        super("rpgitemarmor");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Item.getItemById(7));
    }
}

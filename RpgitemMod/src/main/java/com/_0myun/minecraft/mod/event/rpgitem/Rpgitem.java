package com._0myun.minecraft.mod.event.rpgitem;

import net.minecraft.item.Item;

public class Rpgitem extends Item {
    public Rpgitem(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setMaxDamage(0);
        this.setMaxStackSize(64);
        this.setCreativeTab(Main.rpgitemTab);
    }

    public void registerModels() {
        new ClientProxy().registerItemRenderer(this, 0, "inventory");
    }
}

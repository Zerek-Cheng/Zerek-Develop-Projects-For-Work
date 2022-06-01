package com._0myun.minecraft.mod.event.rpgitem;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class RpgitemArmor extends ItemArmor {
    public RpgitemArmor(String name, ArmorMaterial material, EntityEquipmentSlot equipmentSlotIn) {
        super(material, 1, equipmentSlotIn);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setCreativeTab(Main.rpgitemArmorTab);
    }

    public void registerModels() {
        new ClientProxy().registerItemRenderer(this, 0, "inventory");
    }
}

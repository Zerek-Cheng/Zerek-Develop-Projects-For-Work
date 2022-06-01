package com._0myun.minecraft.tiredcommands;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PotionManager {

    private static ConfigurationSection getData() {
        return Main.plugin.getConfig().getConfigurationSection("potion");
    }

    public static ConfigurationSection getPotionConfig(String name) {
        return getData().getConfigurationSection(name);
    }

    public static int getPotionAdd(String name) {
        return getPotionConfig(name).getInt("add");
    }

    public static ItemStack getPotionItem(String name) {
        ConfigurationSection potionConfig = getPotionConfig(name);
        String[] ids = potionConfig.getString("id").split(":");
        ItemStack itemStack = null;
        if (ids.length == 1) {
            itemStack = new ItemStack(Integer.parseInt(ids[0]), 1);
        } else if (ids.length >= 2) {
            itemStack = new ItemStack(Integer.parseInt(ids[0]), 1, Short.valueOf(ids[1]));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(potionConfig.getString("display"));
        itemMeta.setLore(potionConfig.getStringList("lore"));
        itemStack.setItemMeta(itemMeta);


        itemStack = MinecraftReflection.getBukkitItemStack(itemStack);
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemOptional(itemStack).get();
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.tiredcommands.Main.type", "potion");
        nbt.put("com._0myun.minecraft.tiredcommands.Main.potion.name", name);
        NbtFactory.setItemTag(itemStack, nbt);
        return itemStack;
    }

    public static String getNameFromItemStack(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return null;
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        try {
            return nbt.getString("com._0myun.minecraft.tiredcommands.Main.potion.name");
        } catch (Exception ex) {
            return null;
        }
    }
}

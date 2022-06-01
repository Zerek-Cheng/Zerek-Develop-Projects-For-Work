package com._0myun.bind;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public final class Utils {
    public static boolean hasBind(ItemStack itemStack) {
        return getOwner(itemStack) != null;
    }

    public static String getOwner(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return null;
        try {
            itemStack = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(itemStack));
            NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(itemStack));
            return nbt.getString("com._0myun.bind.R.owner");
        } catch (Exception ex) {
            return null;
        }
    }


    public static void setOwner(ItemStack itemStack, UUID owner) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
        try {
            itemStack = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(itemStack));
            NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(itemStack));
            if (owner != null)
                nbt.put("com._0myun.bind.R.owner", owner.toString());
            else
                nbt.remove("com._0myun.bind.R.owner");
            NbtFactory.setItemTag(itemStack, nbt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

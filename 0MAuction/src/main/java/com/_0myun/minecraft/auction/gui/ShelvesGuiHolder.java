package com._0myun.minecraft.auction.gui;

import com._0myun.minecraft.auction.ConfigUtils;
import com._0myun.minecraft.auction.OrderManager;
import com._0myun.minecraft.auction.table.Orders;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Data
public class ShelvesGuiHolder implements InventoryHolder {
    String player;
    Inventory inv;

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void init() {
        Inventory inv = this.getInventory();
        List<Orders> orders = OrderManager.list(this.player, Arrays.asList(-1));
        for (Orders order : orders) {
            ItemStack itemShow = order.getItemShow();
            if (itemShow == null || itemShow.getType().equals(Material.AIR)) continue;
            itemShow = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(itemShow));
            setOrderSign(itemShow, order.getId());
            inv.addItem(itemShow);
        }
        inv.setItem(45, ConfigUtils.getConfigItem("gui.button.back"));
        inv.setItem(49, ConfigUtils.getConfigItem("gui.button.page-shelves-getall"));

    }

    public static void setOrderSign(ItemStack itemStack, int id) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.auction.Auction.order", id);
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public static int getOrderSign(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getIntegerOrDefault("com._0myun.minecraft.auction.Auction.order");
    }
}
